package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import se.ld46.game.Item;

import java.util.Arrays;

public class Inventory implements Component {

    public final static int SIZE = 5;

    public Item[] items = new Item[SIZE];
    public Boolean[] empty = new Boolean[SIZE];

    public Inventory(Item[] items, Texture empty) {
        if (items.length > SIZE) {
            throw new IllegalStateException("To many items in the inventory");
        }
        Arrays.fill(this.items, (Item) () -> empty);
        Arrays.fill(this.empty, true);
        for (int i = 0; i < items.length; i++) {
            this.items[i] = items[i];
            this.empty[i] = false;
        }
    }
}
