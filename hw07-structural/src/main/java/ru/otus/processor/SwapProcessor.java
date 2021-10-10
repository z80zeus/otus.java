package ru.otus.processor;

import ru.otus.model.Message;

/**
 * Этот процессор в соответствии с заданием переставляет местами значения полей field11 и field12.
 */
public class SwapProcessor implements Processor {
    @Override
    public Message process(Message message) {
        final var messageBuilder = message.toBuilder();
        var tmp = message.getField11();
        messageBuilder.field11(message.getField12());
        messageBuilder.field12(tmp);
        return messageBuilder.build();
    }
}
