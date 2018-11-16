DROP KEYSPACE IF EXISTS hw2;
CREATE KEYSPACE hw2 WITH replication = {'class': 'SimpleStrategy', 'replication_factor':1};
CREATE TABLE hw2.mean_salary (id INT PRIMARY KEY, passportNumber TEXT, salary DOUBLE );