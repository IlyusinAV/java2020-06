package ru.otus.processor.homework;

import ru.otus.MyMessage;
import ru.otus.processor.homework.exceptions.EvenSecondException;

public class MyProcessorThrowException implements MyProcessor {
    private final MyProcessor processor;

    public MyProcessorThrowException(MyProcessor processor) {
        this.processor = processor;
    }

    @Override
    public MyMessage process(MyMessage message) {
        try {
            long currentTime = System.currentTimeMillis() * 1000;
            if (currentTime % 2 == 0) throw new EvenSecondException("Even second");
        } finally {
            return processor.process(message);
        }
    }
}
