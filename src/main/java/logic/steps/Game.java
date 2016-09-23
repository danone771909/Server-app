package logic.steps;

import logic.messages.Message;
import logic.messages.Steps;
import model.GameClient;

import java.util.List;

public class Game extends GameStepBase {

    public Game(GameClient gameClient) {
        super(gameClient);
    }

    @Override
    public String run(Steps step, String clientResponse) {

        switch (step) {
            case START_GAME:
                return gameClient.getOpponent().getName() + " - Opponent and Client " + gameClient.getName();
            case DURING_GAME:
                List<Float> checkpointTime = gameClient.getCheckpointsTime();
                if(!"wait".equals(clientResponse))
                    checkpointTime.add(Float.valueOf(clientResponse));
                gameClient.getGameRoom().checkCkeckpoint();
                return Message.CHECKPOINT.toString();
            case AFTER_FINISH:
                return Message.FINALLY.toString();
        }

        return Message.NONE.toString();
    }
}
