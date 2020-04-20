package se.ld46.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import se.ld46.game.collisionmap.CollisionMap;
import se.ld46.game.components.Hunger;
import se.ld46.game.components.ItemType;
import se.ld46.game.entityfactories.*;
import se.ld46.game.systems.*;
import se.ld46.game.util.Config;

import java.util.ArrayList;
import java.util.function.Consumer;

import static se.ld46.game.input.GameInputProcessor.gameInputProcessor;
import static se.ld46.game.util.AssetManagerWrapper.*;
import static se.ld46.game.util.WorldCamera.worldCamera;


public class Game extends ApplicationAdapter {
    private Engine engine;

    @Override
    public void create() {


        CollisionMapRendered collisionMapRendered = new CollisionMapRendered(1, worldCamera());
        TiledDebugMapRendered tiledDebugMapRendered = new TiledDebugMapRendered(3, worldCamera());
        engine = EngineBuilder
                .engineBuilder()
                .withEntity(OrcFactory.create(55, 50, 2, 2))
                .withEntity(ItemFactory.create(46, 50, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(46, 48, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(40, 48, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(48, 40, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(20, 90, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(46, 10, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(47, 48, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(20, 60, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(70, 10, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(35, 10, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(70, 35, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(70, 70, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))
                .withEntity(ItemFactory.create(60, 55, 1, 1, assetManagerWrapper().get(ITEM_WOOD), assetManagerWrapper().get(ITEM_WOOD_TAKEN), ItemType.WOOD))


                .withEntity(ItemFactory.create(42,
                        47,
                        1,
                        1,
                        assetManagerWrapper().get(ROD),
                        assetManagerWrapper().get(ROD_TAKEN), ItemType.ROD))
                .withEntity(MousePointerFactory.create(47, 50))
                .withEntity(FireFactory.create(50, 50, 1, 1, assetManagerWrapper().get(NO_FIRE)))
                .withEntity(TiledMapFactory.create())
                .withEntitySystem(new MapRenderingSystem(0, worldCamera()))
                .withEntitySystem(collisionMapRendered)
                .withEntitySystem(new RenderSystem(worldCamera(), 2))
                .withEntitySystem(new CameraControlSystem(4, worldCamera()))
                .withEntitySystem(new HungerSystem(Hunger.INCREASE_RATE, 2))
                .withEntitySystem(new EatSystem())
                .withEntitySystem(new ClickItemSystem())
                .withEntitySystem(new ClickToMoveSystem())
                .withEntitySystem(new PathfindingSystem())
                .withEntitySystem(new MoveToGoalSystem(0.05f))
                .withEntitySystem(tiledDebugMapRendered)
                .withEntitySystem(new UtilSystem(collisionMapRendered, tiledDebugMapRendered))
                .withEntitySystem(new HudSystem(6))
                .withEntitySystem(new MousePointerSystem(10))
                .withEntitySystem(new ActionOrMoveToSystem())
                .withEntitySystem(new FireInteractionSystem(50))
                .withEntitySystem(new ActionOnceCloseSystem(51))
                .withEntitySystem(new HealthSystem(0))
                .withEntitySystem(new FireSystem(1))
                .withEntitySystem(new TakeingSystem(20))
                .withEntitySystem(new FishingSystem())
                .withEntitySystem(new MoveToInventorySystem(21))
                .withEntitySystem(new RemovingSystem(Integer.MAX_VALUE))
                .withEntitySystem(new NightRenderingSystem())
                .withEntitySystem(new FishGeneratorSystem(2))
                .withEntitySystem(new CountdownSystem())
                .withEntitySystem(new WinSystem())
                .withEntitySystem(new DialogSystem())
                .build();


        TiledMap t = assetManagerWrapper().get(BACKGROUND_TMX);
        TiledMapTileLayer a = (TiledMapTileLayer) t.getLayers().get(0);


        ArrayList<Vector2> blockedPositions = new ArrayList<>();
        t.getLayers().get("Collisions").getObjects().getByType(TiledMapTileMapObject.class).forEach(r -> {
            float x = (int) (r.getX() / Config.TILE_SIZE);
            float y = (int) (Config.WORLD_HEIGHT - r.getY() / Config.TILE_SIZE);
            blockedPositions.add(new Vector2(x, y));
        });

        CollisionMap.createCollisionMap(blockedPositions);
        CollisionMap.data[49][49] = 1; //N.b THIS IS THE FIRES POSITON


        Gdx.input.setInputProcessor(gameInputProcessor());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Config.gameState.task.accept(engine);
    }


    public enum GameState {
        RUN(engine -> {
            engine.update(Gdx.graphics.getDeltaTime());
        }),
        GAME_OVER(engine -> {
            SpriteBatch batch = new SpriteBatch();
            BitmapFont font = new BitmapFont();
            batch.begin();
            font.draw(batch, "GAME OVER!", (float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2);
            batch.end();
        }),
        WON(engine -> {
            SpriteBatch batch = new SpriteBatch();
            BitmapFont font = new BitmapFont();
            batch.begin();
            font.draw(batch, "YOU SURVIVED!", (float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2);
            batch.end();
        });

        public transient final Consumer<Engine> task;

        GameState(final Consumer<Engine> task) {
            this.task = task;
        }
    }

}
