package se.ld46.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import se.ld46.game.collisionmap.CollisionMap;
import se.ld46.game.entityfactories.ItemFactory;
import se.ld46.game.entityfactories.MousePointerFactory;
import se.ld46.game.entityfactories.OrcFactory;
import se.ld46.game.entityfactories.TiledMapFactory;
import se.ld46.game.systems.*;
import se.ld46.game.util.Config;

import java.util.ArrayList;

import static se.ld46.game.input.GameInputProcessor.gameInputProcessor;
import static se.ld46.game.util.AssetManagerWrapper.BACKGROUND_TMX;
import static se.ld46.game.util.AssetManagerWrapper.assetManagerWrapper;
import static se.ld46.game.util.WorldCamera.worldCamera;


public class Game extends ApplicationAdapter {
    private Engine engine;

    @Override
    public void create() {


        CollisionMapRendered collisionMapRendered = new CollisionMapRendered(1, worldCamera());
        TiledDebugMapRendered tiledDebugMapRendered = new TiledDebugMapRendered(3, worldCamera());
        engine = EngineBuilder
                .engineBuilder()
                .withEntity(OrcFactory.create(48, 50, 2, 2))
                .withEntity(ItemFactory.create(40, 50, 1, 1))
                .withEntity(MousePointerFactory.create(47, 50))
                .withEntity(TiledMapFactory.create())
                .withEntitySystem(new MapRenderingSystem(0, worldCamera()))
                .withEntitySystem(collisionMapRendered)
                .withEntitySystem(new RenderSystem(worldCamera(), 2))
                .withEntitySystem(new CameraControlSystem(4, worldCamera()))
                .withEntitySystem(new HungerSystem(10, 2))
                .withEntitySystem(new ClickToMoveSystem())
                .withEntitySystem(new PathfindingSystem())
                .withEntitySystem(new MoveToGoalSystem(0.2f))
                .withEntitySystem(tiledDebugMapRendered)
                .withEntitySystem(new UtilSystem(collisionMapRendered, tiledDebugMapRendered))
                .withEntitySystem(new HudSystem(6))
                .withEntitySystem(new ClickItemSystem()) // TODO: [A.B.] item
                .withEntitySystem(new MousePointerSystem(10))
                .withEntitySystem(new ActionDecisionSystem())
                .build();


        TiledMap t = assetManagerWrapper().get(BACKGROUND_TMX);
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



        Gdx.input.setInputProcessor(gameInputProcessor());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(Gdx.graphics.getDeltaTime());
    }

}
