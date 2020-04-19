package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import se.ld46.game.Item;
import se.ld46.game.components.Health;
import se.ld46.game.components.Hunger;
import se.ld46.game.components.Inventory;

import java.util.Arrays;
import java.util.List;

import static se.ld46.game.util.Config.DEBUG;


public class HudSystem extends IteratingSystem {
    private ComponentMapper<Health> healthComponentMapper = ComponentMapper.getFor(Health.class);
    private ComponentMapper<Hunger> hungerComponentMapper = ComponentMapper.getFor(Hunger.class);
    private ComponentMapper<Inventory> inventoryComponentMapper = ComponentMapper.getFor(Inventory.class);

    private Stage stage;

    private final static int PADDING = 10;
    private final static int SIZE = 50;
    private final static LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);

    public HudSystem(int priority) {
        super(Family.all(Health.class, Hunger.class, Inventory.class).get(), priority);
        stage = new Stage();
    }


    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        Health health = healthComponentMapper.get(entity);
        Hunger hunger = hungerComponentMapper.get(entity);
        Inventory inventory = inventoryComponentMapper.get(entity);
        stage.clear();
        stage.addActor(inventoryTable(Arrays.asList(inventory.items)));
        stage.addActor(statsTable(Arrays.asList(health.print(), hunger.print())));
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
            final Label label = new Label(item.name(), labelStyle);
            label.setAlignment(Align.center);
            table.add(label).width(SIZE).height(SIZE);
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
