package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowException implements Processor {
    private final CurrentSecondProvider localTime;

    public ProcessorThrowException(CurrentSecondProvider localTime) {
        this.localTime = localTime;
    }

    @Override
    public Message process(Message message) {
        if (localTime.getCurrentSecond() % 2 == 0) {
            throw new RuntimeException("Even second");
        }
        return message;
    }

}
