package se.ld46.game.input;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

public class GameInputProcessor implements InputProcessor {

    private final ArrayList<TouchDownSubscriber> touchDownSubscribers = new ArrayList<>();

    public void add(TouchDownSubscriber subscriber) {
        touchDownSubscribers.add(subscriber);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (TouchDownSubscriber subscriber : touchDownSubscribers) {
            subscriber.onTouchDown(screenX, screenY, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
