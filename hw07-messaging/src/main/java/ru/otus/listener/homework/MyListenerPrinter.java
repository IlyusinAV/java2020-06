package ru.otus.listener.homework;

import ru.otus.MyMessage;

public class MyListenerPrinter implements MyListener {
    @Override
    public void onUpdated(MyMessage oldMsg, MyMessage newMsg) {
        var logString = String.format("oldMsg:%s, newMsg:%s", oldMsg, newMsg);
        System.out.println(logString);
    }
}
