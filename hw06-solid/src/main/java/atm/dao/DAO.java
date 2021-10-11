package atm.dao;

import atm.config.ConfigInterface;

/**
 * Класс сервисных функций для работы со службой доступа к данным.
 */
public class DAO {
    /**
     * Фабрика создаёт объект доступа к данным на основе конфигурации.
     * @param cfg Конфигурационный объект.
     * @return Объект доступа к данным.
     */
    public static DAOInterface createDAO(@SuppressWarnings("unused") ConfigInterface cfg) {
        return new DAODefault();
    }
}
