package ru.otus.listener.homework;

import ru.otus.MyMessage;

public interface MyListener {
    void onUpdated(MyMessage oldMsg, MyMessage newMsg);
}
