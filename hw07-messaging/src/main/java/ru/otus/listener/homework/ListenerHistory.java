package ru.otus.listener.homework;

import ru.otus.Message;
import ru.otus.ObjectForMessage;
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
        var oldField13Backup = new ObjectForMessage();
        oldField13Backup.setData(oldMsg.getField13().getData());
        var oldMessageBackup = oldMsg.toBuilder().field13(oldField13Backup).build();
        historyStorage.addHistoryItem(oldMessageBackup);

        var newField13Backup = new ObjectForMessage();
        newField13Backup.setData(newMsg.getField13().getData());
        var newMessageBackup = newMsg.toBuilder().field13(newField13Backup).build();
        historyStorage.addHistoryItem(newMessageBackup);

        System.out.println("Stored:" + historyStorage.getHistoryStorage());
    }
}
