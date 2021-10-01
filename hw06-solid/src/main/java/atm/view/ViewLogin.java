package atm.view;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Класс главного меню.
 */
public class ViewLogin implements ViewInterface {

    public ViewLogin(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void show() {
        System.out.print("Insert card and enter PIN-code: ");
        var streamReader = new Scanner(System.in);
        var pin = streamReader.next();
        consumer.accept(pin);
    }

    @Override
    public void hide() {
        System.out.println("=========================");
    }

    private final Consumer<String> consumer;
}
