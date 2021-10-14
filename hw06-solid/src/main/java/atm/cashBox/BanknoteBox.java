package atm.cashBox;

/**
 * Интерфейс ячейки для банкнот.
 */
public interface BanknoteBox {
    /**
     * Получить текущее количество банкнот в ячейке.
     * @return Количество банкнот в ячейке.
     */
    long getBanknotesNumber();

    /**
     * Переместить из ячейки в бокс для выдачи указанное количество банкнот.
     * @param banknotesToEject Количество банкнот для выдачи.
     * @throws IllegalArgumentException В ячейке нет достаточного количества банкнот.
     */
    void ejectBanknotes(long banknotesToEject) throws IllegalArgumentException;
}