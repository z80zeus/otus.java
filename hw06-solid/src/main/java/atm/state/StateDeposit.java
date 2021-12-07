package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.View;

import java.util.concurrent.Callable;

/**
 * Состояние контроллера банкомата: "Внесение наличных".
 * Реализует интерфейс Callable, чтобы получать оповещение пользователя о завершении внесении денег.
 */
public class StateDeposit extends State implements Callable<Void> {

    /**
     * Запуск алгоритма работы банкомата в данном состоянии.
     * Показывает ранее созданную экранную форму и открывает бокс для наличных.
     */
    @Override
    public void doJob() {
        view.show();
        atm.getCashBox().open();
    }

    /**
     * Метод интерфейса Callable. Вызывается экранной формой по команде пользователя о завершении внесения средств.
     * Если пользователь поместил в сейф-машину банкноты - выясняет сумму и вносит её на счёт.
     * В случае ошибки переключает контроллер в состояние "Обработка ошибки" и возвращает внесённые средства.
     */
    @Override
    public Void call() {
        final var depoValue = atm.getCashBox().takeIn();
        atm.getCashBox().close();
        if (depoValue.isPresent()) {
            try {
                atm.getAccountService().deposit(atm.getUuid(), depoValue.get());
                view.hide();
                atm.setState(States.createState(Operations.Menu, atm));
            }
            catch (IllegalAccessException e) {
                view.hide();
                atm.setState(States.createState(Operations.Error, atm, e.getMessage()));
                atm.getCashBox().reject();
            }
        }
        return null;
    }

    /**
     * Конструктор создаёт экранную форму, с которой будет работать.
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     */
    protected StateDeposit(Controller atm) {
        super(atm);
        view = atm.getViewFactory().createView(Operations.Deposit, this);
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final View view;
}
