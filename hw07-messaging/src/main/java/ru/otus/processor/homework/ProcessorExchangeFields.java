package ru.otus.processor.homework;

import ru.otus.Message;
import ru.otus.processor.Processor;

public class ProcessorExchangeFields implements Processor {

    @Override
    public Message process(Message message) {
        var newFieldValue = message.getField11();
        return message.toBuilder().field11(message.getField12()).field12(newFieldValue).build();
    }

}
