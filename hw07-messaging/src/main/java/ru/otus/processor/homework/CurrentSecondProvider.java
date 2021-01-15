package ru.otus.processor.homework;

import java.time.LocalTime;

public class CurrentSecondProvider {

    public int getCurrentSecond() {
        LocalTime now = LocalTime.now();
        return now.getSecond();
    }
}
