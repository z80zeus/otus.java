package atm;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;

public class CashBox {
    public BigInteger giveOut(BigInteger cash) {
        for (var bankNote : bankNotes) {
            var num = cash.divide(BigInteger.valueOf(bankNote));
            if (!num.equals(BigInteger.valueOf(0)))
                System.out.println("Bank note " + bankNote + ": " + num);
            cash = cash.remainder(BigInteger.valueOf(bankNote));
        }
        return cash;
    }

    public void open() {
        System.out.println("Cashbox is opened.");
    }

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

    private static final int[] bankNotes = { 5000, 1000, 500, 100, 50, 10, 5, 1 };
}
