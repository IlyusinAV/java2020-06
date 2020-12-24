package ru.otus.processor.homework;

import ru.otus.MyMessage;
import ru.otus.processor.homework.exceptions.EvenSecondException;

public class MyProcessorExchangeFields implements MyProcessor {

    @Override
    public MyMessage process(MyMessage message) throws EvenSecondException {
        var newFieldValue = message.getField11();
        return message.toBuilder().field11(message.getField12()).field12(newFieldValue).build();
    }

}
