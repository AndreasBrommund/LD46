package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import se.ld46.game.Item;

import java.util.Arrays;

public class Inventory implements Component {

    public final static int SIZE = 5;
    public Item[] items = new Item[SIZE];

    public Inventory(Texture empty) {

        Arrays.fill(this.items, new Item() {
            @Override
            public Texture texture() {
                return empty;
            }

            @Override
            public ItemType type() {
                return ItemType.EMPTY;
            }
        });
    }
}
