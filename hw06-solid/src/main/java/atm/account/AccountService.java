package atm.account;

import java.math.BigInteger;

/**
 * Интерфейс сервисов доступа к учётной записи пользователя.
 */
public interface AccountService {
    /**
     * Запрос баланса пользователя.
     * @param uuid Идентификатор пользователя.
     * @return Баланс пользователя.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для осуществления операции.
     */
    BigInteger balance(String uuid) throws IllegalAccessException;

    /**
     * Снятие наличных пользователем.
     * @param uuid Идентификатор пользователя.
     * @param cash Снимаемая сумма.
     * @return Остаток на счёте после совершения операции снятия наличных.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для осуществления операции.
     * @throws IllegalStateException Состояние счёта не позволяет завершить операцию (недостаточно средств?)
     */
    BigInteger cash(String uuid, BigInteger cash) throws IllegalAccessException, IllegalStateException;

    /**
     * Внесение денег на счёт пользователем.
     * @param uuid Идентификатор пользователя.
     * @param depo Вносимая сумма.
     * @return Остаток на счёте после совершения операция внесения наличных.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для осуществления операции.
     */
    @SuppressWarnings("UnusedReturnValue")
    BigInteger deposit(String uuid, BigInteger depo) throws IllegalAccessException;

    /**
     * Проверка возможности входа в систему пользователя.
     * @param uuid UID пользователя, который пытается войти в систему.
     * @throws IllegalAccessException Пользователь uuid не имеет прав для входа в систему.
     */
    void login(String uuid) throws IllegalAccessException;
}