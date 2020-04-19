package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import se.ld46.game.Item;
import se.ld46.game.components.*;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.TouchDownSubscriber;
import se.ld46.game.util.WorldCamera;

import java.util.Arrays;

import static com.badlogic.ashley.core.ComponentMapper.getFor;
import static se.ld46.game.util.AssetManagerWrapper.*;

public class FireSystem extends IntervalIteratingSystem implements TouchDownSubscriber {

    private ComponentMapper<Fire> firemap = getFor(Fire.class);
    private ComponentMapper<Visual> vm = getFor(Visual.class);
    private ComponentMapper<Position> pm = getFor(Position.class);
    private ComponentMapper<Inventory> inventoryComponentMapper = getFor(Inventory.class);

    ImmutableArray<Entity> player;

    public FireSystem(float interval) {
        super(Family.all(Fire.class).get(), interval);
        GameInputProcessor.gameInputProcessor().add(this);
    }

    ImmutableArray<Entity> fires;


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        fires = engine.getEntitiesFor(Family.all(Fire.class).get());
        player = engine.getEntitiesFor(Family.all(Inventory.class).get());

    }

    @Override
    public void onTouchDown(int screenX, int screenY, int pointer, int button) {
        if (Input.Buttons.LEFT == button) {
            Vector3 unproject = WorldCamera.worldCamera().camera.unproject(new Vector3(screenX, screenY, 0));
            int x = (int) Math.ceil(unproject.x);
            int y = (int) Math.ceil(unproject.y);
            clickOnItem(x, y);
        }
    }

    private void clickOnItem(int x, int y) {
        for (Entity entity : fires) {
            Position p = pm.get(entity);
            if (p.x == x && p.y == y) {
                clickOnFire(entity);
            }
        }
    }

    private void clickOnFire(Entity entity) {
        Inventory inventory = inventoryComponentMapper.get(player.first());//TODO: there can only be one player
        for (Item item : inventory.items) {
            Gdx.app.log("INV", item.type().name());
        }
        if (Arrays.stream(inventory.items).anyMatch(i -> i.type() == ItemType.WOOD)) {
            Fire f = firemap.get(entity);
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
            Gdx.app.log("NO WOORD", "Missing wood");
        }
    }


    @Override
    protected void processEntity(Entity entity) {
        Visual v = vm.get(entity);
        Fire f = firemap.get(entity);
        if (f.fuel == 0) {
            v.texture = assetManagerWrapper().get(NO_FIRE);
        } else if (0 < f.fuel && f.fuel < 50) {
            v.texture = assetManagerWrapper().get(MIDDLE_FIRE);
        } else if (50 <= f.fuel) {
            v.texture = assetManagerWrapper().get(FULL_FIRE);
        }


        if (0 < f.fuel) {
            f.fuel -= 1;//1.0/6.0;
        } else {
            f.fuel = 0;
        }
    }
}
