package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.GRPCServer;
import ru.otus.protobuf.generated.RemoteCounterServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;
import ru.otus.protobuf.generated.ResponseMessage;

/**
 * Бизнес-логика gRPC-сервера.
 * Генерирует последовательность чисел по запросу вида [first,last).
 */
public class RemoteCounterServiceImpl extends RemoteCounterServiceGrpc.RemoteCounterServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(GRPCServer.class);

    /**
     * Продолжительность задержки (миллисекунд) между генерацией чисел.
     */
    private static final long SleepDurationMs = 2000;

    /**
     * Метод генерации чисел по запросу. Переопределяет скелетную реализацию.
     * @param request Входные параметры запроса.
     * @param responseObserver Поток для выходных сообщений сервера.
     */
    @Override
    public void getValuesStream(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {
        try {
            for (var i = request.getFirstValue(); i < request.getLastValue(); i++) {
                //noinspection BusyWait
                Thread.sleep(SleepDurationMs);
                responseObserver.onNext(ResponseMessage.newBuilder().setValue(i).build());
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        responseObserver.onCompleted();
    }
}
