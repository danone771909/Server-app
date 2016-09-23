package model;

import logic.GameClientHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class GameClient {
    @Setter
    private int id;

    @Setter
    private int level;

    @Setter
    private String route;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private GameClient opponent;

    @Setter
    @Getter
    private GameClientHandler gameClientHandler;

    @Setter
    @Getter
    private GameRoom gameRoom;

    @Setter
    @Getter
    private List<Float> checkpointsTime = new ArrayList<>();
}
