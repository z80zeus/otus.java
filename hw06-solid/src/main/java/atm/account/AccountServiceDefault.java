package atm.account;

import atm.dao.DAO;

import java.math.BigInteger;
import java.util.Random;

/**
 * Служба работы с аккаунтом пользователя.
 */
@SuppressWarnings("ClassCanBeRecord")
public class AccountServiceDefault implements AccountService {
    /**
     * Запрос баланса пользователя.
     * @param uuid Идентификатор пользователя.
     * @return Баланс пользователя.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для осуществления операции.
     */
    @SuppressWarnings("RedundantThrows")
    @Override
    public BigInteger balance(@SuppressWarnings("unused") String uuid) throws IllegalAccessException {
        // В реальном проекте здесь должен быть код работы с DAO.
        // Заглушка игнорирует параметры и выдаёт случайное число.
        return randomInsideMillion();
    }

    /**
     * Снятие наличных пользователем.
     * @param uuid Идентификатор пользователя.
     * @param cash Снимаемая сумма.
     * @return Остаток на счёте после совершения операции снятия наличных.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для осуществления операции.
     * @throws IllegalStateException Состояние счёта не позволяет завершить операцию (недостаточно средств?)
     */
    @Override
    @SuppressWarnings("UnusedReturnValue")
    public BigInteger cash(@SuppressWarnings("unused") String uuid,
                           @SuppressWarnings("unused") BigInteger cash) throws IllegalAccessException, IllegalStateException {
        // В реальном проекте здесь должен быть код работы с DAO.
        // Заглушка игнорирует параметры и выдаёт случайное число.
        return randomInsideMillion();
    }

    /**
     * Внесение денег на счёт пользователем.
     * @param uuid Идентификатор пользователя.
     * @param depo Вносимая сумма.
     * @return Остаток на счёте после совершения операция внесения наличных.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для осуществления операции.
     */
    @Override
    @SuppressWarnings({"UnusedReturnValue", "RedundantThrows"})
    public BigInteger deposit(@SuppressWarnings("unused") String uuid,
                              @SuppressWarnings("unused") BigInteger depo) throws IllegalAccessException {
        // В реальном проекте здесь должен быть код работы с DAO.
        // Заглушка игнорирует параметры и возвращает случайное число.
        return randomInsideMillion();
    }

    /**
     * Проверка возможности входа в систему пользователя.
     * @param uuid UID пользователя, который пытается войти в систему.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для входа в систему.
     */
    @SuppressWarnings("RedundantThrows")
    @Override
    public void login(@SuppressWarnings("unused") String uuid) throws IllegalAccessException {
        // В реальном проекте здесь должен быть код работы с DAO.
        // Заглушка игнорирует параметры и пускает всех.
    }

    /**
     * При конструировании сервису передаётся объект доступа к данным.
     * Сервис создаётся фабрикой, поэтому конструктор защищён.
     * @param dao Объект, реализующий интерфейс доступа к данным.
     */
    protected AccountServiceDefault(DAO dao) {
        this.dao = dao;
    }

    /**
     * Генерация случайного числа в пределах миллиона. Это чисто mock-функция. В реальном проекте такой не будет.
     * @return Случайное число в пределах миллиона.
     */
    private BigInteger randomInsideMillion() {
        var rnd = new Random();
        return BigInteger.valueOf(rnd.nextInt(1000000));
    }

    @SuppressWarnings({"LocalVariable", "FieldCanBeLocal", "unused"})
    private final DAO dao;
}
