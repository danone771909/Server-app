package logic;

import lombok.extern.slf4j.Slf4j;
import model.GameClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class GameServer {
    private final int PORT = 9002;
    private ArrayBlockingQueue<GameClient> waitingPlayers = new ArrayBlockingQueue<>(2048);

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            log.debug("Server is started on PORT: " + PORT);
            try{
                while(true) {
                    GameClientHandler gameClientHandler = new GameClientHandler(serverSocket.accept(), waitingPlayers);
                    new Thread(gameClientHandler).start();
                }
            }
            finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            log.debug(e.toString());
        }
    }
}
