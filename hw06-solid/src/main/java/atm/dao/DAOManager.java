package atm.dao;

import atm.config.Config;

/**
 * Класс сервисных функций для работы со службой доступа к данным.
 */
public class DAOManager {
    /**
     * Фабрика создаёт объект доступа к данным на основе конфигурации.
     * @param cfg Конфигурационный объект.
     * @return Объект доступа к данным.
     */
    public static DAO createDAO(@SuppressWarnings("unused") Config cfg) {
        return new DAOImplDefault();
    }
}
