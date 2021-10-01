package atm.view;

import java.util.Scanner;
import java.util.concurrent.Callable;

@SuppressWarnings("ClassCanBeRecord")
public class ViewDeposit implements ViewInterface {
    public ViewDeposit(Callable<Void> controller) {
        this.controller = controller;
    }

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

    private final Callable<Void> controller;
}
