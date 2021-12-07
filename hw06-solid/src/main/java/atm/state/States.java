package atm.state;

import atm.Controller;
import atm.Operations;

/**
 * Класс утилит для работы с состояниями.
 */
public class States {
    /**
     * Фабрика состояний.
     * @param operation Операция, для обработки которой требуется создать состояние.
     * @param controller Объект-контроллер, в контексте которого будет работать создаваемое состояние.
     * @param args Дополнительные аргументы конструктору состояния.
     * @return Новый объект "Состояние банкомата" в соответствии с параметрами.
     */
    public static State createState(Operations operation, Controller controller, Object... args) {
        return switch (operation) {
            case Login -> new StateLogin(controller);
            case Logout -> new StateLogout(controller);
            case Menu -> new StateMenu(controller);
            case Balance -> new StateBalance(controller);
            case Cash -> new StateCash(controller);
            case Deposit -> new StateDeposit(controller);
            case Error -> new StateError(controller, args);
        };
    }
}
