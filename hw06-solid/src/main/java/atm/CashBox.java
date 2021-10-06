package atm;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Random;

/**
 * Класс, объект которого управляет сейфом банкомата.
 */
public class CashBox {

    /**
     * Выдача наличных.
     * @param cash Сумма, которую следует выдать.
     */
    public void giveOut(BigInteger cash) {
        for (var bankNote : bankNotes) {
            var num = cash.divide(BigInteger.valueOf(bankNote));
            if (!num.equals(BigInteger.valueOf(0)))
                // Этот вывод - не штатный, а отладочный, для примера. Иллюстрирует набор купюр сейфом.
                // Поэтому находится здесь, а не в экранной форме.
                System.out.println("Bank note " + bankNote + ": " + num);
            cash = cash.remainder(BigInteger.valueOf(bankNote));
        }
    }

    /**
     * Открыть купюроприёмник.
     */
    public void open() {
        // Этот вывод - не штатный, а отладочный, для примера. Иллюстрирует работу сейфа.
        // Поэтому находится здесь, а не в экранной форме.
        System.out.println("Cashbox is opened.");
    }

    /**
     * Приём наличных.
     * @return Внесённая пользователем сумма или пустой Optional, если ничего не было внесено.
     */
    public Optional<BigInteger> takeIn() {
        var sum = 0;
        var rnd = new Random();
        for (var bankNote: bankNotes) {
            var num = rnd.nextInt(10);
            sum += bankNote * num;
            // Этот вывод в консоль не является частью пользовательского интерфейса банкомата.
            // Этот вывод просто иллюстрирует работу купюроприёмника, поэтому находится здесь, а не в View.
            System.out.println("Cash: " + bankNote + " = " + num);
        }
        // Этот вывод в консоль не является частью пользовательского интерфейса банкомата.
        // Этот вывод просто иллюстрирует работу купюроприёмника, поэтому находится здесь, а не в View.
        System.out.println("Total: " + sum);
        return Optional.of(BigInteger.valueOf(sum));
    }

    /**
     * Номиналы банкнот в обращении.
     */
    private static final int[] bankNotes = { 5000, 1000, 500, 100, 50, 10, 5, 1 };
}
