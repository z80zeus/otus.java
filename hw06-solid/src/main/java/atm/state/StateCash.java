package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewCash;
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
     * Если пользователь отменил операцию - значение будет не установленным.
     * @param cash Требуемая сумма или не установленный Optional если операция отменена.
     */
    @Override
    public void accept(Optional<BigInteger> cash) {
        cash.ifPresent(cashValue -> atm.getCashBox().giveOut(cashValue));
        view.hide();
        atm.setState(States.createState(atm, Operations.Menu));
    }

    /**
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * Конструктор создаёт экранную форму, с которой будет работать.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     */
    protected StateCash(Controller atm) {
        super(atm);
        view = new ViewCash(this);
    }

    private final ViewInterface view;
}
