package se.ld46.game.input;

public interface TouchDownSubscriber {
    void onTouchDown(int screenX, int screenY, int pointer, int button);
}
