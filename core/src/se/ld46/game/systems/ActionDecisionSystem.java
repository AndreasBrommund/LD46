package se.ld46.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import se.ld46.game.components.*;
import se.ld46.game.pathfinding.Location;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class ActionDecisionSystem extends EntitySystem {


    private ComponentMapper<SelectedForMovement> sfm = getFor(SelectedForMovement.class);
    private ComponentMapper<Position> pm = getFor(Position.class);
    private ComponentMapper<ActionDecision> adm = getFor(ActionDecision.class);
    private ComponentMapper<PickableItem> pim = getFor(PickableItem.class);
    ImmutableArray<Entity> movementEntities;
    ImmutableArray<Entity> actionDecisionEntites;

    public ActionDecisionSystem() {
    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        movementEntities = engine.getEntitiesFor(Family.all(SelectedForMovement.class).get());
        actionDecisionEntites = engine.getEntitiesFor(Family.all(ActionDecision.class).get());
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (actionDecisionEntites.size() > 0) {
            Gdx.app.log("ACTION DECISION", "A needs to be made!");

            actionDecisionEntites.forEach(e -> {

                //Pickup or move to target?
                Position playerPos = pm.get(movementEntities.first());
                Vector2 playerVec = new Vector2(playerPos.x, playerPos.y);

                ActionDecision ad = adm.get(e);
                Gdx.app.log("DST", "The distance is " + playerVec.dst(ad.clicked));
                if (playerVec.dst(ad.clicked) < 2.5f) {
                    //no need to move to target
                    if (pim.has(e)) {
                        e.add(new MoveToInventory());
                    }
                    e.remove(Visual.class);
                } else {
                    //need  to move to target..
                    Location start = new Location(playerPos.x, playerPos.y);
                    Location goal = new Location((int) ad.clicked.x, (int) ad.clicked.y);
                    movementEntities.first().add(new Pathfinding(start, goal));
                }

                e.remove(ActionDecision.class);
            });

        }
    }
}
