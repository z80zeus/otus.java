package atm.cashBox;

import java.util.Random;

/**
 * Ячейка для банкнот одного номинала.
 */
public class BanknoteBoxImpl implements BanknoteBox {

    /**
     * Получить текущее количество банкнот в ячейке.
     * @return Количество банкнот в ячейке.
     */
    @Override
    public long getBanknotesNumber() {
        // В реальном проекте количество банкнот считывается из аппаратного контроллера ячейки,
        // используя deviceId.
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
     * В конструкторе указывается аппаратный идентификатор контроллера ячейки во внутренней сети банкомата.
     * @param deviceId Аппаратный идентификатор контроллера ячейки.
     */
    public BanknoteBoxImpl(long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Аппаратный идентификатор ячейки во внутренней сети банкомата.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final long deviceId;

    /**
     * В реальном проекте этого поля не будет.
     * Класс будет считывать количество банкнот из аппаратного контроллера ячейки, используя deviceId.
     */
    private int banknotesNumber = Math.abs(new Random().nextInt());
}