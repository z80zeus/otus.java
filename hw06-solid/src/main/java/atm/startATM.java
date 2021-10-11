package atm;

import atm.account.AccountService;
import atm.cashBox.CashBox;
import atm.config.ConfigManager;
import atm.dao.DAO;
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
        final var cashBox = CashBox.createCashBox(config);
        final var dao = DAO.createDAO(config);
        final var accountService = AccountService.createAccountService(config, dao);
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

