package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.MyMessage;
import ru.otus.ObjectForMessage;
import ru.otus.handler.homework.MyComplexProcessor;
import ru.otus.processor.homework.exceptions.EvenSecondException;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyProcessorThrowExceptionTest {

    @Test
    @DisplayName("Тестируем исключение в четную секунду")
    void handleExceptionTest() {
        //before
        var message = new MyMessage.MyBuilder().field13(new ObjectForMessage()).build();

        List<MyProcessor> processors = List.of(new MyProcessorThrowException(new MyProcessorTee()));

        var complexProcessor = new MyComplexProcessor(processors, (ex) -> {
            try {
                throw new EvenSecondException(ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }});

        //test
        if (System.currentTimeMillis() / 1000 % 2 != 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        assertThatExceptionOfType(EvenSecondException.class).isThrownBy(() -> complexProcessor.handle(message));

        //after
    }
}
