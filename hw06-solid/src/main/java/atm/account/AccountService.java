package atm.account;

import atm.config.ConfigInterface;
import atm.dao.DAOInterface;

/**
 * Служебный класс сервиса доступа к учётной записи пользователя.
 */
public class AccountService {
    /**
     * Фабрика сервисов доступа к учётной записи.
     * @param cfg Объект-конфигурация, в соответствии с которой создаётся требуемый объект.
     * @param dao Объект доступа к данным, передаваемый создаваемому серсиву.
     */
    public static AccountServiceInterface createAccountService(
            @SuppressWarnings("unused") ConfigInterface cfg,
            DAOInterface dao) {
        return new AccountServiceDefault(dao);
    }
}