package se.ld46.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import se.ld46.game.components.*;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.TouchDownSubscriber;
import se.ld46.game.pathfinding.Location;
import se.ld46.game.util.WorldCamera;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class ClickEventSystem extends EntitySystem implements TouchDownSubscriber {

    private ComponentMapper<Position> pm = getFor(Position.class);
    private ComponentMapper<ClickableItem> clickableItemComponentMapper = getFor(ClickableItem.class);
    ImmutableArray<Entity> entities;
    ImmutableArray<Entity> players;

    public ClickEventSystem() {
        GameInputProcessor.gameInputProcessor().add(this);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(Position.class, ClickableItem.class).get());
        players = engine.getEntitiesFor(Family.all(SelectedForMovement.class).get());
    }

    @Override
    public void onTouchDown(int screenX, int screenY, int pointer, int button) {
        if (Input.Buttons.LEFT == button) {
            Gdx.app.log("CLICK ITEM", "TRIGGER");
            Vector3 unproject = WorldCamera.worldCamera().camera.unproject(new Vector3(screenX, screenY, 0));
            //TODO: fix this, should take a proper float position and see if it is inside the clickbox instead..
            int x = (int) Math.ceil(unproject.x);
            int y = (int) Math.ceil(unproject.y);
            clickOnItem(x, y);
        }
    }

    private boolean clickOnItem(int x, int y) {
        for (Entity entity : entities) {
            Position p = pm.get(entity);
            if (p.x == x && p.y == y) {
                decideOnAction(entity, p);
            }
        }
        moveToTile(x, y);
        return false;
    }

    private void decideOnAction(Entity entity, Position p) {
        ClickableItem clickableItem = clickableItemComponentMapper.get(entity);
        Gdx.app.log("CLICK ITEM", "Clicked on:" + clickableItem.clickType);
        entity.add(new ActionOrMoveToPosition(clickableItem.clickType, p));
    }

    private void moveToTile(int x, int y) {
        Gdx.app.log("CLICK ITEM", "No item pressed, will try to move to tile instead..");
        Position playerPos = pm.get(players.first());
        Location start = new Location(playerPos.x, playerPos.y);
        Location goal = new Location(x, y);
        players.first().add(new Pathfinding(start, goal));
    }
}
