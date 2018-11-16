package ru.mephi.hw;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class AppConsumer {

    Consumer consumer;

    public AppConsumer() {
        // initialize base properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", Constants.KAFKA_BROKERS);
        properties.setProperty("group.id","1");
        properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset","earliest");
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(Constants.TOPIC));
    }

    public ArrayList<String> getData() {

        ArrayList<String> result = new ArrayList<>();
        int i = 0;
        String[] workerRecord;
        // polling data from kafka
        while(i < Constants.KAFKA_POLL_AMOUNTS) {

            ConsumerRecords<String,String> records = consumer.poll(Constants.KAFKA_POLL_INTERVAL);
            // limit kafka connection by attempts
            if (records.isEmpty())
            {
                i++;
                continue;
            }
            for (ConsumerRecord<String,String> record : records) {
                workerRecord = record.value().split(Constants.CSV_SPITTER);
                // get only valid data to kafka
                if (workerRecord.length == 3) {
                    result.add(new StringBuilder(workerRecord[0]).append(Constants.CSV_SPITTER).append(workerRecord[2]).toString());
                }
            }
        }
        return result;
    }
}
