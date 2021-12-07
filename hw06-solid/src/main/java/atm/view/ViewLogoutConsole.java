package atm.view;

import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Экранная форма "Выход из системы".
 */
public class ViewLogoutConsole implements View {

    /**
     * Показать экранную форму.
     * Выводит в консоль прощальное сообщение, ожидает ввода строки и вызывает контроллер, информируя его о
     * завершении операции.
     */
    @Override
    public void show() {
        System.out.println("Bye!");
        System.out.print("Press Enter:");
        new Scanner(System.in).nextLine();
        try {
            controller.call();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Скрыть экранную форму.
     */
    @Override
    public void hide() {
        System.out.println("=========================");
    }

    /**
     * Конструктор сохраняет переданный объект-контроллер банкомата, с которым будет работать далее.
     * Для этого класса контроллером может быть что угодно, реализующее интерфейс Callable, поэтому конструктор
     * проверяет, что переданный объект реализует этот интерфейс.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param controller Контроллер, с которым взаимодействует экранная форма.
     * @throws ClassCastException Если контроллер не реализует интерфейс Callable или args[0] не является строкой.
     */
    protected ViewLogoutConsole(Object controller) {

        if (!(controller instanceof Callable))
            throw new ClassCastException("ViewLogoutConsole: controller does not implements Callable interface");

        @SuppressWarnings("unchecked") final var controller_ = (Callable<Void>) controller;
        this.controller = controller_;
    }

    /**
     * Контроллер банкомата с которым работает экранная форма.
     */
    private final Callable<Void> controller;
}
