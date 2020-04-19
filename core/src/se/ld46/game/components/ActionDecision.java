package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ActionDecision implements Component {
    public Vector2 clicked;

    public ActionDecision(Vector2 clicked) {
        this.clicked = clicked;
    }
}
