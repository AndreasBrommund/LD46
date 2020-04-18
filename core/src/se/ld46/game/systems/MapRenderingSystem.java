package se.ld46.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import se.ld46.game.components.TiledMapWrapper;
import se.ld46.game.util.WorldCamera;

import static se.ld46.game.util.Config.TILE_SIZE;

public class MapRenderingSystem extends EntitySystem {
    private ComponentMapper<TiledMapWrapper> tiledMapComponentMapper = ComponentMapper.getFor(TiledMapWrapper.class);


    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public MapRenderingSystem(int priority, WorldCamera worldCamera) {
        super(priority);
        camera = worldCamera.camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(TiledMapWrapper.class).get());
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
        renderer.setView(camera);
        renderer.render();
    }
}
