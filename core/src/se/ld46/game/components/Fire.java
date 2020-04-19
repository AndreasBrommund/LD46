package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Fire implements Component {
    public double fuel;

    public Fire(int fuel) {
        this.fuel = fuel;
    }
}
