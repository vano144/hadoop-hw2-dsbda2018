#SPARK-CASSANDRA-KAFKA mean salary

It is app, which calculates mean salary. 

## Minimal system requirements
* Java 8
* Apache Maven 4.0.0
* Mac OS
* python3
* brew


## How to start

* Initialize system environment

    ```bash init_system.sh```
    ```bash init_cassandra.sh```


* Create jar

  ```bash build.sh```

* Prepare data

  ```bash generate_data.sh```
  
It will create workers.csv file. 

    ```bash produce_run.sh```
    
    or with csv
    
    ```bash produce_run_with_csv.sh```
  
* Run it

  ```bash run.sh```
 

