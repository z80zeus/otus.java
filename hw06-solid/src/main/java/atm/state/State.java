package atm.state;

import atm.Controller;

/**
 * Абстрактный класс для состояний банкомата.
 * Наследники должны переопределить функцию doJob.
 */
public abstract class State {

    /**
     * Запуск алгоритма конкретного состояния.
     * Этот метод должен быть переопределён в классах-потомках.
     */
    public abstract void doJob();

    /**
     * Ссылка на контроллер в контексте которого работает текущий объект State.
     */
    protected final Controller atm;

    /**
     * Конструктор базового класса. Вызывается конструкторами дочерних классов.
     * Обеспечивает для дочерних классов функционал сохранения ссылки на контроллер в контексте которого работает состояние.
     * @param atm Ссылка на контроллер, в контексте которого будет работать данный объект State.
     */
    protected State(Controller atm) {
        this.atm = atm;
    }
}
