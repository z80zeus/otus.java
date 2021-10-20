package ru.otus.processor;

import ru.otus.model.Message;

/**
 * Процессор, выбрасывающий исключение, если сообщение поступило в чётную секунду.
 */
@SuppressWarnings("ClassCanBeRecord")
public class ProcessorEvenSecondException implements Processor {

    /**
     * Метод обработки сообщения. Выбрасывает RuntimeException, если сообщение поступило в чётную секунду.
     * В противном случае - возвращает сообщение без обработки.
     * @param message Сообщение для обработки.
     * @return Данный процессор возвращает то же сообщение, которое ему передали параметром.
     * @throws RuntimeException Сообщение пришло в чётную секунду времени.
     */
    @Override
    public Message process(Message message) throws RuntimeException {
        if (dateTimeProvider.getDateTime().getSecond() % 2 == 0)
            throw new RuntimeException("Even second exception!");
        return message;
    }

    /**
     * В конструкторе инъектится объект, предоставляющий сервис получения даты-времени.
     * Этот объект используется в методе process вместо JavaAPI функций работы с датой-временем.
     * @param dateTimeProvider Объект, реализующий интерфейс получения даты-времени.
     */
    public ProcessorEvenSecondException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    /**
     * Провайдер даты-времени инъектится при создании процессора и используется в методе process.
     */
    private final DateTimeProvider dateTimeProvider;
}
