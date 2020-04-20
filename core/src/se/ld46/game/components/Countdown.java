package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Countdown implements Component {
    public int timeLeft;

    public Countdown(final int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
