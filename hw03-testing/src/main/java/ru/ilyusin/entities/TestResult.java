package ru.ilyusin.entities;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestResult {
    private final Map<String, Boolean> testResult = new LinkedHashMap<>();
    private int good;
    private int bad;

    public Map<String, Boolean> getTestResult() {
        return testResult;
    }

    public void setTestResult(String testCase, boolean result) {
        testResult.put(testCase, result);
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

}
