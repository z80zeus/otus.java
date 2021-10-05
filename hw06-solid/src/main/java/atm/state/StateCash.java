package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewInterface;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Состояние контроллера банкомата: "Снятие наличных".
 * Реализует интерфейс Consumer, чтобы получать введённую сумму от экранной формы ViewCash.
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
     * Метод из интерфейса Consumer. Вызывается экранной формой после ввода пользователем требуемой суммы.
     * Если пользователь отменил операцию - значение будет пустым.
     * @param cash Требуемая сумма или не установленный Optional если операция отменена.
     */
    @Override
    public void accept(Optional<BigInteger> cash) {
        if (cash.isPresent())
            try {
                final var cashValue = cash.get();
                atm.getAccountService().cash(atm.getUuid(), cashValue);
                atm.getCashBox().giveOut(cashValue);
            }
            catch (IllegalAccessException e) {
                view.hide();
                atm.setState(States.createState(Operations.Error, atm, e.getMessage()));
                return;
            }

        view.hide();
        atm.setState(States.createState(Operations.Menu, atm));
    }

    /**
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * Конструктор создаёт экранную форму, с которой будет работать.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     */
    protected StateCash(Controller atm) {
        super(atm);
        view = atm.getViewFactory().createView(Operations.Cash, this);
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final ViewInterface view;
}
