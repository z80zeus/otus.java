package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    /**
     * Группирует выходящий список по name, при этом суммирует поля value
     * @param data Список объектов Measurement, ключи/значения которых следует сгруппировать по ключам в словарь.
     * @return Сгруппированные значения.
     */
    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return data.stream().reduce(
                new TreeMap<>(),
                (accum, measurement) -> {
                    accum.put(measurement.getName(),
                            accum.getOrDefault(measurement.getName(), 0.0) + measurement.getValue());
                    return accum;
                },
                (accum1, accum2) -> accum1); // В однопоточном варианте combine не вызывается, поэтому всё-равно что возвращать.

//  А это - редьюс с человеческим лицом:
//        var rtn = new TreeMap<String,Double>();
//        data.forEach(measurement ->
//                rtn.put(measurement.getName(),
//                        rtn.getOrDefault(measurement.getName(),0.0) + measurement.getValue()));
//        return rtn;
    }
}
