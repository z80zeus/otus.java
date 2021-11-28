package ru.otus.protobuf;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.RemoteCounterServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;
import ru.otus.protobuf.service.StreamObserverImpl;

/**
 * gRPC-клиент.
 * Подключается к gRPC-серверу, а затем получая значения от сервера и генерируя собственные,
 * обрабатывает два этих потока в соответствии с заданием.
 */
public class GRPCClient {

    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);

    /**
     * Имя хоста на котором работает сервер.
     */
    private static final String SERVER_HOST = "localhost";

    /**
     * Порт, который слушает сервер.
     */
    private static final int SERVER_PORT = 8190;

    /**
     * Продолжительность задержки (миллисекунд) между генерацией чисел.
     */
    private static final long SleepDurationMs = 1000;

    /**
     * Точка входа в программу.
     * @param args Аргументы командной строки игнорируются.
     */
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        // Последнее значение, поступившее от сервера будет помещаться в нулевой элемент массива,
        // ссылка на который передастся обработчику потока серверных сообщений StreamObserverImpl.
        // Этот же массив используется в качестве монитора критической секции в которой разделяется
        // доступ к последнему значению от сервера со стороны рабочего потока клиента (функции main)
        // и потока gRPC, обрабатывающего сообщения сервера.
        long[] valueFromServer = {0};

        // Запрос к серверу включает в себя начальное и конечное значения последовательности,
        // которую должен вернуть сервер.
        var request = RequestMessage.newBuilder().setFirstValue(0).setLastValue(30).build();

        RemoteCounterServiceGrpc.RemoteCounterServiceStub newStub = RemoteCounterServiceGrpc.newStub(channel);
        var streamObserver = new StreamObserverImpl(valueFromServer);
        newStub.getValuesStream(request, streamObserver);

        for (long i = 0, currentValue = 0; i < 50; i++) {
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (valueFromServer) {
                currentValue += valueFromServer[0] + 1;
                valueFromServer[0] = 0;
            }
            logger.info("{}. currentValue:{}", i, currentValue);
            Thread.sleep(SleepDurationMs);
        }
        channel.shutdown();
    }
}

