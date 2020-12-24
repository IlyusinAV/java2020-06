package ru.otus.handler.homework;

import ru.otus.MyMessage;
import ru.otus.listener.homework.MyListener;
import ru.otus.processor.homework.MyProcessor;
import ru.otus.processor.homework.exceptions.EvenSecondException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyComplexProcessor implements MyHandler{
    private final List<MyListener> listeners = new ArrayList<>();
    private final List<MyProcessor> processors;
    private final Consumer<Exception> errorHandler;

    public MyComplexProcessor(List<MyProcessor> processors, Consumer<Exception> errorHandler) {
        this.processors = processors;
        this.errorHandler = errorHandler;
    }

    @Override
    public MyMessage handle(MyMessage msg) throws Throwable {
        MyMessage newMsg = msg;
        for (MyProcessor pros : processors) {
            try {
                newMsg = pros.process(newMsg);
            } catch (Exception ex) {
                throw new EvenSecondException(ex.getMessage());
            }
        }
        notify(msg, newMsg);
        return newMsg;
    }

    @Override
    public void addListener(MyListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(MyListener listener) {
        listeners.remove(listener);
    }

    private void notify(MyMessage oldMsg, MyMessage newMsg) {
        listeners.forEach(listener -> {
            try {
                listener.onUpdated(oldMsg, newMsg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
