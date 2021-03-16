package ru.otus.listener.homework;

import ru.otus.model.Message;

import java.util.LinkedList;
import java.util.List;

public class HistoryStorage {
    private final List<Message> historyStorage = new LinkedList<>();

    public List<Message> getHistoryStorage() {
        return historyStorage;
    }

    public void addHistoryItem(Message message) {
        this.historyStorage.add(message);
    }
}
