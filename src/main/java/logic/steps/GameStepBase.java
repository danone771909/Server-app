package logic.steps;

import model.GameClient;

public abstract class GameStepBase implements GameStep {
    protected GameClient gameClient;

    public GameStepBase(GameClient gameClient) {
        this.gameClient = gameClient;
    }
}
