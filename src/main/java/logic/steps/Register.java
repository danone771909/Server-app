package logic.steps;

import logic.messages.Message;
import logic.messages.Steps;
import model.GameClient;

public class Register extends GameStepBase {

    public Register(GameClient gameClient) {
        super(gameClient);
    }

    @Override
    public String run(Steps step, String clientResponse) {
        switch(step) {
            case START:
                return Message.REQUEST_CLIENT_ID.toString();
            case AFTER_CLIENT_ID:
                gameClient.setId(Integer.valueOf(clientResponse));
                return Message.REQUEST_CLIENT_NAME.toString();
            case AFTER_CLIENT_NAME:
                gameClient.setName(clientResponse);
                return Message.REQUEST_CLIENT_LEVEL.toString();
            case AFTER_CLIENT_LEVEL:
                gameClient.setLevel(Integer.valueOf(clientResponse));
                return Message.REQUEST_CLIENT_ROUTE.toString();
            case AFTER_CLIENT_ROUTE:
                gameClient.setRoute(clientResponse);
                return Message.WAIT_FOR_OPPONENT.toString();
        }

        return Message.NONE.toString();
    }
}
