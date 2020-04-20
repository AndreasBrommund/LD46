package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import se.ld46.game.systems.FishingSystem;


public class Fishing implements Component {
    public int period;

    public Fishing() {
        this.period = FishingSystem.getGaussian();
    }
}
