package atm.view;

import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Экранная форма, работающая с пользователем при внесении наличных на счёт.
 */
public class ViewDepositConsole implements View {

    /**
     * Показать экранную форму.
     * Ожидает от пользователя нажатия Enter и не обращая внимания на введённые данные - вызывает контроллер, информируя
     * его о том, что деньги помещены в купюроприёмник.
     */
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

    /**
     * Скрыть экранную форму.
     */
    @Override
    public void hide() {
        System.out.println("======================");
    }

    /**
     * Конструктор сохраняет переданный объект-контроллер банкомата, с которым будет работать далее.
     * Для этого класса контроллером может быть что угодно, реализующее интерфейс Callable, поэтому конструктор
     * проверяет, что переданный объект реализует этот интерфейс.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param controller Контроллер, с которым взаимодействует экранная форма.
     * @throws ClassCastException Если контроллер не реализует интерфейс Callable.
     */
    protected ViewDepositConsole(Object controller) throws ClassCastException {

        if (!(controller instanceof Callable))
            throw new ClassCastException("ViewDepositConsole: controller does not implements Callable interface");

        @SuppressWarnings("unchecked") final var controller_ = (Callable<Void>) controller;
        this.controller = controller_;
    }

    /**
     * Контроллер банкомата с которым работает экранная форма.
     */
    private final Callable<Void> controller;
}
