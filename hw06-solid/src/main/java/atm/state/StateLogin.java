package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewInterface;

import java.util.function.Consumer;

/**
 * Состояние контроллера "Вход в систему".
 * Реализует интерфейс Consumer, для того чтобы получить строку UUID пользователя.
 */
public class StateLogin extends State implements Consumer<String> {

    /**
     * Запуск алгоритма работы банкомата в данном состоянии.
     * Показывает экранную форму с вводом данных пользователя.
     */
    @Override
    public void doJob() {
        view.show();
    }

    /**
     * Метод интерфейса Consumer. Вызывается экранной формой по завершении ввода данных пользователем.
     * Допускает пользователя в систему и переключает контроллер в состояние "Меню".
     * В случае ошибки переключает контроллер в состояние "Обработка ошибки".
     * @param uuid Строка с идентификатором пользователя.
     */
    @Override
    public void accept(String uuid) {
        view.hide();
        try {
            atm.getAccountService().login(uuid);
            atm.setUuid(uuid);
            atm.setState(States.createState(Operations.Menu, atm));
        }
        catch (IllegalAccessException e) {
            atm.setState(States.createState(Operations.Error, atm, e.getMessage()));
        }
    }

    /**
     * Конструктор создаёт экранную форму с которой будет работать.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     */
    protected StateLogin(Controller atm) {
        super(atm);
        view = this.atm.getViewFactory().createView(Operations.Login, this);
    }

    /**
     * Экранная форма соответствующая состоянию.
     */
    private final ViewInterface view;
}
