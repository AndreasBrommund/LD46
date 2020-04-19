package se.ld46.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import se.ld46.game.components.ActionDecision;
import se.ld46.game.components.ClickableItem;
import se.ld46.game.components.ClickedItem;
import se.ld46.game.components.Position;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.TouchDownSubscriber;
import se.ld46.game.util.WorldCamera;

public class ClickItemSystem extends EntitySystem implements TouchDownSubscriber {

    private ComponentMapper<Position> pm = ComponentMapper.getFor(Position.class);
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
    public void onTouchDown(int screenX, int screenY, int pointer, int button) {
        if (Input.Buttons.LEFT == button) {
            Vector3 unproject = WorldCamera.worldCamera().camera.unproject(new Vector3(screenX, screenY, 0));
            Gdx.app.log("debug", "Click screen to world:" + unproject);
            int x = (int) Math.ceil(unproject.x);
            int y = (int) Math.ceil(unproject.y);
            clickOnItem(x, y);
        }
    }

    private void clickOnItem(int x, int y) {
        for (Entity entity : entities) {
            Position p = pm.get(entity);
            if (p.x == x && p.y == y) {
                entity.add(new ClickedItem());
                entity.add(new ActionDecision(new Vector2(p.x, p.y)));
            }
        }
    }
}
