package ru.mephi.hw;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AppProducerTest {
    MockProducer<String, String> producer;

    @Before
    public void setUp() {
        producer = new MockProducer<>(
                true, new StringSerializer(), new StringSerializer());
    }

    @Test
    public void testProducer() {
        AppProducer appProducer = new AppProducer();
        appProducer.producer = producer;

        appProducer.generateData(1);

        List<ProducerRecord<String, String>> history = producer.history();

        assertNotEquals(history.size(), 0);
    }
}

