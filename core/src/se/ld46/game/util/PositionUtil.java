package se.ld46.game.util;

import com.badlogic.gdx.math.Vector2;
import se.ld46.game.components.Position;

public class PositionUtil {

    public static float dst(Position a, Position b) {
        Vector2 aVec = new Vector2(a.x, a.y);
        Vector2 bVec = new Vector2(b.x, b.y);
        return aVec.dst(bVec);
    }
}
