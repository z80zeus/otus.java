package atm.view;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Класс главного меню.
 */
public class ViewLogin implements ViewInterface {

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

    protected ViewLogin(Object consumer) {
        if (!(consumer instanceof Consumer))
            throw new ClassCastException("ViewLogin: controller does not implements Consumer interface");
        @SuppressWarnings("unchecked") final var consumer_ = (Consumer<String>) consumer;
        this.consumer = consumer_;
    }

    private final Consumer<String> consumer;
}
