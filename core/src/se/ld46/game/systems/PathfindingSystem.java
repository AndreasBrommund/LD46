package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import se.ld46.game.collisionmap.CollisionMap;
import se.ld46.game.components.GoalPath;
import se.ld46.game.components.Pathfinding;
import se.ld46.game.pathfinding.Location;
import se.ld46.game.pathfinding.PathfinderService;

import java.util.ArrayList;

public class PathfindingSystem extends IteratingSystem {
    private ComponentMapper<Pathfinding> pfm = ComponentMapper.getFor(Pathfinding.class);

    private PathfinderService pathfinderService;

    private int[][] collisionMap = CollisionMap.data;

    public PathfindingSystem() {
        super(Family.all(Pathfinding.class).get());
        pathfinderService = new PathfinderService();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Pathfinding p = pfm.get(entity);
        Gdx.app.log("DEBUG", "Pathfinding system");
        ArrayList<Location> pathToGoal = pathfinderService.find(collisionMap, p.start, p.goal);
        if (pathToGoal.size() != 0) {
            entity.add(new GoalPath(pathToGoal));
        }
        entity.remove(Pathfinding.class);
    }
}
