package atm.view;

import java.util.function.Consumer;

/**
 * Класс главного меню.
 */
public class ViewLogout implements ViewInterface {

    @Override
    public void show() {
        System.out.println("Bye");
        consumer.accept(0);
    }

    @Override
    public void hide() {
        System.out.println("=========================");
    }

    protected ViewLogout(Object consumer) {
        if (!(consumer instanceof Consumer))
            throw new ClassCastException("ViewLogout: controller does not implements Consumer interface");
        @SuppressWarnings("unchecked") final var consumer_ = (Consumer<Integer>) consumer;
        this.consumer = consumer_;
    }

    private final Consumer<Integer> consumer;
}
