package se.ld46.game.components;

import com.badlogic.ashley.core.Component;

public class Dialog implements Component {
    public float time; //Seconds
    public String message;

    public Dialog(final String message, final float time) {
        this.message = message;
        this.time = time;
    }
}
