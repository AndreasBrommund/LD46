package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import se.ld46.game.Item;
import se.ld46.game.components.*;

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
        Fire f = firemapper.get(fires.first());
        if (Arrays.stream(inventory.items).anyMatch(i -> i.type() == ItemType.WOOD)) {
            f.fuel += 10;
            fires.first().add(new Dialog("+10 Fuel", 1));
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
        } else if (Arrays.stream(inventory.items).anyMatch(fish -> fish.type() == ItemType.FISH)) {
            if (f.fuel > 0) {
                player.add(new Eat(MathUtils.random(1, 3)));
                for (int i = 0; i < inventory.items.length; i++) {
                    if (inventory.items[i].type() == ItemType.FISH) {
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
                fires.first().add(new Dialog("The fire is not burning. Can't cook fish.", 2));
            }
        } else {
            fires.first().add(new Dialog("Missing anything useful to put on fire. You need wood or fish.", 5));
            Gdx.app.log("DEBUG", "Missing anything useful to put on fire");
        }
    }
}
