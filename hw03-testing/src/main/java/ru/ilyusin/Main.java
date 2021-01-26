package ru.ilyusin;

import ru.ilyusin.entities.TestResult;
import ru.ilyusin.utils.WriteResults;

class Main {
    public static void main(String... args) {
        RunTests tests = new RunTests();
        TestResult result = tests.run("ru.ilyusin.TestClass");
        WriteResults.write(result);
    }
}