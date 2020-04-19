package se.ld46.game;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import se.ld46.game.util.AssetManagerWrapper;
import se.ld46.game.util.Config;

public class NightRenderingSystem extends EntitySystem {

    SpriteBatch batch;
    Texture img;
    private float alpha = 0;

    public NightRenderingSystem() {
        batch = new SpriteBatch();
        img = AssetManagerWrapper.assetManagerWrapper().get(AssetManagerWrapper.NIGHT);
    }

    @Override
    public void update(float deltaTime) {

        alpha += 0.001f;

        super.update(deltaTime);
        batch.enableBlending();
        batch.begin();
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, Math.abs(MathUtils.sin(alpha))); //set alpha to 1
        batch.draw(img, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        batch.end();
    }
}
