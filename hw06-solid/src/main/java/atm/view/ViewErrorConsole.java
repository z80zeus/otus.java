package atm.view;

import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Экранная форма, показывающая сообщение об ошибке в консоль.
 */
public class ViewErrorConsole implements View {

    /**
     * Показать экранную форму.
     * Выводит в консоль сообщение об ошибке переданное в конструкторе, ждёт нажатие Enter и вызывает контроллер,
     * информируя о завершении операции.
     */
    @Override
    public void show() {
        System.out.println("Error: " + errorMsg);
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
     * @param args Аргумент переменной длины. Конструктор воспринимает только нулевой, интерпретируя его как строку -
     *            сообщение об ошибке.
     * @throws ClassCastException Если контроллер не реализует интерфейс Callable или args[0] не является строкой.
     */
    protected ViewErrorConsole(Object controller, Object... args) throws ClassCastException {

        if (!(controller instanceof Callable))
            throw new ClassCastException("ViewErrorConsole: controller does not implements Callable interface");

        @SuppressWarnings("unchecked") final var controller_ = (Callable<Void>) controller;
        this.controller = controller_;

        if (!(args[0] instanceof String))
            throw new ClassCastException("ViewErrorConsole: First of varArgs is not instance of String");

        errorMsg = (String) args[0];
    }

    /**
     * Контролер банкомата с которым работает данная экранная форма.
     */
    private final Callable<Void> controller;

    /**
     * Сообщение об ошибке, которое будет выведено в методе show.
     */
    private final String errorMsg;
}
