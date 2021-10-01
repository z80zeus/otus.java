package atm.view;

import java.util.function.Consumer;

/**
 * Класс главного меню.
 */
public class ViewLogout implements ViewInterface {

    public ViewLogout(Consumer<Integer> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void show() {
        System.out.println("Bye");
        consumer.accept(0);
    }

    @Override
    public void hide() {
        System.out.println("=========================");
    }

    private final Consumer<Integer> consumer;
}
