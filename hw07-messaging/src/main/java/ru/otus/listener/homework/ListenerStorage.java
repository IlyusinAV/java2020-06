package ru.otus.listener.homework;

import ru.otus.Message;
import ru.otus.listener.Listener;

import java.util.LinkedList;
import java.util.Queue;

public class ListenerStorage implements Listener {
    Queue<Message> messageQueue;

    public ListenerStorage() {
         messageQueue = new LinkedList<>();
    }

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        messageQueue.offer(oldMsg);
        System.out.println ("Stored:" + messageQueue.peek());
    }
}
