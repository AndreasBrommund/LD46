package se.ld46.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import se.ld46.game.components.*;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.systems.ClickToMoveSystem;
import se.ld46.game.systems.MapRenderingSystem;
import se.ld46.game.systems.RenderSystem;

import static se.ld46.game.AssetManagerWrapper.assetManagerWrapper;
import static se.ld46.game.input.GameInputProcessor.gameInputProcessor;

public class Game extends ApplicationAdapter {
    private GameInputProcessor inputProcessor;
    private Engine engine;

    @Override
    public void create() {
        engine = new Engine();
        Entity orc = new Entity();

        orc.add(new Position(5, 5));
        orc.add(new Size(1, 1));
        orc.add(new Visual(assetManagerWrapper().get("orc.png")));
        orc.add(new SelectedForMovement());

        Entity tiledMap = new Entity();
        tiledMap.add(new TiledMapWrapper(assetManagerWrapper().get("background.tmx")));

        engine.addEntity(orc);
        engine.addEntity(tiledMap);

        engine.addSystem(new MapRenderingSystem(Integer.MIN_VALUE, WorldCamera.worldCamera()));
        engine.addSystem(new RenderSystem(WorldCamera.worldCamera()));
        engine.addSystem(new ClickToMoveSystem());

        inputProcessor = gameInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(Gdx.graphics.getDeltaTime());
    }
}
