package ru.mephi.hw;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class AppProducer {

    Producer producer;

    public AppProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, Constants.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
    }

    public void addDataFromCsvFile(String csvFile) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] record = line.split(Constants.CSV_SPITTER);
                if (record.length == 3) {
                    send(record[0], record[1], record[2]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        producer.flush();
    }

    public void send(String passportNumber, int monthNumber, int salary) {
        producer.send(new ProducerRecord<String, String>(Constants.TOPIC,
                null, Worker.getCorrectRecord(passportNumber, monthNumber, salary)));
    }

    public void send(String passportNumber, String monthNumber, String salary) {
        producer.send(new ProducerRecord<String, String>(Constants.TOPIC,
                 null, Worker.getCorrectRecord(passportNumber, monthNumber, salary)));
    }



    public void generateData(int AmountOfRecords) {
        for (int i = 0; i < AmountOfRecords; i++) {
            String passportNumber = Worker.getRndPassportNumber();
            for (int j = 0; j <  ThreadLocalRandom.current().nextInt(1, 10 + 1); j++) {
                send(passportNumber, Worker.getRndMonthNumber(), Worker.getRndSalary());
            }
        }
        producer.flush();
    }

    public void close() {
        producer.close();
    }

}

