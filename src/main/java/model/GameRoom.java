package model;


import lombok.Getter;
import lombok.Setter;

public class GameRoom {

    private final int id;

    @Setter
    @Getter
    private GameClient player1;

    @Setter
    @Getter
    private GameClient player2;
    private int checkpoint = 0;

    public GameRoom(int id) {
        this.id = id;
    }

    private void countTimeDiffrence() {
        float player1time = player1.getCheckpointsTime().get(checkpoint);
        float player2time = player2.getCheckpointsTime().get(checkpoint);
        float diff = player1time - player2time;

        if(diff < 0) {
            //System.out.println(player1.getName() + " is ahead " + diff);
        } else if(diff > 0) {
            //System.out.println(player2.getName() + " is ahead " + diff);
        } else {
            //System.out.println("Draw between! " + player1.getName() + " | " + player2.getName());
        }
        checkpoint++;
    }

    public void checkCkeckpoint() {
        int len = checkpoint+1;
        if(!player1.getCheckpointsTime().isEmpty() && !player2.getCheckpointsTime().isEmpty()) {
            if(player1.getCheckpointsTime().size() == len && player2.getCheckpointsTime().size() == len) {
                countTimeDiffrence();
            }
        }
    }
}
