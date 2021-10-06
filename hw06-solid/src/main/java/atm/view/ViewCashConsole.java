package atm.view;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Экранная форма запроса через консоль суммы снятия наличных
 */
public class ViewCashConsole implements ViewInterface {

    /**
     * Показать экранную форму.
     * Выводит в консоль вопрос о сумме наличных и передаёт введённую сумму контролеру.
     */
    @Override
    public void show() {
        var streamReader = new Scanner(System.in);
        System.out.print("Enter the cash volume or just push ENTER to cancel operation: ");
        controller.accept(Optional.ofNullable(streamReader.nextBigInteger()));
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
     * Для этого класса контроллером может быть что угодно, реализующее интерфейс Consumer, поэтому конструктор
     * проверяет, что переданный объект реализует этот интерфейс.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param consumer Контроллер, с которым взаимодействует экранная форма.
     * @throws ClassCastException Если контроллер не реализует интерфейс Consumer.
     */
    protected ViewCashConsole(Object consumer) throws ClassCastException {

        if (!(consumer instanceof Consumer))
            throw new ClassCastException("ViewCashConsole: controller does not implements Consumer interface");

        @SuppressWarnings("unchecked") final var controller_ = (Consumer<Optional<BigInteger>>) consumer;
        this.controller = controller_;
    }

    /**
     * Контролер банкомата с которым работает данная экранная форма.
     */
    private final Consumer<Optional<BigInteger>> controller;
}
