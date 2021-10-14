package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.View;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Состояние контроллера банкомата: "Снятие наличных".
 * Реализует интерфейс Consumer, чтобы получать введённую пользователем сумму от экранной формы ViewCash.
 */
public class StateCash extends State implements Consumer<Optional<BigInteger>> {

    /**
     * Запуск алгоритма работы банкомата в данном состоянии.
     * Показывает ранее созданную экранную форму.
     */
    @Override
    public void doJob() {
        view.show();
    }

    /**
     * Метод интерфейса Consumer. Вызывается экранной формой после ввода пользователем требуемой суммы.
     * Если пользователь отменил операцию - значение будет пустым.
     * Через сервис доступа к аккаунту метод списывает у клиента сумму со счёта и даёт команду сейфовой машине выдать
     * сумму.
     * В случае ошибки переключает контроллер в состояние "Обработка ошибки".
     * @param cash Требуемая сумма или не установленный Optional если операция отменена.
     */
    @Override
    public void accept(Optional<BigInteger> cash) {
        if (cash.isEmpty()) {
            switchControllerToMenu();
            return;
        }

        try {
            final var cashValue = cash.get();
            atm.getAccountService().cash(atm.getUuid(), cashValue);
            atm.getCashBox().giveOut(cashValue);
            switchControllerToMenu();
        } catch (IllegalAccessException e) {
            view.hide();
            atm.setState(States.createState(Operations.Error, atm, e.getMessage()));
        }
    }

    /**
     * Конструктор создаёт экранную форму, с которой будет работать.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     */
    protected StateCash(Controller atm) {
        super(atm);
        view = atm.getViewFactory().createView(Operations.Cash, this);
    }

    /**
     * Переключить контроллер в состояние "Меню".
     */
    private void switchControllerToMenu() {
        view.hide();
        atm.setState(States.createState(Operations.Menu, atm));
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final View view;
}
