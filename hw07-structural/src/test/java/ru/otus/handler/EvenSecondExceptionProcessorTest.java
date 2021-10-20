package ru.otus.handler;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.DateTimeProvider;
import ru.otus.processor.ProcessorEvenSecondException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class EvenSecondExceptionProcessorTest {

    @Test
    void noExceptionTest() {
        final var processor = new ProcessorEvenSecondException(new DateTimeProviderOdd());
        processor.process(new Message.Builder(0).build());
    }


    @Test
    void exceptionTest() {
        final var processor = new ProcessorEvenSecondException(new DateTimeProviderEven());
        assertThatThrownBy(() -> processor.process(new Message.Builder(0).build())).isInstanceOf(RuntimeException.class);
    }

    static class DateTimeProviderEven implements DateTimeProvider {

        @Override
        public LocalDateTime getDateTime() {
            return LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.ofHours(0));
        }
    }

    static class DateTimeProviderOdd implements DateTimeProvider {

        @Override
        public LocalDateTime getDateTime() {
            return LocalDateTime.ofEpochSecond(1, 0, ZoneOffset.ofHours(0));
        }
    }
}

