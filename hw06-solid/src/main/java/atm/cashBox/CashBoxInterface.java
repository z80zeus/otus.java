package atm;

public interface CashBoxInterface {

    void giveOut(BigInteger cash);
    void open();
    Optional<BigInteger> takeIn();

}