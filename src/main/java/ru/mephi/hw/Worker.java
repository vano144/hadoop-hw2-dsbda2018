package ru.mephi.hw;

import java.util.concurrent.ThreadLocalRandom;

public class Worker {

    public static String getCorrectRecord(String passportNumber, int monthNumber, int salary) {
        StringBuilder sb = new StringBuilder();
        sb.append(passportNumber);
        sb.append(Constants.CSV_SPITTER);
        sb.append(monthNumber);
        sb.append(Constants.CSV_SPITTER);
        sb.append(salary);
        return sb.toString();
    }

    public static String getCorrectRecord(String passportNumber, String monthNumber, String salary) {
        StringBuilder sb = new StringBuilder();
        sb.append(passportNumber);
        sb.append(Constants.CSV_SPITTER);
        sb.append(monthNumber);
        sb.append(Constants.CSV_SPITTER);
        sb.append(salary);
        return sb.toString();
    }

    public static int getRndSalary() {
        return ThreadLocalRandom.current().nextInt(10, 100000 + 1);
    }

    public static int getRndMonthNumber() {
        return ThreadLocalRandom.current().nextInt(1, 12 + 1);
    }

    public static String getRndPassportNumber () {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            if (i == 2 || i == 5) {
                sb.append(" ");
            }
            else {
                sb.append(ThreadLocalRandom.current().nextInt(0, 9 + 1));
            }
        }
        return sb.toString();
    }

}
