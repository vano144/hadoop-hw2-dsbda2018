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
        // initialize spark connection with JavaSparkContext
//        final SparkConf sparkConf = new SparkConf().setAppName("Workers").setMaster("spark://MacBook-Pro-Ivan.local:7077");
        final SparkConf sparkConf = new SparkConf().setAppName("Workers").setMaster("local");
        context = new JavaSparkContext(sparkConf);

    }

    public void close() {
        context.close();
    }

    public JavaPairRDD<String, Double> runSparkTask(ArrayList<String> workers) {

        JavaRDD<String> lines = context.parallelize(workers);

        // str data -> mapStruct(str, <int,int>) -> reduce -> map(str, int/int)
        final JavaPairRDD<String, Double> data = lines
                .mapToPair((x) -> {
                    String[] record = x.split(",");
                    int salary = Integer.parseInt(record[1]);
                    String passportNumber = record[0];
                    return new Tuple2<String, Tuple2<Integer, Integer>>(passportNumber, new Tuple2(salary, 1));
                })
                .reduceByKey((Function2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>)
                        (i1, i2) -> new Tuple2(i1._1 + i2._1, i1._2 + i2._2))
                .mapValues((Function<Tuple2<Integer, Integer>, Double>)
                        f -> {
                            Integer a = f._1;
                            Integer b = f._2;
                            return (double) a / b;
                        }
                );

        // output to stdout
        data.foreach(x -> {
            String output = String.format("Mean salary for passport:%s = %f", x._1, x._2);
            System.out.println(output);
        });
        return data;

    }
}
