package atm.config;

import java.util.Map;
import static java.util.Map.entry;

/**
 * Конфигурационный объект по-умолчанию. Для учебного примера.
 */
public class ConfigImplDefault implements Config {
    /**
     * Получение значения параметра по ключу.
     * @param key Имя ключа.
     * @return Этот класс - учебный. В нём метод может вернуть конфигурацию ячеек для банкнот.
     *  Для остальных параметров всегда возвращает null.
     */
    @Override
    public Object getValue(String key) {
        //noinspection SwitchStatementWithTooFewBranches
        return switch (key) {
            case "banknoteBoxes" -> bankNoteBoxes;
            default -> null;
        };
    }

    /**
     * Конфигурация ячеек для банкнот:
     * словарь отображающий номиналы банкнот в адреса контроллеров соответствующих ячеек.
     */
    private final Map<Integer, Long> bankNoteBoxes = Map.ofEntries(
            entry (5000, 1L),
            entry (2000, 2L),
            entry (1000, 3L),
            entry (500, 4L),
            entry (200, 5L),
            entry (100, 6L));
}
