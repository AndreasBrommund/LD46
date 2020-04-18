package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Position implements Component {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
