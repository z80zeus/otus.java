package atm.view;

import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * Экранная форма показа баланса в консоли.
 */
public class ViewBalanceConsole implements ViewInterface {

    /**
     * Показать экранную форму.
     * Выводит в консоль баланс и вызывает контроллер для оповещения о завершении демонстрации баланса.
     */
    @Override
    public void show() {
        System.out.println("Balance: " + balance);
        try {
            controller.call();
        }
        catch (Exception e) {                   // В соответствии с контрактом Callable может бросить исключение.
            System.err.println(e.getMessage()); // Делать с ним нечего, кроме как показать исключение в консоль.
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
     * Установить значение баланса.
     * @param balance Баланс для вывода в экранной форме.
     */
    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    /**
     * Конструктор сохраняет переданный объект-контроллер банкомата, с которым будет работать далее.
     * Для этого класса контроллером может быть что угодно, реализующее интерфейс Callable, поэтому конструктор
     * проверяет, что переданный объект реализует этот интерфейс.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param controller Контроллер, с которым взаимодействует экранная форма.
     * @throws ClassCastException Если контроллер не реализует интерфейс Callable.
     */
    protected ViewBalanceConsole(Object controller) throws ClassCastException {

        if (!(controller instanceof Callable))
            throw new ClassCastException("ViewBalanceConsole: controller does not implements Callable interface");

        @SuppressWarnings("unchecked") final var controller_ = (Callable<Void>) controller;
        this.controller = controller_;
    }

    /**
     * Контроллер банкомата с которым работает экранная форма.
     */
    private final Callable<Void> controller;

    /**
     * Значение баланса, которое выводится на экран.
     */
    private BigInteger balance;
}
