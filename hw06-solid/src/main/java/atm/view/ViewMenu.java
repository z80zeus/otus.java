package atm.view;

import atm.Operations;

import java.util.Scanner;
import java.util.function.Consumer;

public class ViewMenu implements ViewInterface{

    @Override
    public void show() {
        var streamReader = new Scanner(System.in);
        while(true) {
            System.out.println("Select operation: ");
            System.out.println("1. Logout.");
            System.out.println("2. Balance.");
            System.out.println("3. Cash.");
            System.out.println("4. Deposit.");
            var operation = streamReader.nextInt();

            switch (operation) {
                case 1 -> {
                    consumer.accept(Operations.Logout);
                    return;
                }
                case 2 -> {
                    consumer.accept(Operations.Balance);
                    return;
                }
                case 3 -> {
                    consumer.accept(Operations.Cash);
                    return;
                }
                case 4 -> {
                    consumer.accept(Operations.Deposit);
                    return;
                }
            }
        }
    }

    @Override
    public void hide() {
        System.out.println("=========================");
    }

    protected ViewMenu(Object consumer) {
        if (!(consumer instanceof Consumer))
            throw new ClassCastException("ViewMenu: controller does not implements Consumer interface");
        @SuppressWarnings("unchecked") final var consumer_ = (Consumer<Operations>) consumer;
        this.consumer = consumer_;
    }

    private final Consumer<Operations> consumer;
}
