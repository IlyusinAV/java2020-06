package ru.ilyusin;

import ru.ilyusin.annotations.After;
import ru.ilyusin.annotations.Before;
import ru.ilyusin.annotations.Test;
import ru.ilyusin.entities.TestResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RunTests {
    private Class tc;
    private final List<Method> beforeMethods = new LinkedList<>();
    private final List<Method> testMethods = new LinkedList<>();
    private final List<Method> afterMethods = new LinkedList<>();
    private final TestResult result = new TestResult();

    public TestResult run(String testClass) {
        try {
            tc = Class.forName(testClass);
            readAnnotatedMethods(tc);
            for (Method testMethod : testMethods) {
                Object tobj = createTestObject(tc);
                String testName = testMethod.getName();
                runBefore(tobj, testName);
                try {
                    testMethod.invoke(tobj);
                    result.setGood(result.getGood() + 1);
                    result.setTestResult(testName, true);
                } catch (Exception tfe) {
                    result.setBad(result.getBad() + 1);
                    result.setTestResult(testName, false);
                }
                runAfter(tobj, testName);
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("file TestClass.class not found");
        }
        return result;
    }

    private void readAnnotatedMethods(Class tc) {
        Method[] methods = tc.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) beforeMethods.add(method);
            if (method.isAnnotationPresent(Test.class)) testMethods.add(method);
            if (method.isAnnotationPresent(After.class)) afterMethods.add(method);
        }
    }

    private Object createTestObject(Class tc) {
        try {
            Constructor tcc = tc.getConstructor();
            try {
                return tcc.newInstance();
            } catch (InstantiationException ie) {
            } catch (IllegalAccessException iae) {
            } catch (InvocationTargetException ite) {
            }
        } catch (NoSuchMethodException nsme) {
        }
        return null;
    }

    private void runBefore(Object tobj, String testName) {
        for (Method beforeMethod : beforeMethods) {
            try {
                beforeMethod.invoke(tobj);
                result.setTestResult("Before " + testName, true);
            } catch (Exception tfe) {
                result.setTestResult("Before " + testName, false);
            }
        }
    }

    private void runAfter(Object tobj, String testName) {
        for (Method afterMethod : afterMethods) {
            try {
                afterMethod.invoke(tobj);
                result.setTestResult("After " + testName, true);
            } catch (Exception tfe) {
                result.setTestResult("After " + testName, false);
            }
        }
    }

}