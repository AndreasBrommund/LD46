package se.ld46.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
    SpriteBatch batch;
    Texture orcImage;
    Texture backgroundImage;

    private float orcX = 320;
    private float orcY = 240;
    private float dx = 1;
    private float speed = 10;

    @Override
    public void create() {
        batch = new SpriteBatch();
        orcImage = new Texture("orc.png");
        backgroundImage = new Texture("background.png");
    }

    @Override
    public void render() {

        if (orcX + 32 > 640 || orcX < 0) {
            dx = dx * -1;
        }


        System.out.println(orcX);


        orcX += speed * dx;


        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundImage, 0, 0);
        batch.draw(orcImage, orcX, 240);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        orcImage.dispose();
    }
}
