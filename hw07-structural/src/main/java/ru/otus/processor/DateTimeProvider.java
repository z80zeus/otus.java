package ru.otus.processor;

import java.time.LocalDateTime;

/**
 * Интерфейс объектов, обеспечивающих доступ к дате-времени.
 */
public interface DateTimeProvider {
    /**
     * Получить текущую дату-время с локальной временной зоной.
     * @return Дата-время с местной локалью.
     */
    LocalDateTime getDateTime();
}
