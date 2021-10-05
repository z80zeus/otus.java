package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewInterface;

import java.util.concurrent.Callable;

/**
 * Состояние контроллера "Обработка ошибки".
 * Никакой особой обработки не предполагается: просто нужно показать сообщение об ошибке пользователю.
 * Реализует интерфейс Callable, чтобы получить оповещение о завершении просмотра сообщения.
 */
public class StateError extends State implements Callable<Void> {

    /**
     * Запуск алгоритма работы банкомата в данном состоянии.
     * Показывает экранную форму с сообщением об ошибке.
     */
    @Override
    public void doJob() {
        view.show();
    }

    /**
     * Метод интерфейса Callable. Вызывается экранной формой по завершении демонстрации ошибки пользователю.
     * Переключает контроллер банкомата в состояние "Меню".
     */
    @Override
    public Void call() {
        view.hide();
        atm.setState(States.createState(Operations.Menu, atm));
        return null;
    }

    /**
     * Конструктор создаёт экранную форму, с которой будет работать.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     * @param args Строковое сообщение об ошибке (args[0] = String).
     */
    protected StateError(Controller atm, Object... args) {
        super(atm);
        final String msg = (String) args[0];
        view = atm.getViewFactory().createView(Operations.Error, this, msg);
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final ViewInterface view;
}
