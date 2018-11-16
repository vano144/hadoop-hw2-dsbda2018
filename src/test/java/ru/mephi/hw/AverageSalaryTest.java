package ru.mephi.hw;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AverageSalaryTest {



    @Test
    public void testAverageSalary() {

        Cluster cluster = Cluster.builder().addContactPoint(Constants.CASSANDRA_HOST).build();
        Session session = cluster.connect(Constants.CASSANDRA_KEYSPACE);
        final String query = "Select count(*) from " + Constants.CASSANDRA_KEYSPACE + "." + Constants.CASSANDRA_TABLE;
        Object result = session.execute(query).one().getToken(0).getValue();
//        assertEquals((long) result, 0);

        AverageSalary.runProducer(new String[0]);
        AverageSalary.runConsumer();

        Object newResult = session.execute(query).one().getToken(0).getValue();
        assertNotEquals((long) newResult, 0);

    }
}
