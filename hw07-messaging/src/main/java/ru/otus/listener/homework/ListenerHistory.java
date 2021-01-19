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
        var messageBackup = oldMsg;

        // подумайте, как сделать, чтобы сообщения не портились
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(oldMsg);
            ous.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            messageBackup = (Message) ois.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        historyStorage.addHistoryItem(messageBackup);
        System.out.println("Stored:" + historyStorage.getHistoryStorage());
    }
}
