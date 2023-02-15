package org.example;

import org.example.calc.Calculator;
import org.example.calc.newCalc.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServer {

    private final int port;
    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("[CustomWebApplicationServer started {} port.]",port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client");

            // ServerSocket은 connection이 생길때까지 계속 대기 한다.
            // 연결이 되어도 프로그램 종료가 안되는데 응답을 해야 종료가 되는지??
            while ((clientSocket = serverSocket.accept()) != null){
                logger.info("[CustomWebApplicationServer] client connected!");
                new Thread(new ClientRequestHandler(clientSocket)).start();

            }

            logger.info("finish log");
        }
    }
}
