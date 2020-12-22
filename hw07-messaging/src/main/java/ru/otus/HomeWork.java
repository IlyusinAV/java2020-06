package ru.otus;

import ru.otus.handler.homework.MyComplexProcessor;
import ru.otus.listener.homework.MyListenerPrinter;
import ru.otus.processor.homework.MyProcessorExchangeFields;
import ru.otus.processor.homework.MyProcessorTee;
import ru.otus.processor.homework.MyProcessorThrowException;

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
        var processors = List.of(new MyProcessorExchangeFields(),
                new MyProcessorThrowException(new MyProcessorTee()));

        var complexProcessor = new MyComplexProcessor(processors, (ex) -> {});
        var listenerPrinter = new MyListenerPrinter();
        complexProcessor.addListener(listenerPrinter);

        var message = new MyMessage.MyBuilder()
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
