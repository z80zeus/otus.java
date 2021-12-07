package atm.cashBox;

import atm.config.Config;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Сервис работы с ячейками.
 */
public class BanknoteBoxService {

    /**
     * Создать словарь ячеек.
     * @param cfg Конфигурационный объект на основе которого создаются программные объекты для работы с ячейками.
     * @return Словарь ячеек: номинал -> ячейка.
     * @throws IllegalArgumentException Ошибка в конфигурационной объекте не позволяет создать ячейки.
     */
    public static Map<Integer, BanknoteBox> createBanknoteBoxes(Config cfg) throws IllegalArgumentException {

        var banknoteBoxesObject = cfg.getValue("banknoteBoxes");

        if (!(banknoteBoxesObject instanceof Map))
            throw new IllegalArgumentException("Config error: bad type of banknoteBoxes. Map<Integer,Long> is need.");

        @SuppressWarnings("unchecked")
        Map<Integer, Long> banknoteBoxesMap = (Map<Integer, Long>) banknoteBoxesObject;

        final Map<Integer, BanknoteBox> banknoteBoxes = new TreeMap<>(Comparator.reverseOrder());

        for (var banknoteBox: banknoteBoxesMap.entrySet())
            banknoteBoxes.put(banknoteBox.getKey(), new BanknoteBoxImpl(banknoteBox.getValue()));

        return banknoteBoxes;
    }
}
