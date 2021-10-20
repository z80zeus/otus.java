package ru.otus.processor;

import ru.otus.model.Message;

/**
 * Этот процессор в соответствии с заданием переставляет местами значения полей field11 и field12.
 */
public class ProcessorSwap implements Processor {

    /**
     * Функция интерфейса Processor производит обработку сообщения и возвращает новое.
     * В данном классе функция создаёт новое сообщение на основе переданного параметром.
     * У нового сообщения значения полей field11 и field12 будут переставлены местами.
     * @param message Обрабатываемое сообщение.
     * @return Копия исходного сообщения с переставленными значениями полей field11 и field12.
     */
    @Override
    public Message process(Message message) {
        final var messageBuilder = message.toBuilder();
        messageBuilder.field11(message.getField12());
        messageBuilder.field12(message.getField11());
        return messageBuilder.build();
    }
}
