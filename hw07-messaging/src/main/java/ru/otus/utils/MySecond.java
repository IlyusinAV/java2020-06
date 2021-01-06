package ru.otus.utils;

import java.time.LocalTime;

public class MySecond {
    public static int currentSecond;

    public static void getCurrentSecond() {
        LocalTime now = LocalTime.now();
        currentSecond = now.getSecond();
    }
}
