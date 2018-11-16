package ru.mephi.hw;

import org.junit.Test;

public class WorkerTest {

    @Test
    public void testGetRndSalary() {
        int salary = Worker.getRndSalary();
        assert(salary >= 10 && salary <= 100000);
    }

    @Test
    public void testGetRndMonthNumber() {
        int monthNumber = Worker.getRndMonthNumber();
        assert(monthNumber >= 1 && monthNumber <= 12);
    }

    @Test
    public void testGetRndPassportNumber() {
        String passportNumber = Worker.getRndPassportNumber();
        assert(passportNumber.split(" ").length == 3);
    }

    @Test
    public void getCorre() {
        String passportNumber1 = Worker.getCorrectRecord("a", 1, 1);
        String passportNumber2 = Worker.getCorrectRecord("a", "1", "1");
        assert(passportNumber1.equals(passportNumber2));
    }
}
