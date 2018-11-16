package ru.mephi.hw;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class CassandraApi {

    public static void write(Session session, String passportNumber, Double meanSalary){
        // insert passportNumber and meanSalary in Cassandra
        Insert insert = QueryBuilder
                .insertInto(Constants.CASSANDRA_KEYSPACE, Constants.CASSANDRA_TABLE)
                .value("passportNumber", passportNumber)
                .value("salary", meanSalary)
                .value("id", passportNumber.hashCode());
        session.execute(insert.toString());
    }
}
