package ru.otus.utils;

import java.time.LocalTime;

public class MySecond {
    public int currentSecond() {
        var now = LocalTime.now();
        return now.getSecond();
    }
}
