package ru.otus.handler.homework;

import ru.otus.MyMessage;
import ru.otus.listener.homework.MyListener;

public interface MyHandler {
    MyMessage handle(MyMessage msg) throws Throwable;

    void addListener(MyListener listener);
    void removeListener(MyListener listener);
}
