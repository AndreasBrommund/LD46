package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Eat implements Component {
    public int feedLevel;

    public Eat(int feedLevel) {
        this.feedLevel = feedLevel;
    }
}
