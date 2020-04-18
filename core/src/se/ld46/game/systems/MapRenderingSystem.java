package se.ld46.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import se.ld46.game.WorldCamera;
import se.ld46.game.components.TiledMapWrapper;

import static se.ld46.game.Config.TILE_SIZE;

public class MapRenderingSystem extends EntitySystem {

    private ComponentMapper<TiledMapWrapper> tiledMapComponentMapper = ComponentMapper.getFor(TiledMapWrapper.class);
    private WorldCamera worldCamera;

    private OrthogonalTiledMapRenderer renderer;

    public MapRenderingSystem(int priority, WorldCamera worldCamera) {
        super(priority);
        this.worldCamera = worldCamera;

    }

    @Override
    public void addedToEngine(Engine engine) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.one(TiledMapWrapper.class).get());
        if (entities.size() != 1) {
            throw new IllegalStateException("You bad, only one tile map plz");
        }
        Entity e = entities.first();

        TiledMap background = tiledMapComponentMapper.get(e).tiledMap;
        renderer = new OrthogonalTiledMapRenderer(background, 1 / (float) TILE_SIZE);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderer.setView(worldCamera.value);
        renderer.render();
    }
}
