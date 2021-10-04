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
     */
    @Override
    public void doJob() {
        view.setBalance(atm.getAccountService().balance(atm.getUuid()));
        view.show();
    }

    /**
     * Метод из интерфейса Callable. Вызывается экранной формой по завершении демонстрации баланса пользователю.
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
    protected StateBalance(Controller atm) {
        super(atm);
        view = (ViewBalance) atm.getViewFactory().createView(Operations.Balance, this);
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final ViewBalance view;
}
