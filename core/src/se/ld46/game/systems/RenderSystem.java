package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.ld46.game.WorldCamera;
import se.ld46.game.components.Position;
import se.ld46.game.components.Size;
import se.ld46.game.components.Visual;

public class RenderSystem extends IteratingSystem {

    private ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private ComponentMapper<Size> sizeMapper = ComponentMapper.getFor(Size.class);
    private ComponentMapper<Visual> visualMapper = ComponentMapper.getFor(Visual.class);


    private SpriteBatch batch;
    private OrthographicCamera camera;

    public RenderSystem(WorldCamera camera) {
        super(Family.all(Visual.class, Position.class, Size.class).get());
        this.camera = camera.value;
        this.batch = new SpriteBatch();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        Position position = positionMapper.get(entity);
        Visual visual = visualMapper.get(entity);
        Size size = sizeMapper.get(entity);

        batch.draw(
                visual.texture,
                position.x,
                position.y,
                size.width,
                size.height);
        batch.end();

    }
}