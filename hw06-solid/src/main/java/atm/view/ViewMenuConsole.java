package atm.view;

import atm.Operations;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Экранная форма "Меню".
 */
public class ViewMenuConsole implements View {

    /**
     * Показать экранную форму.
     * Выводит в консоль меню, ожидает ввода пункта меню и передаёт контролеру выбранную операцию.
     */
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
                    controller.accept(Operations.Logout);
                    return;
                }
                case 2 -> {
                    controller.accept(Operations.Balance);
                    return;
                }
                case 3 -> {
                    controller.accept(Operations.Cash);
                    return;
                }
                case 4 -> {
                    controller.accept(Operations.Deposit);
                    return;
                }
                default -> System.out.println("Bad menu number. Enter number from 1 to 4.");
            }
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
     * Для этого класса контроллером может быть что угодно, реализующее интерфейс Consumer, поэтому конструктор
     * проверяет, что переданный объект реализует этот интерфейс.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param consumer Контроллер, с которым взаимодействует экранная форма.
     * @throws ClassCastException Если контроллер не реализует интерфейс Consumer.
     */
    protected ViewMenuConsole(Object consumer) {

        if (!(consumer instanceof Consumer))
            throw new ClassCastException("ViewMenuConsole: controller does not implements Consumer interface");

        @SuppressWarnings("unchecked") final var controller_ = (Consumer<Operations>) consumer;
        this.controller = controller_;
    }

    private final Consumer<Operations> controller;
}
