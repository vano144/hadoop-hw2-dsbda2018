package ru.mephi.hw;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.spark.api.java.JavaPairRDD;

import java.util.ArrayList;


public class AverageSalary {


    public static void runProducer(String[] args) {
        // run producer with csv or without
        AppProducer appProducer = new AppProducer();
        if (args.length == 2) {
            appProducer.addDataFromCsvFile(args[1]);
        } else {
            appProducer.generateData(Constants.AMOUNT_OF_GENERATED_RECORDS);
        }
        appProducer.close();
    }

    public static void runConsumer() {
        // initializa JavaSparkContext
        SparkApi sparkApi = new SparkApi();
        // initializa kafka consumer
        AppConsumer appConsumer = new AppConsumer();
        ArrayList<String> workers = appConsumer.getData();

        // transformate data in spark
        JavaPairRDD<String, Double> data = sparkApi.runSparkTask(workers);
        Cluster cluster = Cluster.builder().addContactPoint(Constants.CASSANDRA_HOST).build();
        Session session = cluster.connect(Constants.CASSANDRA_KEYSPACE);

        // write data to Cassandra
        data.collectAsMap().forEach((k,v) -> CassandraApi.write(session, k, v));
        session.close();
        cluster.close();
        sparkApi.close();
    }

    public static void main(String[] args) throws Exception {
        // start program consumer with spark and cassandra or producer
        if (args.length > 0) {
            if (args[0].equals("produce")) {
                runProducer(args);
                return;
            }
        }
        runConsumer();
    }
}