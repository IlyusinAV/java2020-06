package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.Message;
import ru.otus.ObjectForMessage;
import ru.otus.utils.MySecond;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

public class ProcessorThrowExceptionTest {

    @Test
    @DisplayName("Тестируем исключение в четную секунду")
    void handleExceptionTest() {
        //before
        var message = new Message.Builder().field13(new ObjectForMessage()).build();

        var processor = spy(ProcessorThrowException.class);
        when (processor.getCurrentSecond()).thenReturn(2);

        //test
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> processor.process(message));

        //after
    }
}
