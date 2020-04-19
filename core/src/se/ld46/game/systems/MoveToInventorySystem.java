package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import se.ld46.game.components.Inventory;
import se.ld46.game.components.MoveToInventory;
import se.ld46.game.components.PickableItem;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class MoveToInventorySystem extends EntitySystem {
    public MoveToInventorySystem(int prio) {
        super(prio);
    }

    @Override
    public void update(final float deltaTime) {
        super.update(deltaTime);

        ImmutableArray<Entity> entitiesWithInventory = getEngine().getEntitiesFor(Family.all(Inventory.class).get());
        ImmutableArray<Entity> entitiesWithMoveToInventory = getEngine().getEntitiesFor(Family.all(MoveToInventory.class).get());

        ComponentMapper<PickableItem> pickableItemComponentMapper = getFor(PickableItem.class);

        ComponentMapper<Inventory> inventoryComponentMapper = getFor(Inventory.class);

        entitiesWithMoveToInventory.forEach(entity -> {
            PickableItem item = pickableItemComponentMapper.get(entity);
            entity.remove(MoveToInventory.class);

            if (entitiesWithInventory.size() != 1) {
                throw new IllegalStateException("Must be exactly one entity, size=" + entitiesWithInventory.size());
            }

            Inventory inventory = inventoryComponentMapper.get(entitiesWithInventory.first());

            for (int i = 0; i < inventory.empty.length; i++) {
                Texture texture = item.texture;
                if (inventory.empty[i]) {
                    inventory.empty[i] = false;
                    inventory.items[i] = () -> texture;
                    break;
                }
            }
        });
    }
}
