package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import se.ld46.game.Item;
import se.ld46.game.components.Fire;
import se.ld46.game.components.FireInteract;
import se.ld46.game.components.Inventory;
import se.ld46.game.components.ItemType;

import java.util.Arrays;

import static com.badlogic.ashley.core.ComponentMapper.getFor;
import static se.ld46.game.util.AssetManagerWrapper.EMPTY;
import static se.ld46.game.util.AssetManagerWrapper.assetManagerWrapper;

public class FireInteractionSystem extends IteratingSystem {
    private ComponentMapper<Inventory> inventoryComponentMapper = getFor(Inventory.class);
    private ComponentMapper<Fire> firemapper = getFor(Fire.class);
    ImmutableArray<Entity> fires;

    public FireInteractionSystem(int pr) {
        super(Family.all(FireInteract.class, Inventory.class).get(), pr);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        fires = engine.getEntitiesFor(Family.all(Fire.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Gdx.app.log("DEBUG", "Interacting with fire");
        clickOnFire(entity);
        entity.remove(FireInteract.class);
    }

    private void clickOnFire(Entity player) {
        Inventory inventory = inventoryComponentMapper.get(player);
        if (Arrays.stream(inventory.items).anyMatch(i -> i.type() == ItemType.WOOD)) {
            Fire f = firemapper.get(fires.first());
            f.fuel += 10;
            for (int i = 0; i < inventory.items.length; i++) {
                if (inventory.items[i].type() == ItemType.WOOD) {
                    inventory.items[i] = new Item() {
                        @Override
                        public Texture texture() {
                            return assetManagerWrapper().get(EMPTY);
                        }

                        @Override
                        public ItemType type() {
                            return ItemType.EMPTY;
                        }
                    };
                    break;
                }
            }
        } else {
            Gdx.app.log("DEBUG", "Missing wood");
        }
    }
}
