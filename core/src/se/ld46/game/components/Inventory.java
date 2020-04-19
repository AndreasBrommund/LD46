package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import se.ld46.game.Item;

import java.util.Arrays;

public class Inventory implements Component {

    public final static int SIZE = 5;

    public Item[] items = new Item[SIZE];

    public Inventory(Item[] items) {
        if (items.length > SIZE) {
            throw new IllegalStateException("To many items in the inventory");
        }
        Arrays.fill(this.items, (Item) () -> "Empty");
        System.arraycopy(items, 0, this.items, 0, items.length);
    }
}
