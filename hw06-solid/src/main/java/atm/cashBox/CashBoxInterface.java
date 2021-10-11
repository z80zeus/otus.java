package atm.cashBox;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Интерфейс сейфовой машины банкомата.
 */
public interface CashBoxInterface {

    /**
     * Выдать наличные.
     * @param cash Сумма для выдачи.
     */
    void giveOut(BigInteger cash);

    /**
     * Открыть купюроприёмник.
     */
    void open();

    /**
     * Закрыть купюроприёмник.
     */
    void close();

    /**
     * Вернуть внесённые средства.
     */
    void reject();

    /**
     * Принять наличные.
     * @return Опциональная сумма внесённых средств.
     */
    Optional<BigInteger> takeIn();

}