package ru.otus.processor.homework;

import java.time.LocalTime;

public class MySecond {

    public int getCurrentSecond() {
        LocalTime now = LocalTime.now();
        return now.getSecond();
    }
}
