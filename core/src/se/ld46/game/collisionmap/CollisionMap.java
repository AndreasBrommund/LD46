package se.ld46.game.collisionmap;

import com.badlogic.gdx.math.Vector2;
import se.ld46.game.util.Config;

import java.util.ArrayList;

public class CollisionMap {
    public static int[][] data = new int[Config.WORLD_HEIGHT][Config.WORLD_WIDTH];

    public static void createCollisionMap(ArrayList<Vector2> blocked) {
        for (Vector2 location : blocked) {
            data[(int) (Config.WORLD_HEIGHT - location.y)][(int) location.x] = 1;
        }
    }
}