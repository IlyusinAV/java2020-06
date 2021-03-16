package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final List<Message> historyStorage = new LinkedList<>();

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        var oldField13Backup = new ObjectForMessage();
        oldField13Backup.setData(oldMsg.getField13().getData());
        var oldMessageBackup = oldMsg.toBuilder().field13(oldField13Backup).build();
        historyStorage.add(oldMessageBackup);

        var newField13Backup = new ObjectForMessage();
        newField13Backup.setData(newMsg.getField13().getData());
        var newMessageBackup = newMsg.toBuilder().field13(newField13Backup).build();
        historyStorage.add(newMessageBackup);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        for (Message message : historyStorage) {
            if (message.getId() == id) {
                return Optional.of(message);
            }
        }
        return null;
    }
}
