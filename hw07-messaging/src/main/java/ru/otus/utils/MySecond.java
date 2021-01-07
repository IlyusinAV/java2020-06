package ru.otus.utils;

import java.time.LocalTime;

public class MySecond {
    private static int currentSecond;

    public static int getCurrentSecond() {
        LocalTime now = LocalTime.now();
        currentSecond = now.getSecond();
        return currentSecond;
    }
}
