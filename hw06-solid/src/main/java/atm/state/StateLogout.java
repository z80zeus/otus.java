package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.View;

import java.util.concurrent.Callable;

/**
 * Состояние контроллера "Завершение работы".
 * Реализует интерфейс Callable, чтобы получить от экранной формы команду на выход.
 */
public class StateLogout extends State implements Callable<Void> {

    /**
     * Запуск алгоритма работы контроллера в данном состоянии.
     * Показывает соответствующую экранную форму.
     */
    @Override
    public void doJob() {
        view.show();
    }

    /**
     * Метод интерфейса Callable. Вызывается экранной формой по завершении демонстрации пользователю сообщения о
     * завершении работы.
     * Переключает контроллер в состояние "Вход в систему".
     */
    @Override
    public Void call() {
        view.hide();
        atm.setUuid(null);
        atm.setState(States.createState(Operations.Login, atm));
        return null;
    }

    /**
     * Конструктор создаёт экранную форму с которой будет работать.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     */
    protected StateLogout(Controller atm) {
        super(atm);
        view = atm.getViewFactory().createView(Operations.Logout, this);
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final View view;
}
