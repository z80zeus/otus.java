package atm.cashBox;

/**
 * Интерфейс ячейки для банкнот.
 */
public interface BanknoteBox {
    /**
     * Получить текущее количество банкнот в ячейке.
     * @return Количество банкнот в ячейке.
     */
    int getBanknotesNumber();

    /**
     * Переместить указанное количество банкнот из ячейки - в бокс для выдачи.
     * @param banknotesToEject Количество банкнот для выдачи.
     * @throws IllegalArgumentException В ячейке нет достаточного количества банкнот.
     */
    void ejectBanknotes(int banknotesToEject) throws IllegalArgumentException;
}