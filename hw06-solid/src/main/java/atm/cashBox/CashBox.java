package atm;

public class CashBox {
    public static CashBoxInterface createCashBox(@SuppressWarnings("unused") ConfigInterface cfg) {
        return new CashBoxDefault();
    }
}