package demo.spring.instance.unit_test.junit.business;

import java.math.BigDecimal;

public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("/ by zero");
        }
        return new BigDecimal(a).divide(new BigDecimal(b)).intValue();
    }

    public int subtract(int a, int b) {
        return a - b;
    }
}