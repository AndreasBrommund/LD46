package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class ClickableItem implements Component {
    public ClickType clickType;

    public ClickableItem(ClickType clickType) {
        this.clickType = clickType;
    }
}
