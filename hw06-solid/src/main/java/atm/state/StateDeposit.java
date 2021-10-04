package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewDeposit;
import atm.view.ViewInterface;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Состояние контроллера банкомата: "Снятие наличных".
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
     * Метод из интерфейса Callable. Вызывается экранной формой по команде пользователя о завершении внесения средств.
     * @return Метод ничего не возвращает.
     * @throws Exception Декларация исключения - для совместимости с интерфейсом Callable. Метод ничего не выбрасывает.
     */
    @Override
    public Void call() throws Exception {
        var bankNotes = atm.getCashBox().takeIn();
        if (!bankNotes.isEmpty())
            atm.getAccountService().deposit(atm.getUuid(), BigInteger.valueOf(sortOutCash(bankNotes)));

        view.hide();
        atm.setState(States.createState(Operations.Menu, atm));
        return null;
    }

    /**
     * Объект создаётся фабрикой, поэтому конструктор защищён.
     * Конструктор создаёт экранную форму, с которой будет работать.
     * @param atm Контроллер банкомата в контексте которого будет работать конструируемый объект.
     */
    protected StateDeposit(Controller atm) {
        super(atm);
        view = new ViewDeposit(this);
    }

    /**
     * Разбор поступившей наличности.
     * @param bankNotes Словарь наличности (номинал-количество), помещённой в банкомат пользователем.
     * @return Общая сумма внесённых наличных.
     */
    private Integer sortOutCash(Map<Integer, Integer> bankNotes) {
        var sum = 0;
        for(var bankNote: bankNotes.entrySet()) {
            // Этот вывод в консоль не является частью пользовательского интерфейса банкомата.
            // Этот вывод просто иллюстрирует работу купюроприёмника, поэтому находится здесь, а не в ViewDeposit
            System.out.println("Cash: " + bankNote.getKey());
            sum += bankNote.getKey() * bankNote.getValue();
        }
        System.out.println("Total: " + sum);
        return sum;
    }

    /**
     * Экранная форма, соответствующая состоянию.
     */
    private final ViewInterface view;
}
