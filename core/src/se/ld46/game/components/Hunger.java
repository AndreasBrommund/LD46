package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Hunger implements Component {
    public int current;
    public int max;

    public Hunger(final int max) {
        this.current = 0;
        this.max = max;
    }

    public Hunger(final int current, final int max) {
        this.current = current;
        this.max = max;
    }

    public String print() {
        return current + "/" + max;
    }
}