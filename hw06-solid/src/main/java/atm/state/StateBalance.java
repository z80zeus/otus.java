package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewBalance;

import java.util.concurrent.Callable;

/**
 * Состояние контроллера банкомата: "Проверка баланса".
 * Реализует интерфейс callable, чтобы получать оповещения от экранной формы ViewBalance о завершении просмотра
 * баланса и необходимости переключения банкомата в другое состояние.
 */
public class StateBalance extends State implements Callable<Void> {

    /**
     * Запуск алгоритма работы банкомата в данном состоянии.
     * Запрашивает баланс пользователя, передаёт его ранее сконструированной экранной форме и показывает её.
     * В случае ошибки переключает контроллер в состояние "Обработка ошибки".
     */
    @Override
    public void doJob() {
        try {
            view.setBalance(atm.getAccountService().balance(atm.getUuid()));
        } catch (IllegalAccessException e) {
            view.hide();
            atm.setState(States.createState(Operations.Error, atm, e.getMessage()));
            return;
        }

        view.show();
    }

    /**
     * Метод из интерфейса Callable. Вызывается экранной формой по завершении демонстрации баланса пользователю.
     * Переключает контроллер в состояние "Меню".
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
     */
    protected StateBalance(Controller atm) {
        super(atm);
        view = (ViewBalance) atm.getViewFactory().createView(Operations.Balance, this);
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final ViewBalance view;
}
