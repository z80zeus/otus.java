package atm.view;

import atm.config.ConfigInterface;

/**
 * Абстрактная фабрика фабрик экранных форм.
 */
public class ViewAbstractFactory {

    /**
     * Метод создания фабрики экранных форм определённого типа в соответствии с конфигурацией приложения.
     * В учебном примере независимо от конфигурации создаёт фабрику генерирующую только консольные экранные формы.
     * @param cfg Конфигурация приложения.
     * @return Фабрика, создающая экранные формы в соответствии с конфигурацией приложения.
     */
    public static ViewFactoryInterface createFactory(@SuppressWarnings("unused") ConfigInterface cfg) {
        return new ViewFactoryConsole();
    }
}
