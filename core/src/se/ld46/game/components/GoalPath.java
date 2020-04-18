package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import se.ld46.game.pathfinding.Location;

import java.util.ArrayList;

public class GoalPath implements Component {
    public ArrayList<Location> pathToGoal;

    public GoalPath(ArrayList<Location> pathToGoal) {
        this.pathToGoal = pathToGoal;
    }
}
