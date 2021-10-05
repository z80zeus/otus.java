package atm.view;

import java.util.concurrent.Callable;

public class ViewError implements ViewInterface {

    @Override
    public void show() {
        System.out.println("Error: " + msg);
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

     protected ViewError(Object controller, Object... args) {
        if (!(controller instanceof Callable))
            throw new ClassCastException("ViewBalance: controller does not implements Callable interface");
        @SuppressWarnings("unchecked") final var controller_ = (Callable<Void>) controller;
        this.controller = controller_;

        if (!(args[0] instanceof String))
            throw new ClassCastException("First of varArgs is not instance of String");

        msg = (String) args[0];
    }

    private final Callable<Void> controller;
    private final String msg;
}
