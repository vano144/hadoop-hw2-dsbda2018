package ru.mephi.hw;

import javafx.util.Pair;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.ArrayList;

public class SparkApi {

    JavaSparkContext context;

    public SparkApi() {
        final SparkConf sparkConf = new SparkConf().setAppName("Workers").setMaster("local");
        context = new JavaSparkContext(sparkConf);

    }

    public void close() {
        context.close();
    }

    public JavaPairRDD<String, Double> runSparkTask(ArrayList<String> workers) {
        JavaRDD<String> lines = context.parallelize(workers);
        final JavaPairRDD<String, Double> data = lines
                .mapToPair((x) -> {
                    String[] record = x.split(",");
                    int salary = Integer.parseInt(record[1]);
                    String passportNumber = record[0];
                    return new Tuple2<String, Pair<Integer, Integer>>(passportNumber, new Pair(salary, 1));
                })
                .reduceByKey((Function2<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>>)
                        (i1, i2) -> new Pair(i1.getKey() + i2.getKey(), i1.getValue() + i2.getValue()))
                .mapValues((Function<Pair<Integer, Integer>, Double>)
                        f -> {
                            Integer a = f.getKey();
                            Integer b = f.getValue();
                            return (double) a / b;
                        }
                );
        data.foreach(x -> {
            String output = String.format("Mean salary for passport:%s = %f", x._1, x._2);
            System.out.println(output);
        });
        return data;

    }
}
