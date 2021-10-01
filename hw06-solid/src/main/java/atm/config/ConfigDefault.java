package atm.config;

/**
 * Конфигурационный объект по-умолчанию. Для учебного примера.
 */
public class ConfigDefault implements ConfigInterface {
    /**
     * Получение значения параметра по ключу.
     * @param key Имя ключа.
     * @return Этот класс - учебный. В нём метод всегда возвращает пустую строку.
     */
    @Override
    public String getValue(String key) {
        return "";
    }
}
