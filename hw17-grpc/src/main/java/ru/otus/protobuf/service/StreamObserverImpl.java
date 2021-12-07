package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.GRPCClient;
import ru.otus.protobuf.generated.ResponseMessage;

/**
 * Класс-обработчик потока сообщений от сервера, для чего реализует интерфейс StreamObserver.
 */
public record StreamObserverImpl(long[] valueFromServer) implements StreamObserver<ResponseMessage> {

    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);

    /**
     * Метод получения очередного сообщения.
     * @param msg Ответное сообщение сервера.
     */
    @Override
    public void onNext(ResponseMessage msg) {
        synchronized (valueFromServer) {
            valueFromServer[0] = msg.getValue();
            logger.info("newValue:{}", valueFromServer[0]);
        }
    }

    /**
     * Ошибка gRPC.
     * @param t Ошибка.
     */
    @Override
    public void onError(Throwable t) {
        logger.error(t.getMessage());
    }

    /**
     * Сервер завершил передачу.
     * Можно было бы сигнализировать об этом наружу, основному коду клиента, но там это никому не интересно, поэтому
     * просто выводится сообщение в лог.
     */
    @Override
    public void onCompleted() {
        logger.info("Server job is over.");
    }
}
