package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.Message;
import ru.otus.ObjectForMessage;
import ru.otus.utils.MySecond;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProcessorThrowExceptionTest {

    @Test
    @DisplayName("Тестируем исключение в четную секунду")
    void handleExceptionTest() {
        //before
        var message = new Message.Builder().field13(new ObjectForMessage()).build();

        MySecond.currentSecond = 2;

        var processor = new ProcessorThrowException();
        //test
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> processor.process(message));

        //after
    }
}
