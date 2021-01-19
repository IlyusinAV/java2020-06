package ru.otus.listener.homework;

import ru.otus.Message;
import ru.otus.listener.Listener;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class ListenerHistory implements Listener {
    private HistoryStorage historyStorage;

    public ListenerHistory(HistoryStorage historyStorage) {
        this.historyStorage = historyStorage;
    }

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {

        // подумайте, как сделать, чтобы сообщения не портились
        var messageBackup = oldMsg.toBuilder().build();

        historyStorage.addHistoryItem(messageBackup);
        System.out.println("Stored:" + historyStorage.getHistoryStorage());
    }
}
