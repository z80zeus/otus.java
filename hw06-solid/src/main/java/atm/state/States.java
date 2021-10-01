package atm.state;

import atm.Controller;
import atm.Operations;

/**
 * Класс утилит для работы с состояниями.
 */
public class States {
    /**
     * Фабрика состояний.
     * @param atm Объект-контроллер, в контексте которого будет работать создаваемое состояние.
     * @param operation Операция, для обработки которой требуется создать состояние.
     * @return Новый объект "Состояние банкомата" в соответствии с параметрами.
     */
    public static State createState(Controller atm, Operations operation) {
        return switch (operation) {
            case Login -> new StateLogin(atm);
            case Logout -> new StateLogout(atm);
            case Menu -> new StateMenu(atm);
            case Balance -> new StateBalance(atm);
            case Cash -> new StateCash(atm);
            case Deposit -> new StateDeposit(atm);
        };
    }

}
