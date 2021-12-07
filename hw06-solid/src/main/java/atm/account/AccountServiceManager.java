package atm.account;

import atm.config.Config;
import atm.dao.DAO;

/**
 * Служебный класс сервиса доступа к учётной записи пользователя.
 */
public class AccountServiceManager {
    /**
     * Фабрика сервисов доступа к учётной записи.
     * @param cfg Объект-конфигурация, в соответствии с которой создаётся требуемый объект.
     * @param dao Объект доступа к данным, передаваемый создаваемому серсиву.
     */
    public static AccountService createAccountService(
            @SuppressWarnings("unused") Config cfg,
            DAO dao) {
        return new AccountServiceDefault(dao);
    }
}