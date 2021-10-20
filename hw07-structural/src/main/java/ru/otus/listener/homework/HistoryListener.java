package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс хранит сообщения во внутреннем хранилище.
 */
public class HistoryListener implements Listener, HistoryReader {

    /**
     * Сохранение сообщения.
     * Функция ДЕЛАЕТ КОПИЮ сообщения и сохраняет его во внутреннее хранилище Listener'а.
     * @param msg Сообщение, копия которого будет сохранена.
     */
    @Override
    public void onUpdated(Message msg) {
        ObjectForMessage newField13 = new ObjectForMessage();
        newField13.setData(List.copyOf(msg.getField13().getData()));
        messageMap.put(msg.getId(), msg.toBuilder().field13(newField13).build());
    }

    /**
     * Получить сообщение из хранилища по идентификатору.
     * @param id Идентификатор сообщения, которое требуется найти.
     * @return Опциональный объект, содержащий сообщение.
     * Если сообщение с указанным идентификатором не найдено - опциональный объект будет пустым.
     */
    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageMap.get(id));
   }

    private final Map<Long, Message> messageMap = new HashMap<>();
}
