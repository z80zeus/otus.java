package ru.otus.protobuf;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.service.RemoteCounterServiceImpl;

import java.io.IOException;

/**
 * gRPC-сервер.
 * В качестве бизнес-логики сервера используется объект RemoteCounterServerImpl.
 */
public class GRPCServer {
    private static final Logger logger = LoggerFactory.getLogger(GRPCServer.class);

    /**
     * Номер порта на котором будет принимать соединения gRPC-сервер.
     */
    public static final int SERVER_PORT = 8190;

    /**
     * Точка входа в программу.
     * @param args Аргументы командной строки игнорируются.
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        var counterService = new RemoteCounterServiceImpl();

        Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(counterService).build();
        server.start();
        logger.info("Server waiting for client connections...");
        server.awaitTermination();
    }
}
