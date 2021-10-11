package atm.account;

import atm.config.ConfigInterface;

/**
 * Класс сервисных функций для работы со службой доступа к данным.
 */
public class AccountDAO {
    /**
     * Фабрика создаёт объект доступа к данным на основе конфигурации.
     * @param cfg Конфигурационный объект.
     * @return Объект доступа к данным.
     */
    public static AccountDAOInterface createDAO(@SuppressWarnings("unused") ConfigInterface cfg) {
        return new AccountDAODefault();
    }
}
