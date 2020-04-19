package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.ld46.game.components.MoveToInventory;
import se.ld46.game.components.PickableItem;
import se.ld46.game.components.Remove;
import se.ld46.game.components.Takeing;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class TakeingSystem extends IteratingSystem {
    private ComponentMapper<PickableItem> pim = getFor(PickableItem.class);

    public TakeingSystem(int prio) {
        super(Family.all(Takeing.class).get(), prio);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (pim.has(entity)) {
            entity.add(new MoveToInventory()); //TODO: Mabe add to MoveToInventory what we are picking up?
        }
        entity.add(new Remove());
    }
}
