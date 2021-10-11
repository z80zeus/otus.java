package atm;

import atm.account.AccountServiceInterface;
import atm.cashBox.CashBoxInterface;
import atm.config.ConfigInterface;
import atm.state.State;
import atm.state.States;
import atm.view.ViewFactoryInterface;

/**
 * Контроллер банкомата.
 * Является узловым элементом системы.
 * Инкапсулирует алгоритм работы банкомата.
 * Содержит ссылки на прочие элементы системы.
 */
public class Controller {

    /**
     * Функция возвращает объект класса-билдера контроллера.
     * @return Объект-строитель.
     */
    public static ControllerBuilder getBuilder() {
        return new ControllerBuilder();
    }

    /**
     * Билдер контроллера.
     * Как обычный строитель: конфигурируется вызовами соответствующих методов, а затем вызовом метода
     * конструирования (build) - создаёт объект контроллер.
     */
    public static class ControllerBuilder {

        /**
         * Установить параметр "Конфигурация контроллера".
         * @param cfg Конфигурационный объект.
         * @return Ссылка на текущий объект-билдер для построения цепочек вызовов.
         */
        public ControllerBuilder setConfig(ConfigInterface cfg) {
            config = cfg;
            return this;
        }

        /**
         * Установить параметр "Бокс для наличных".
         * @param cashBox Ссылка на объект, управляющий выдачей-приёмом наличных.
         * @return Ссылка на текущий объект-билдер для построения цепочек вызовов.
         */
        public ControllerBuilder setCashBox(CashBoxInterface cashBox) {
            this.cashBox = cashBox;
            return this;
        }

        /**
         * Установить параметр "Сервис работы с учётной записью клиента".
         * @param accountService Сервис, работающий с аккаунтом клиента.
         * @return Ссылка на текущий объект-билдер для построения цепочек вызовов.
         */
        public ControllerBuilder setAccountService(AccountServiceInterface accountService) {
            this.accountService = accountService;
            return this;
        }

        /**
         * Установить параметр "Фабрика экранных форм".
         * @param viewFactory Фабрика, которая создаёт экранные формы нужного типа.
         * @return Ссылка на текущий объект-билдер для построения цепочек вызовов.
         */
        public ControllerBuilder setViewFactory(ViewFactoryInterface viewFactory) {
            this.viewFactory = viewFactory;
            return this;
        }

        /**
         * Создать контроллер.
         * @return Новый контроллер с параметрами, соответствующими установленным в билдере.
         */
        public Controller build() {
            return new Controller(config, cashBox, accountService, viewFactory);
        }

        private ConfigInterface config = null;
        private CashBoxInterface cashBox = null;
        private AccountServiceInterface accountService = null;
        private ViewFactoryInterface viewFactory = null;
    }

    /**
     * Получить конфигурацию проекта.
     * @return Конфигурационный объект, переданный контроллеру при конструировании.
     */
    @SuppressWarnings("unused")
    public ConfigInterface getConfig() {
        return config;
    }

    /**
     * Установить текущее состояние контроллера.
     * @param newState Объект, инкапсулирующий состояние контроллера.
     * @return Ссылка на текущий объект-контроллер для построения цепочек вызовов.
     */
    public Controller setState (State newState) {
        state = newState;
        state.doJob();
        return this;
    }

    /**
     * Запуск контроллера - точка входа в проект.
     */
    public void run () {
        setState(States.createState(Operations.Login, this));
    }

    /**
     * Установить идентификатор пользователя, для которого в банкомате открыта сеесия.
     * @param uuid UID пользователя.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Получить идентификатор пользователя, для которого в банкомате открыта сессия.
     * @return UID пользователя, работающего с банкоматом.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Получить объект управляющий боксом для наличных.
     * @return Объект бокс для наличных.
     */
    public CashBoxInterface getCashBox() {
        return cashBox;
    }

    /**
     * Получить сервис, работающий с учётными записями клиентов.
     * @return Сервис работы с учётными записями.
     */
    public AccountServiceInterface getAccountService() { return accountService; }

    /**
     * Получить фабрику экранных форм.
     * @return Фабрика экранных форм.
     */
    public ViewFactoryInterface getViewFactory() { return viewFactory; }

    /**
     * Конфигурация приложения.
     */
    private final ConfigInterface config;

    /**
     * ID пользователя, работающего в системе.
     */
    private String uuid = null;

    /**
     * Объект управляющий сейфом банкомата.
     */
    private final CashBoxInterface cashBox;

    /**
     * Служба работы с учётной записью пользователя.
     */
    private final AccountServiceInterface accountService;

    /**
     * Текущее состояние контроллера банкомата.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private State state = null;

    /**
     * Фабрика экранных форм.
     */
    private final ViewFactoryInterface viewFactory;

    /**
     * Конструктор, с помощью которого билдер создаёт контроллер.
     * @param config Объект, содержащий конфигурацию проекта.
     * @param cashBox Объект, управляющий сейфом банкомата.
     * @param accountService Сервис, работающий с учётными записями пользователей.
     * @param viewFactory, Фабрика экранных форм.
     */
    private Controller(ConfigInterface config,
                       CashBoxInterface cashBox,
                       AccountServiceInterface accountService,
                       ViewFactoryInterface viewFactory) {
        this.config = config;
        this.cashBox = cashBox;
        this.accountService = accountService;
        this.viewFactory = viewFactory;
    }
}
