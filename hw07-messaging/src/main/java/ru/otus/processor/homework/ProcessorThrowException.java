package ru.otus.processor.homework;

import ru.otus.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.exceptions.EvenSecondException;
import ru.otus.utils.MySecond;

import java.util.function.Consumer;

public class ProcessorThrowException implements Processor {

    @Override
    public Message process(Message message) {
        if (getCurrentSecond() % 2 == 0) {
            try {
                throw new EvenSecondException("Even second");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    int getCurrentSecond() {
        var mySecond = new MySecond();
        return mySecond.currentSecond();
    }
}
