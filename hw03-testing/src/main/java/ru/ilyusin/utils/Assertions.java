package ru.ilyusin.utils;

import ru.ilyusin.exceptions.TestFailureException;

public class Assertions {
    public static void assertEquals (int f1, int f2) throws TestFailureException {
        if (f1 != f2) throw new TestFailureException("Test failure");
    }

    public static void assertSum (int f1, int f2, int s) throws TestFailureException {
        if (f1 + f2 != s) throw new TestFailureException("Test failure");
    }
}
