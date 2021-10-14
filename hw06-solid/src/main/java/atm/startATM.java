package atm;

import atm.account.AccountServiceManager;
import atm.cashBox.CashBoxService;
import atm.config.ConfigManager;
import atm.dao.DAOManager;
import atm.view.ViewAbstractFactory;

/**
 * Главный класс приложения.
 */
public class startATM {
    /**
     * Точка входа в приложение.
     * Функция из параметров командной строки создаёт конфигурационный объект, с применением конфигурационного объекта
     * создаёт служебные компоненты, параметризирует ими билдер контроллера банкомата, создаёт и запускает контроллер.
     */
    public static void main (String[] args) {
        final var config = ConfigManager.getConfig(args);
        final var cashBox = CashBoxService.createCashBox(config);
        final var dao = DAOManager.createDAO(config);
        final var accountService = AccountServiceManager.createAccountService(config, dao);
        final var viewFactory = ViewAbstractFactory.createFactory(config);
        final var controller = Controller.getBuilder()
                .setConfig(config)
                .setCashBox(cashBox)
                .setAccountService(accountService)
                .setViewFactory(viewFactory)
                .build();
        controller.run();
    }
}

