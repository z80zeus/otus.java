package atm.account;

import java.math.BigInteger;
import java.util.Random;

/**
 * Служба работы с аккаунтом пользователя.
 */
public class AccountService {
    /**
     * При конструировании сервису инъектится объект доступа к данным.
     * @param dao Объект, реализующий интерфейс доступа к данным.
     */
    public AccountService(AccountDAOInterface dao) {
        this.dao = dao;
    }

    /**
     * Запрос баланса пользователя.
     * @param uuid Идентификатор пользователя.
     * @return Баланс пользователя.
     */
    public BigInteger balance(String uuid) throws IllegalAccessException {
        // В реальном проекте здесь должен быть код работы с DAO. Заглушка игнорирует параметры и выдаёт случайное число.
        return randomInsideMillion();
    }

    /**
     * Снятие наличных пользователем.
     * @param uuid Идентификатор пользователя.
     * @param cash Снимаемая сумма.
     * @return Остаток на счёте после совершения операции снятия наличных.
     * @throws IllegalAccessException Невозможно завершить операцию для пользователя uuid.
     * @throws IllegalStateException Состояние счёта не позволяет завершить операцию (недостаточно средств?)
     */
    public BigInteger cash(String uuid, BigInteger cash) throws IllegalAccessException, IllegalStateException {
        // В реальном проекте здесь должен быть код работы с DAO. Заглушка игнорирует параметры и выдаёт случайное число.
        return randomInsideMillion();
    }

    /**
     * Внесение денег на счёт пользователем.
     * @param uuid Идентификатор пользователя.
     * @param depo Вносимая сумма.
     * @return Остаток на счёте после совершения операция внесения наличных.
     * @throws IllegalAccessException Невозможно завершить операцию для пользователя uuid.
     */
    public BigInteger deposit(String uuid, BigInteger depo) throws IllegalAccessException {
        // В реальном проекте здесь должен быть код работы с DAO. Заглушка игнорирует параметры и выдаёт случайное число.
        return randomInsideMillion();
    }

    /**
     * Проверка возможности входа в систему пользователя.
     * @param uuid UID пользователя, который пытается войти в систему.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для входа в систему.
     */
    public void login(String uuid) throws IllegalAccessException {
        // В реальном проекте здесь должен быть код работы с DAO. Заглушка игнорирует параметры и пускает всех.
    }

    /**
     * Генерация случайного числа в пределах миллиона. Это чисто mock-функция. В реальном проекте такой не будет.
     * @return Случайное число в пределах миллиона.
     */
    private BigInteger randomInsideMillion() {
        var rnd = new Random();
        return BigInteger.valueOf(rnd.nextInt(1000000));
    }

    @SuppressWarnings("Unused")
    private final AccountDAOInterface dao;
}
