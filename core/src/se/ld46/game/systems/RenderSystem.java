package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.ld46.game.components.MousePointer;
import se.ld46.game.components.Position;
import se.ld46.game.components.Size;
import se.ld46.game.components.Visual;
import se.ld46.game.util.WorldCamera;

public class RenderSystem extends IteratingSystem {

    private ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private ComponentMapper<Size> sizeMapper = ComponentMapper.getFor(Size.class);
    private ComponentMapper<Visual> visualMapper = ComponentMapper.getFor(Visual.class);

    private OrthographicCamera camera;

    private SpriteBatch batch;

    public RenderSystem(WorldCamera worldCamera, int priority) {
        super(Family.all(Visual.class, Position.class, Size.class).exclude(MousePointer.class).get(), priority);
        this.batch = new SpriteBatch();
        camera = worldCamera.camera;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        Position position = positionMapper.get(entity);
        Visual visual = visualMapper.get(entity);
        Size size = sizeMapper.get(entity);

        batch.draw(
                visual.texture,
                position.x - size.width / size.offset,
                position.y - size.width / size.offset,
                size.width,
                size.height);
        batch.end();

    }
}