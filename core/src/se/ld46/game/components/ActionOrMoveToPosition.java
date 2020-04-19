package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class ActionOrMoveToPosition implements Component {
    public ClickType action;
    public Position p;

    public ActionOrMoveToPosition(ClickType action, Position p) {
        this.action = action;
        this.p = p;
    }
}
