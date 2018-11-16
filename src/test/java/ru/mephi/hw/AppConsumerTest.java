package ru.mephi.hw;

import java.util.ArrayList;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


public class AppConsumerTest {


    MockConsumer<String, String> consumer;

    @Before
    public void setUp() {
        consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    }

    @Test
    public void ConsumerGetDataTest() {
        AppConsumer appConsumer = new AppConsumer();
        appConsumer.consumer = consumer;
        consumer.assign(Arrays.asList(new TopicPartition(Constants.TOPIC, 0)));
        HashMap<TopicPartition, Long> beginningOffsets = new HashMap<>();
        beginningOffsets.put(new TopicPartition(Constants.TOPIC, 0), 0L);
        consumer.updateBeginningOffsets(beginningOffsets);
        consumer.addRecord(new ConsumerRecord<String, String>(Constants.TOPIC,
                0, 0L, "mykey", "a,1,1"));
        consumer.addRecord(new ConsumerRecord<String, String>(Constants.TOPIC, 0,
                1L, "mykey", "b,1,1"));
        ArrayList<String> data =  appConsumer.getData();
        assertEquals(2,data.size());
    }
}