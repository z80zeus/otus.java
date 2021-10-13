package atm.cashBox;

/**
 * Ячейка для банкнот одного номинала.
 */
public class BanknoteBox implements BanknoteBoxInterface {

    /**
     * Получить текущее количество банкнот в ячейке.
     * @return Количество банкнот в ячейке.
     */
    @Override
    public long getBanknotesNumber() {
        return banknotesNumber;
    }

    /**
     * Переместить из ячейки в бокс для выдачи указанное количество банкнот.
     * @param banknotesToEject Количество банкнот для выдачи.
     * @throws IllegalArgumentException В ячейке нет достаточного количества банкнот.
     */
    @Override
    public void ejectBanknotes(long banknotesToEject) throws IllegalArgumentException {
        if (banknotesToEject > banknotesNumber) throw new IllegalArgumentException("Not enough banknotes");
        banknotesNumber -= banknotesToEject;
    }

    /**
     * В конструкторе указывается количество банкнот, заложенных в установленную в банкомат ячейку.
     * @param banknotesNumber Количество банкнот.
     */
    public BanknoteBox(long banknotesNumber) {
        this.banknotesNumber = banknotesNumber;
    }

    /**
     * Количество хранящихся в ячейке банкнот.
     */
    private long banknotesNumber;
}