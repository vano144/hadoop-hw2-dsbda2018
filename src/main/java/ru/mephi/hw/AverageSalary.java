package ru.mephi.hw;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.spark.api.java.JavaPairRDD;

import java.util.ArrayList;


public class AverageSalary {


    public static void runProducer(String[] args) {
        AppProducer appProducer = new AppProducer();
        if (args.length == 2) {
            appProducer.addDataFromCsvFile(args[1]);
        } else {
            appProducer.generateData(Constants.AMOUNT_OF_GENERATED_RECORDS);
        }
        appProducer.close();
    }

    public static void runConsumer() {
        SparkApi sparkApi = new SparkApi();
        AppConsumer appConsumer = new AppConsumer();
        ArrayList<String> workers = appConsumer.getData();

        JavaPairRDD<String, Double> data = sparkApi.runSparkTask(workers);
        Cluster cluster = Cluster.builder().addContactPoint(Constants.CASSANDRA_HOST).build();
        Session session = cluster.connect(Constants.CASSANDRA_KEYSPACE);
        data.collectAsMap().forEach((k,v) -> CassandraApi.write(session, k, v));
        session.close();
        cluster.close();
        sparkApi.close();
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            if (args[0].equals("produce")) {
                runProducer(args);
                return;
            }
        }
    }
}