package atm.cashBox;

import java.math.BigInteger;
import java.util.*;

/**
 * Класс, объект которого управляет сейфом банкомата.
 */
public class CashBoxDefault implements CashBoxInterface {

    /**
     * Выдача наличных.
     * Функция:
     * - выясняет, какими банкнотами следует выдать сумму,
     * - проверяет наличие необходимого количества банкнот в ячейках и если необходимое количество банкнот присутствует,
     * - даёт команду ячейкам выдать банкноты.
     * @param cash Сумма, которую следует выдать.
     * @throws IllegalStateException Недостаточно банкнот для выдачи суммы.
     */
    @Override
    public void giveOut(BigInteger cash) {
        final var bankNotesToGiveOut = new TreeMap<Integer, Integer>();

        for (var bankNote : bankNotes) {
            var num = cash.divide(BigInteger.valueOf(bankNote));
            if (!num.equals(BigInteger.valueOf(0)))
                if (banknotesBoxes.get(bankNote).getBanknotesNumber() >= num.longValue()) {
                    bankNotesToGiveOut.put(bankNote, num.intValue());
                }
                else
                    throw new IllegalStateException("Not enough banknotes");
            cash = cash.remainder(BigInteger.valueOf(bankNote));
        }

        for (var banknote2number: bankNotesToGiveOut.entrySet()) {
            final var bankNote = banknote2number.getKey();
            final var num = banknote2number.getValue();
            // Этот вывод - не штатный, а отладочный, для примера. Иллюстрирует набор купюр сейфом.
            // Поэтому находится здесь, а не в экранной форме.
            System.out.println("Bank note " + bankNote + ": " + num);
            banknotesBoxes.get(bankNote).ejectBanknotes(num);
        }
    }

    /**
     * Открыть купюроприёмник.
     */
    @Override
    public void open() {
        // Этот вывод - не штатный, а отладочный, для примера. Иллюстрирует работу сейфа.
        // Поэтому находится здесь, а не в экранной форме.
        System.out.println("Cashbox is opened.");
    }

    /**
     * Закрыть купюроприёмник.
     */
    @Override
    public void close() {
        // Этот вывод - не штатный, а отладочный, для примера. Иллюстрирует работу сейфа.
        // Поэтому находится здесь, а не в экранной форме.
        System.out.println("Cashbox is closed.");
    }


    /**
     * Вернуть внесённые средства.
     */
    @Override
    public void reject() {
        System.out.println("Cash is returned.");
    }

    /**
     * Приём наличных.
     * @return Внесённая пользователем сумма или пустой Optional, если ничего не было внесено.
     */
    @Override
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

    protected CashBoxDefault() {
        banknotesBoxes = new HashMap<Integer, BanknoteBox>();
        for (var bankNote: bankNotes)
            banknotesBoxes.put(bankNote, new BanknoteBox(2500));
    }

    private final Map<Integer, BanknoteBox> banknotesBoxes;

    /**
     * Номиналы банкнот в обращении.
     */
    private static final int[] bankNotes = { 5000, 1000, 500, 100, 50, 10, 5, 1 };
}
