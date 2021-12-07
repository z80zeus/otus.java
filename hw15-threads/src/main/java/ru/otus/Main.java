package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Название одного потока (не важно какого).
     * Так он будет представляться в логгере,
     * а главное - помечать очерёдность вывода в лог значений (см.lastThreadName).
     */
    private static final String thread1Name = "Поток 1";

    /**
     * Название другого потока (не важно какого).
     * Так он будет представляться в логгере,
     * а главное - помечать очерёдность вывода в лог значений (см.lastThreadName).
     */
    private static final String thread2Name = "Поток 2";

    /**
     * Название потока, который последним вывел в лог число.
     * Значит следующим должен вывести число другой поток.
     * Переменная инициализируется именем второго потока, чтобы первым своё число вывел поток 1.
     */
    private static String lastThreadName = thread2Name;

    /**
     * Точка входа в программу.
     * Запускает два потока и всё. Остальная работа происходит в потоках.
     * @param args Параметры командной строки.
     */
    public static void main(String[] args) {
        new Thread(Main::task, thread1Name).start();
        new Thread(Main::task, thread2Name).start();
    }

    /**
     * Функция, выполняющаяся в отдельном потоке.
     * Выводит в лог бесконечную последовательность чисел:
     * 1->2->3->4->5->6->7->8->9->10->9->8->7->6->5->4->3->2->1->2->...
     * Ожидает своей очереди, ориентируясь на значение переменной lastThreadName:
     * в эту переменную пишет своё имя поток только что отправивший число в лог.
     */
    private static void task() {
        final var thisThreadName = Thread.currentThread().getName();
        final var MIN = 1;
        final var MAX = 10;
        final var SLEEP = 1000; // После вывода каждого числа поток спит SLEEP мсек.
        var i = MIN;        // "Счётчик цикла"
        var add = 1;            // Значение, прибавляемое к i: меняется 1/-1/1/-1 в зависимости от направления счёта.
        try {
            while (true) {
                synchronized (Main.class) { // Синхронизация на классе, чтобы оставаться в статическом контексте.
                    while (thisThreadName.equals(lastThreadName)) { // Предупреждение спонтанного пробуждения из wait.
                        Main.class.wait();
                    }
                    logger.info("{}", i);
                    lastThreadName = thisThreadName;

                    //noinspection BusyWait
                    Thread.sleep(SLEEP);
                    Main.class.notify();

                    // Считаем вверх или вниз: смена направления на MAX или MIN значениях.
                    if (i >= MAX) add = -1;
                    if (i <= MIN) add = 1;
                    i += add;
                }
            }
        } catch (InterruptedException e) {
            // В данном примере взводить флаг прерывания потока необязательно - читать его никто не будет,
            // но чтобы помнить, об этом моменте - пусть будет.
            Thread.currentThread().interrupt();
        }
    }
}
