package atm.cashBox;

import atm.config.ConfigInterface;

/**
 * Служебный класс
 */
public class CashBox {
    public static CashBoxInterface createCashBox(@SuppressWarnings("unused") ConfigInterface cfg) {
        return new CashBoxDefault();
    }
}