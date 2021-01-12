package ru.otus.processor.homework;

import ru.otus.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowException implements Processor {
    private MySecond currentSecond;

    public ProcessorThrowException(MySecond currentSecond) {
        this.currentSecond = currentSecond;
    }

    @Override
    public Message process(Message message) {
        if (currentSecond.getCurrentSecond() % 2 == 0) {
            throw new RuntimeException("Even second");
        }
        return message;
    }

}
