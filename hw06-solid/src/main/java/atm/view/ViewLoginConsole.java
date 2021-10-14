package atm.view;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Экранная форма "Вход в систему".
 */
public class ViewLoginConsole implements View {

    /**
     * Показать экранную форму.
     * Выводит в консоль диалог входа, ожидает ввода строки и передаёт её контролеру.
     */
    @Override
    public void show() {
        System.out.print("Insert card and enter PIN-code: ");
        controller.accept(new Scanner(System.in).next());
    }

    /**
     * Скрыть экранную форму
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
    protected ViewLoginConsole(Object consumer) {

        if (!(consumer instanceof Consumer))
            throw new ClassCastException("ViewLoginConsole: controller does not implements Consumer interface");

        @SuppressWarnings("unchecked") final var consumer_ = (Consumer<String>) consumer;
        this.controller = consumer_;
    }

    /**
     * Контролер банкомата с которым работает данная экранная форма.
     */
    private final Consumer<String> controller;
}
