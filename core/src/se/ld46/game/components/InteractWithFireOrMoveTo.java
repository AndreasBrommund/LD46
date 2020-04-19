package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class InteractWithFireOrMoveTo implements Component {
    Position p;

    public InteractWithFireOrMoveTo(Position p) {
        this.p = p;
    }
}
