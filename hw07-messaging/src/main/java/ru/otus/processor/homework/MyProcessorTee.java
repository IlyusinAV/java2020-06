package ru.otus.processor.homework;

import ru.otus.MyMessage;
import ru.otus.processor.homework.exceptions.EvenSecondException;

public class MyProcessorTee implements MyProcessor {

    @Override
    public MyMessage process(MyMessage message) throws EvenSecondException {
        // Предполагается, что message будет сериализован
        return message;
    }
}