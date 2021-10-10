package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    @Override
    public void onUpdated(Message msg) {
        ObjectForMessage newField13 = new ObjectForMessage();
        newField13.setData(List.copyOf(msg.getField13().getData()));
        messageMap.put(msg.getId(), msg.toBuilder().field13(newField13).build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageMap.get(id));
   }

    private final Map<Long, Message> messageMap = new HashMap<>();
}
