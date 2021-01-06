package ru.otus.processor.homework;

import ru.otus.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.exceptions.EvenSecondException;
import ru.otus.utils.MySecond;

import java.util.function.Consumer;

public class ProcessorThrowException implements Processor {

    @Override
    public Message process(Message message) {
        if (MySecond.currentSecond % 2 == 0) {
            throw new RuntimeException("Even second");
        }
        return message;
    }
}
