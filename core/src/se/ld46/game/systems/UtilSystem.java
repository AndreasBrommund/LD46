package se.ld46.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Input;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.KeyDownSubscriber;

public class UtilSystem extends EntitySystem implements KeyDownSubscriber {
    boolean debugPress = false;
    private final CollisionMapRendered collisionMapRendered;
    private final TiledDebugMapRendered tiledDebugMapRendered;

    public UtilSystem(CollisionMapRendered collisionMapRendered, TiledDebugMapRendered tiledDebugMapRendered) {
        this.collisionMapRendered = collisionMapRendered;
        this.tiledDebugMapRendered = tiledDebugMapRendered;
        GameInputProcessor.gameInputProcessor().add(this);
    }

    @Override
    public void onKeyDown(int keycode) {
        if (Input.Keys.O == keycode) {
            debugPress = true;
        }
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        if (debugPress) {
            tiledDebugMapRendered.setProcessing(!tiledDebugMapRendered.checkProcessing());
            collisionMapRendered.setProcessing(!collisionMapRendered.checkProcessing());
            debugPress = false;
        }

    }
}
