package atm;

/**
 * Служебный класс сервиса доступа к учётной записи пользователя.
 */
public class AccountService {
    /**
     * Фабрика сервисов доступа к учётной записи.
     * @param cfg Объект-конфигурация, в соответствии с которой создаётся требуемый объект.
     * @param dao Объект доступа к данным, передаваемый создаваемому серсиву.
     */
    public static createAccountService(ConfigInterface cfg, AccountDAOInterface dao) {
        return new AccountServiceDefault(dao);
    }
}