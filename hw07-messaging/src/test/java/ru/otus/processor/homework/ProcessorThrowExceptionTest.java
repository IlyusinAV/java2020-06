package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class ProcessorThrowExceptionTest {

    @Test
    @DisplayName("Тестируем исключение в четную секунду")
    void handleExceptionTest() {
        //before
        var message = new Message.Builder(1L).field13(new ObjectForMessage()).build();

        var currentSecond = mock(CurrentSecondProvider.class);
        when(currentSecond.getCurrentSecond()).thenReturn(2);

        var processor = new ProcessorThrowException(currentSecond);

        //test
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> processor.process(message));

        //after
    }
}
