package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.ld46.game.components.Dialog;
import se.ld46.game.util.Config;

public class DialogSystem extends IteratingSystem {

    private ComponentMapper<Dialog> dialogComponentMapper = ComponentMapper.getFor(Dialog.class);

    private SpriteBatch batch;
    BitmapFont font;

    public DialogSystem() {
        super(Family.all(Dialog.class).get());
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        final Dialog dialog = dialogComponentMapper.get(entity);
        if (dialog.time <= 0) {
            entity.remove(Dialog.class);
            return;
        }
        batch.begin();
        font.draw(batch, dialog.message, (float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2);
        batch.end();
        dialog.time = dialog.time - deltaTime;

    }
}
