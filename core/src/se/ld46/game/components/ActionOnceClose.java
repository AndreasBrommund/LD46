package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class ActionOnceClose implements Component {
    public Class action;
    public float distance;
    public boolean onPlayer;

    public ActionOnceClose(Class action, float distance, boolean onPlayer) {
        this.action = action;
        this.distance = distance;
        this.onPlayer = onPlayer;
    }
}
