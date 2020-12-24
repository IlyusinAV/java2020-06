package ru.otus.processor.homework;

import ru.otus.MyMessage;
import ru.otus.processor.homework.exceptions.EvenSecondException;

public interface MyProcessor {
    MyMessage process (MyMessage message) throws EvenSecondException;
}
