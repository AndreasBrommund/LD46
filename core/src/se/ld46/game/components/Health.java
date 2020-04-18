package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Health implements Component {
    public int currentHealth;
    public int maxHealth;

    public Health(final int health) {
        this.currentHealth = health;
        this.maxHealth = health;
    }

    public Health(final int currentHealth, int maxHealth) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }
}
