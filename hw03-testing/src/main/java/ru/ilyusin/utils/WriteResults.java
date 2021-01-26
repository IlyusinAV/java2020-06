package ru.ilyusin.utils;

import ru.ilyusin.entities.TestResult;

import java.util.Map;

public class WriteResults {
    public static void write(TestResult result) {
        Map<String, Boolean> testResults = result.getTestResult();
        for (Map.Entry<String,Boolean> testResult : testResults.entrySet()){
            System.out.println(testResult.getKey() + (testResult.getValue() ? " OK" : " Failed"));
        }
        System.out.println("Passed: " + result.getGood());
        System.out.println("Failed: " + result.getBad());
        int total = result.getBad() + result.getGood();
        System.out.println("Total: " + total);
    }
}
