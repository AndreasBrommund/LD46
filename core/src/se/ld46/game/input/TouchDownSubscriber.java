package se.ld46.game.input;

public interface TouchDownSubscriber {
    boolean onTouchDown(int screenX, int screenY, int pointer, int button);
}
