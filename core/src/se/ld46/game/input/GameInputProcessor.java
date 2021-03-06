package se.ld46.game.input;

import com.badlogic.gdx.InputProcessor;
import se.ld46.game.systems.ScrollSubscriber;

import java.util.ArrayList;

public class GameInputProcessor implements InputProcessor {
    private static GameInputProcessor gameInputProcessor = null;

    private final ArrayList<TouchDownSubscriber> touchDownSubscribers = new ArrayList<>();
    private final ArrayList<KeyDownSubscriber> keyDownSubscribers = new ArrayList<>();
    private final ArrayList<MouseMoveSubscriber> mouseMoveSubscribers = new ArrayList<>();
    private final ArrayList<ScrollSubscriber> scrollSubscribers = new ArrayList<>();

    public void add(TouchDownSubscriber subscriber) {
        touchDownSubscribers.add(subscriber);
    }

    public void add(KeyDownSubscriber subscriber) {
        keyDownSubscribers.add(subscriber);
    }

    public void add(MouseMoveSubscriber subscriber) {
        mouseMoveSubscribers.add(subscriber);
    }

    public void add(ScrollSubscriber subscriber) {
        scrollSubscribers.add(subscriber);
    }

    private GameInputProcessor() {
    }

    public static GameInputProcessor gameInputProcessor() {
        if (gameInputProcessor == null) {
            gameInputProcessor = new GameInputProcessor();
        }
        return gameInputProcessor;
    }

    @Override
    public boolean keyDown(int keycode) {
        keyDownSubscribers.forEach(subscriber -> subscriber.onKeyDown(keycode));
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
        touchDownSubscribers.forEach(s -> s.onTouchDown(screenX, screenY, pointer, button));
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
        mouseMoveSubscribers.forEach(s -> s.onMouseMove(screenX, screenY));
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        scrollSubscribers.forEach(s -> s.scrolled(amount));
        return false;
    }
}
