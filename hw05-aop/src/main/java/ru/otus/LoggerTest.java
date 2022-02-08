package ru.otus;

import ru.otus.annotations.Log;

public class LoggerTest {
    public static void main(String[] args) {
        final var job = new Job();
        job.calculation(1);
        job.calculation(1, 2);
        job.calculation(1, 2, "3");
    }

    static class Job {
        @Log
        public void calculation(int param1) {}

        @Log
        public void calculation(int param1, int param2) {}

        @Log
        public void calculation(int param1, int param2, String param3) {}
    }
}
