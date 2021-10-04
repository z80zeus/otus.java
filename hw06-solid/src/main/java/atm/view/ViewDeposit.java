package atm.view;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class ViewDeposit implements ViewInterface {

    @Override
    public void show() {
        System.out.print("Insert bank notes, then press Enter:");
        new Scanner(System.in).nextLine();
        try {
            controller.call();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void hide() {
        System.out.println("======================");
    }

    protected ViewDeposit(Object controller) {
        if (!(controller instanceof Callable))
            throw new ClassCastException("ViewDeposit: controller does not implements Callable interface");
        @SuppressWarnings("unchecked") final var controller_ = (Callable<Void>) controller;
        this.controller = controller_;
    }

    private final Callable<Void> controller;
}
