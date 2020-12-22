package ru.otus.handler.homework;

import ru.otus.MyMessage;
import ru.otus.listener.homework.MyListener;

public interface MyHandler {
    MyMessage handle(MyMessage msg);

    void addListener(MyListener listener);
    void removeListener(MyListener listener);
}