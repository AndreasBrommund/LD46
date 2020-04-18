package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import se.ld46.game.pathfinding.Location;

public class Pathfinding implements Component {
    public Location start;
    public Location goal;


    public Pathfinding(Location start, Location goal) {
        this.start = start;
        this.goal = goal;
    }
}
