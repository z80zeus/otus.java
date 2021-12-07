package atm.view;

import atm.Operations;

/**
 * Фабрика консольных экранных форм.
 */
public class ViewFactoryConsole implements ViewFactoryInterface {
    /**
     * Создать консольную экранную форму.
     * @param operation Операция, для которой требуется экранная форма.
     * @param controller Контроллер, для которого создаётся экранная форма. Для разных экранных форм контроллер должен
     *                   реализовать разный интерфейс, поэтому в метод передаётся по универсальной ссылке Object.
     * @param args Дополнительные аргументы, которые будут переданы экранной форме, если она на них рассчитывает.
     * @return Новая экранная форма.
     */
    @Override
    public View createView(Operations operation, Object controller, Object... args) {
        return switch (operation) {
            case Login -> new ViewLoginConsole(controller);
            case Logout -> new ViewLogoutConsole(controller);
            case Menu -> new ViewMenuConsole(controller);
            case Balance -> new ViewBalanceConsole(controller);
            case Cash -> new ViewCashConsole(controller);
            case Deposit -> new ViewDepositConsole(controller);
            case Error -> new ViewErrorConsole(controller, args);
        };
    }
}
