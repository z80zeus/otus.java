package atm.cashBox;

import atm.config.Config;

import java.math.BigInteger;
import java.util.*;

/**
 * Класс, объект которого управляет сейфом банкомата.
 */
public class CashBoxImplDefault implements CashBox {

    /**
     * Выдача наличных.
     * Функция:
     * - выясняет, какими банкнотами следует выдать сумму,
     * - проверяет наличие необходимого количества банкнот в ячейках и если необходимое количество банкнот присутствует,
     * - даёт команду ячейкам выдать банкноты.
     * @param cash Сумма, которую следует выдать.
     * @throws IllegalStateException Недостаточно банкнот для выдачи суммы.
     * @throws IllegalArgumentException Сумма не кратная номиналам банкнот.
     */
    @Override
    public void giveOut(BigInteger cash) {
        final var bankNotesToGiveOut = new TreeMap<Integer, Integer>();

        for (var bankNote : banknoteBoxes.keySet()) {
            var num = cash.divide(BigInteger.valueOf(bankNote));
            if (!num.equals(BigInteger.valueOf(0)))
                if (banknoteBoxes.get(bankNote).getBanknotesNumber() >= num.longValue()) {
                    bankNotesToGiveOut.put(bankNote, num.intValue());
                }
                else
                    throw new IllegalStateException("Not enough banknotes");
            cash = cash.remainder(BigInteger.valueOf(bankNote));
        }
        if (!cash.equals(BigInteger.ZERO))
            throw new IllegalArgumentException("Bad value.");

        for (var banknote2number: bankNotesToGiveOut.entrySet()) {
            final var bankNote = banknote2number.getKey();
            final var num = banknote2number.getValue();
            // Этот вывод - не штатный, а отладочный, для примера. Иллюстрирует набор купюр сейфом.
            // Поэтому находится здесь, а не в экранной форме.
            System.out.println("Bank note " + bankNote + ": " + num);
            banknoteBoxes.get(bankNote).ejectBanknotes(num);
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
        System.out.println("Cash is rejected.");
    }

    /**
     * Приём наличных.
     * @return Внесённая пользователем сумма или пустой Optional, если ничего не было внесено.
     */
    @Override
    public Optional<BigInteger> takeIn() {
        var sum = 0;
        var rnd = new Random();
        for (var bankNote: banknoteBoxes.keySet()) {
            var num = rnd.nextInt(10);
            sum += bankNote * num;

            // Этот вывод в консоль не является частью пользовательского интерфейса банкомата.
            // Этот вывод просто иллюстрирует работу купюроприёмника, поэтому находится здесь, а не в View.
            System.out.println("Cash: " + bankNote + " = " + num);
        }

        // Этот вывод в консоль не является частью пользовательского интерфейса банкомата.
        // Этот вывод просто иллюстрирует работу купюроприёмника, поэтому находится здесь, а не в View.
        System.out.println("Total: " + sum);

        return Optional.ofNullable(sum != 0? BigInteger.valueOf(sum) : null);
    }

    /**
     * Конструктор на основе конфигурационных данных создаёт ячейки для банкнот.
     * @param cfg Объект конфигурации проекта.
     */
    protected CashBoxImplDefault(Config cfg) throws IllegalArgumentException {
       banknoteBoxes = BanknoteBoxService.createBanknoteBoxes(cfg);
    }

    /**
     * Словарь сопоставления номиналов купюр ячейкам, в которых эти номиналы хранятся.
     */
    private final Map<Integer, BanknoteBox> banknoteBoxes;
}
