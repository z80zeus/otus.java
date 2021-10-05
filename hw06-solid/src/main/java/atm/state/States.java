package atm.state;

import atm.Controller;
import atm.Operations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Класс утилит для работы с состояниями.
 */
public class States {
    /**
     * Фабрика состояний.
     * @param operation Операция, для обработки которой требуется создать состояние.
     * @param atm Объект-контроллер, в контексте которого будет работать создаваемое состояние.
     * @param args Дополнительные аргументы конструктору состояния.
     * @return Новый объект "Состояние банкомата" в соответствии с параметрами.
     */
    public static State createState(Operations operation, Controller atm, Object... args) {
        try {
            return (State) operations2class.get(operation).newInstance(atm, args);
        }
        catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Словарь отображения операций в конструкторы классов соответствующих состояний.
     * В состоянии один конструктор, поэтому берётся нулевой элемент возвращаемого массива конструкторов.
     */
    private final static Map<Operations, Constructor<?>> operations2class = Map.ofEntries(
            Map.entry(Operations.Login, StateLogin.class.getConstructors()[0]),
            Map.entry(Operations.Logout, StateLogout.class.getConstructors()[0]),
            Map.entry(Operations.Menu, StateMenu.class.getConstructors()[0]),
            Map.entry(Operations.Balance, StateBalance.class.getConstructors()[0]),
            Map.entry(Operations.Cash, StateCash.class.getConstructors()[0]),
            Map.entry(Operations.Deposit, StateDeposit.class.getConstructors()[0]),
            Map.entry(Operations.Error, StateError.class.getConstructors()[0])
            );
}
