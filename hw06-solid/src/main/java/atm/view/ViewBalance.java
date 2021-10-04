package atm.view;

import java.math.BigInteger;
import java.util.concurrent.Callable;

public class ViewBalance implements ViewInterface {

    @Override
    public void show() {
        System.out.println("Balance: " + balance);
        try {
            controller.call();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void hide() {
        System.out.println("=========================");
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    protected ViewBalance(Object controller) {
        if (!(controller instanceof Callable))
            throw new ClassCastException("ViewBalance: controller does not implements Callable interface");
        @SuppressWarnings("unchecked") final var controller_ = (Callable<Void>) controller;
        this.controller = controller_;
    }

    private final Callable<Void> controller;
    private BigInteger balance;
}
