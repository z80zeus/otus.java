package atm.view;

import atm.Operations;

/**
 * Интерфейс фабрик экранных форм.
 */
public interface ViewFactoryInterface {
    /**
     * Создать экранную форму.
     * @param operation Операция, для которой требуется экранная форма.
     * @param controller Контроллер, для которого создаётся экранная форма. Для разных экранных форм контроллер должен
     *                   реализовать разный интерфейс, поэтому в метод передаётся по универсальной ссылке Object.
     * @param args Дополнительные аргументы, которые будут переданы экранной форме, если она на них рассчитывает.
     * @return Новая экранная форма.
     */
    View createView(Operations operation, Object controller, Object... args);
}
