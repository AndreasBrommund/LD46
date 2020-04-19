package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Health implements Component {
    public int current;
    public int max;

    public Health(final int health) {
        this.current = health;
        this.max = health;
    }

    public Health(final int current, int max) {
        this.current = current;
        this.max = max;
    }

    public String print() {
        return "Health: " + current + "/" + max;
    }
}
