package se.ld46.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import se.ld46.game.collisionmap.CollisionMap;
import se.ld46.game.components.*;
import se.ld46.game.systems.*;
import se.ld46.game.util.Config;

import java.util.ArrayList;

import static se.ld46.game.AssetManagerWrapper.*;
import static se.ld46.game.input.GameInputProcessor.gameInputProcessor;
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


        TiledMap t = assetManagerWrapper().get("collisionmap/sample_map.tmx");
        TiledMapTileLayer a = (TiledMapTileLayer) t.getLayers().get(0);
        System.out.println(a.getHeight());
        System.out.println(a.getWidth());


        ArrayList<Vector2> blockedPositions = new ArrayList<>();
        t.getLayers().get("Collisions").getObjects().getByType(TiledMapTileMapObject.class).forEach(r -> {
            float x = (int) (r.getX() / Config.TILE_SIZE);
            float y = (int) (Config.WORLD_HEIGHT - r.getY() / Config.TILE_SIZE);
            blockedPositions.add(new Vector2(x, y));
        });

        CollisionMap.createCollisionMap(blockedPositions);


        engine.addEntity(orc);
        engine.addEntity(tiledMap);

        engine.addSystem(new MapRenderingSystem(0, worldCamera()));
        engine.addSystem(new CollisionMapRendered(1, worldCamera()));
        engine.addSystem(new RenderSystem(worldCamera(), 2));
        engine.addSystem(new TiledDebugMapRendered(3, worldCamera()));

        engine.addSystem(new ClickToMoveSystem());
        engine.addSystem(new PathfindingSystem());
        engine.addSystem(new MoveToGoalSystem(0.2f));
        engine.addSystem(new CameraControlSystem(Integer.MIN_VALUE, worldCamera())); // TODO: [A.B.]

        Gdx.input.setInputProcessor(gameInputProcessor());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(Gdx.graphics.getDeltaTime());
    }

}
