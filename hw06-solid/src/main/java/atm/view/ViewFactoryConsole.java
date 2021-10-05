package atm.view;

import atm.Operations;

public class ViewFactoryConsole implements ViewFactoryInterface {
    @Override
    public ViewInterface createView(Operations operation, Object controller, Object... args) {
        return switch (operation) {
            case Login -> new ViewLogin(controller);
            case Logout -> new ViewLogout(controller);
            case Menu -> new ViewMenu(controller);
            case Balance -> new ViewBalance(controller);
            case Cash -> new ViewCash(controller);
            case Deposit -> new ViewDeposit(controller);
            case Error -> new ViewError(controller, args);
        };
    }
}
