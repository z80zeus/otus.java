package atm.cashBox;

import atm.config.Config;

/**
 * Служебный класс
 */
public class CashBoxService {
    public static CashBox createCashBox(Config cfg) {
        return new CashBoxImplDefault(cfg);
    }
}