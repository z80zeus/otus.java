package atm.config;

/**
 * Интерфейс конфигурационных объектов.
 * Конфигурация - это объект, содержащий пары: параметр-значение.
 */
public interface Config {
    /**
     * Получение значения параметра по ключу.
     * @param key Имя ключа.
     * @return Значение параметра.
     */
    @SuppressWarnings("unused")
    Object getValue(String key);
}
