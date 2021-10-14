package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.View;

import java.util.function.Consumer;

/**
 * Состояние контроллера "Меню".
 * Реализует интерфейс Consumer, чтобы получить выбор пользователя.
 */
public class StateMenu extends State implements Consumer<Operations> {

    /**
     * Запуск алгоритма работы контроллера в данном состоянии.
     * Показывает соответствующую экранную форму.
     */
    @Override
    public void doJob() {
        view.show();
    }

    /**
     * Метод интерфейса Consumer. Вызывается экранной формой при выборе пользователем пункта меню.
     * Переключает контроллер в выбранное состояние.
     * @param operation Операция, выбранная пользователем.
     */
    @Override
    public void accept(Operations operation) {
        view.hide();
        atm.setState(States.createState(operation, atm));
    }

    /**
     * Конструктор создаёт экранную форму с которой будет работать.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     */
    protected StateMenu(Controller atm) {
        super(atm);
        view = this.atm.getViewFactory().createView(Operations.Menu, this);
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final View view;
}
