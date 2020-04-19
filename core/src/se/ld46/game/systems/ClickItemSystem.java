package se.ld46.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import se.ld46.game.components.ActionOrMoveToPosition;
import se.ld46.game.components.ClickableItem;
import se.ld46.game.components.Position;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.TouchDownSubscriber;
import se.ld46.game.util.WorldCamera;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class ClickItemSystem extends EntitySystem implements TouchDownSubscriber {

    private ComponentMapper<Position> pm = getFor(Position.class);
    private ComponentMapper<ClickableItem> clickableItemComponentMapper = getFor(ClickableItem.class);
    ImmutableArray<Entity> entities;

    public ClickItemSystem() {


        GameInputProcessor.gameInputProcessor().add(this);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(Position.class, ClickableItem.class).get());
    }

    @Override
    public boolean onTouchDown(int screenX, int screenY, int pointer, int button) {
        if (Input.Buttons.LEFT == button) {
            Gdx.app.log("CLICK ITEM", "TRIGGER");
            Vector3 unproject = WorldCamera.worldCamera().camera.unproject(new Vector3(screenX, screenY, 0));
            int x = (int) Math.ceil(unproject.x);
            int y = (int) Math.ceil(unproject.y);
            return clickOnItem(x, y);
        }
        return false;
    }

    private boolean clickOnItem(int x, int y) {
        for (Entity entity : entities) {
            Position p = pm.get(entity);
            if (p.x == x && p.y == y) {
                decideOnAction(entity, p);
                return true;
            }
        }
        return false;
    }

    private void decideOnAction(Entity entity, Position p) {
        ClickableItem clickableItem = clickableItemComponentMapper.get(entity);
        entity.add(new ActionOrMoveToPosition(clickableItem.clickType, p));
    }


}
