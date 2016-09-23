package logic;

import logic.messages.Steps;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static logic.messages.Steps.*;

public class GameClientHandlerTest {

    @Test
    public void checkQueue() {
        Queue<Steps> queue = new ArrayDeque<>();
        queue.add(START);
        queue.add(AFTER_FINISH);
        queue.add(AFTER_CLIENT_NAME);

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
    }
}