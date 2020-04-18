package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Hunger implements Component {
    public int currentHunger;
    public int maxHunger;

    public Hunger(final int maxHunger) {
        this.currentHunger = 0;
        this.maxHunger = maxHunger;
    }

    public Hunger(final int currentHunger, final int maxHunger) {
        this.currentHunger = currentHunger;
        this.maxHunger = maxHunger;
    }
}
