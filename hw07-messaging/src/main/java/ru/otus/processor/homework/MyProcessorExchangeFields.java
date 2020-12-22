package ru.otus.processor.homework;

import ru.otus.MyMessage;

public class MyProcessorExchangeFields implements MyProcessor {

    @Override
    public MyMessage process(MyMessage message) {
        var newFieldValue = message.getField11();
        return message.toBuilder().field11(message.getField12()).field12(newFieldValue).build();
    }

}
