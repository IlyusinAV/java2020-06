package ru.otus.processor.homework;

import ru.otus.MyMessage;

public class MyProcessorTee implements MyProcessor {

    @Override
    public MyMessage process(MyMessage message) {
        return message;
    }
}