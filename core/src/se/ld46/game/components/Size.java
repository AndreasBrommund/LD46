package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Size implements Component {
    public int width;
    public int height;
    public float offset;

    public Size(int width, int height, float offset) {
        this.width = width;
        this.height = height;
        this.offset = offset;
    }
}
