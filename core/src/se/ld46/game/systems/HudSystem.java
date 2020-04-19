package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import se.ld46.game.Item;
import se.ld46.game.components.Fire;
import se.ld46.game.components.Health;
import se.ld46.game.components.Hunger;
import se.ld46.game.components.Inventory;

import java.util.Arrays;
import java.util.List;

import static com.badlogic.ashley.core.ComponentMapper.getFor;
import static se.ld46.game.util.Config.DEBUG;


public class HudSystem extends IteratingSystem {
    private ComponentMapper<Health> healthComponentMapper = getFor(Health.class);
    private ComponentMapper<Hunger> hungerComponentMapper = getFor(Hunger.class);
    private ComponentMapper<Inventory> inventoryComponentMapper = getFor(Inventory.class);
    private ComponentMapper<Fire> fireComponentMapper = getFor(Fire.class);
    private Stage stage;

    ImmutableArray<Entity> fire;
    private final static int PADDING = 10;
    private final static int SIZE = 100;
    private final static LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);

    public HudSystem(int priority) {
        super(Family.all(Health.class, Hunger.class, Inventory.class).get(), priority);
        stage = new Stage();

    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        fire = engine.getEntitiesFor(Family.all(Fire.class).get());
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {

        stage.clear();
        Health health = healthComponentMapper.get(entity);
        Hunger hunger = hungerComponentMapper.get(entity);
        Inventory inventory = inventoryComponentMapper.get(entity);
        stage.addActor(inventoryTable(Arrays.asList(inventory.items)));
        Fire f = fireComponentMapper.get(fire.first());
        String s = String.format("Fire: %s", f.fuel);
        stage.addActor(statsTable(Arrays.asList(health.print(), hunger.print(), s)));

        stage.draw();

    }


    private Table inventoryTable(List<Item> items) {
        Table table = new Table()
                .left()
                .top()
                .padTop(PADDING)
                .padLeft(PADDING);

        if (DEBUG) {
            table.debugAll();
        }

        items.forEach(item -> {
            final Image image = new Image(item.texture());
            table.add(image);
            table.row();
        });

        table.setFillParent(true);

        return table;
    }

    private Table statsTable(List<String> stats) {
        Table table = new Table()
                .center()
                .top()
                .padTop(PADDING);

        if (DEBUG) {
            table.debugAll();
        }

        stats.forEach(s -> {
            final Label label = new Label(s, labelStyle);
            label.setAlignment(Align.center);
            table.add(label).width(SIZE).height(SIZE);

        });

        table.setFillParent(true);

        return table;
    }
}
