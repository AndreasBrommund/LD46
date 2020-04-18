package se.ld46.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import se.ld46.game.components.*;
import se.ld46.game.systems.*;

import static se.ld46.game.input.GameInputProcessor.gameInputProcessor;
import static se.ld46.game.util.AssetManagerWrapper.*;
import static se.ld46.game.util.WorldCamera.worldCamera;


public class Game extends ApplicationAdapter {
    private Engine engine;

    @Override
    public void create() {

        engine = new Engine();
        Entity orc = new Entity();

        orc.add(new Position(1, 1));
        orc.add(new Size(1, 1));
        orc.add(new Visual(assetManagerWrapper().get(ORC_PNG)));
        orc.add(new SelectedForMovement());

        Entity tiledMap = new Entity();
        tiledMap.add(new TiledMapWrapper(assetManagerWrapper().get(BACKGROUND_TMX)));

        engine.addEntity(orc);
        engine.addEntity(tiledMap);

        engine.addSystem(new MapRenderingSystem(0, worldCamera()));
        engine.addSystem(new CollisionMapRendered(1, worldCamera()));
        engine.addSystem(new RenderSystem(worldCamera(), 2));

        engine.addSystem(new ClickToMoveSystem());
        engine.addSystem(new PathfindingSystem());
        engine.addSystem(new MoveToGoalSystem(0.2f));
        engine.addSystem(new CameraControlSystem(Integer.MIN_VALUE, worldCamera())); // TODO: [A.B.]

        Gdx.input.setInputProcessor(gameInputProcessor());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(Gdx.graphics.getDeltaTime());
    }

}
