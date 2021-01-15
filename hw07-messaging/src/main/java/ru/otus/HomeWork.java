package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryStorage;
import ru.otus.listener.homework.ListenerHistory;
import ru.otus.processor.homework.CurrentSecondProvider;
import ru.otus.processor.homework.ProcessorExchangeFields;
import ru.otus.processor.homework.ProcessorThrowException;

import java.io.*;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        var processors = List.of(new ProcessorExchangeFields(),
                new ProcessorThrowException(new CurrentSecondProvider()));

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });
        var historyStorage = new HistoryStorage();
        var listenerHistory = new ListenerHistory(historyStorage);
        complexProcessor.addListener(listenerHistory);
        var objectForMessage = new ObjectForMessage();
        objectForMessage.setData(List.of("a", "b", "c"));
        var message = new Message.Builder()
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();
        var messageBackup = message;

        // подумайте, как сделать, чтобы сообщения не портились
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(message);
            ous.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            messageBackup = (Message) ois.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        try {
            Message result = complexProcessor.handle(message);

            System.out.println(historyStorage.getHistoryStorage().get(0).getField13().getData());
            objectForMessage.setData(List.of("b", "a", "c"));
            System.out.println(historyStorage.getHistoryStorage().get(0).getField13().getData());

            System.out.println("result:" + result);
            System.out.println("backup:" + messageBackup);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        complexProcessor.removeListener(listenerHistory);
    }
}
