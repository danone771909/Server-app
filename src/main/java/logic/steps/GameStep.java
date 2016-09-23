package logic.steps;

import logic.messages.Steps;

public interface GameStep {
    String run(Steps step, String clientResponse);
}
