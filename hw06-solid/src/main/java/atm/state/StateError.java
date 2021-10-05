package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewInterface;

import java.util.concurrent.Callable;

public class StateError extends State implements Callable<Void> {

    /**
     * Запуск алгоритма работы банкомата в данном состоянии.
     * Показывает экранную форму с ошибкой.
     */
    @Override
    public void doJob() {
        view.show();
    }

    /**
     * Метод из интерфейса Callable. Вызывается экранной формой по завершении демонстрации ошибки пользователю.
     * @return Метод ничего не возвращает.
     */
    @Override
    public Void call() {
        view.hide();
        atm.setState(States.createState(Operations.Menu, atm));
        return null;
    }

    /**
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * Конструктор создаёт экранную форму, с которой будет работать.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
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
