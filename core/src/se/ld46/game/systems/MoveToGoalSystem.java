package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import se.ld46.game.components.GoalPath;
import se.ld46.game.components.Position;
import se.ld46.game.pathfinding.Location;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class MoveToGoalSystem extends IntervalIteratingSystem {

    private ComponentMapper<GoalPath> goalPathComponentMapper = getFor(GoalPath.class);
    private ComponentMapper<Position> pm = getFor(Position.class);

    public MoveToGoalSystem(float interval) {
        super(Family.all(Position.class, GoalPath.class).get(), interval);
    }

    @Override
    protected void processEntity(Entity entity) {
        GoalPath g = goalPathComponentMapper.get(entity);
        if (g.step == g.pathToGoal.size()) {
            entity.remove(GoalPath.class);
            return;
        }
        Location nextPosition = g.pathToGoal.get(g.step);
        g.step++;
        Position p = pm.get(entity);
        p.x = nextPosition.x;
        p.y = nextPosition.y;
    }
}
