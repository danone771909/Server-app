package logic;

import logic.messages.Message;
import logic.messages.Steps;
import logic.steps.Game;
import logic.steps.GameStep;
import logic.steps.Register;
import model.GameClient;
import model.GameRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

import static logic.messages.Steps.*;

public class GameClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private GameClient gameClient;
    private GameStep gameStep;
    private Game game;
    private int CHECKPOINT_COUNTER = 5;
    private ArrayDeque<Steps> steps = new ArrayDeque<>();
    private ArrayBlockingQueue<GameClient> waitingPlayers;
    private Semaphore semaphore = new Semaphore(0);

    public GameClientHandler(Socket socket, ArrayBlockingQueue<GameClient> waitingPlayers) {
        this.waitingPlayers = waitingPlayers;
        this.socket = socket;
        gameClient = new GameClient();
        gameClient.setGameClientHandler(this);
        gameStep = new Register(gameClient);
        game = new Game(gameClient);
        initializeSteps();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String val = "";

            while(true) {
                Steps s = steps.remove();

                String response = gameStep.run(s, val);
                sendMessage(response);

                if(AFTER_FINISH.equals(s))
                    break;

                val = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeSteps() {
        steps.add(START);
        steps.add(AFTER_CLIENT_ID);
        steps.add(AFTER_CLIENT_NAME);
        steps.add(AFTER_CLIENT_LEVEL);
        steps.add(AFTER_CLIENT_ROUTE);
        steps.add(START_GAME);
        steps.add(DURING_GAME);
        steps.add(AFTER_FINISH);
    }

    private void sendMessage(String response) throws InterruptedException {
        if(Message.WAIT_FOR_OPPONENT.toString().equals(response)) {
            out.println(response);
            waitingForOpponent();
            gameStep = game;
            return;
        }
        else if(Message.CHECKPOINT.toString().equals(response) && --CHECKPOINT_COUNTER != 0) {
            steps.addFirst(DURING_GAME);
        }

        else if(Message.REQUEST_CLIENT_ID.toString().equals(response))
            checkClientId(response);

        out.println(response);
    }

    private boolean checkClientId(String id) {
        return true;
    }

    private synchronized void waitingForOpponent() throws InterruptedException {
        if(waitingPlayers.size() < 1) {
            System.out.println("List is empty");
            waitingPlayers.put(gameClient);
            semaphore.acquire();
        }
        else {
            System.out.println("There is one player into list");
            GameClient opponent = waitingPlayers.take();
            gameClient.setOpponent(opponent);
            opponent.setOpponent(gameClient);
            createGameRoom(gameClient, opponent);

            Semaphore s = opponent.getGameClientHandler().getSemaphore();
            s.release();
        }
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    private void createGameRoom(GameClient gameClient1, GameClient gameClient2) {
        GameRoom gameRoom = new GameRoom(1);
        gameClient1.setGameRoom(gameRoom);
        gameClient2.setGameRoom(gameRoom);
        gameRoom.setPlayer1(gameClient1);
        gameRoom.setPlayer2(gameClient2);
    }
}
