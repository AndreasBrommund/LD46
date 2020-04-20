package se.ld46.game.util;

import se.ld46.game.Game;

public final class Config {
    public final static int SCREEN_WIDTH = 1280;
    public final static int SCREEN_HEIGHT = 720;

    public final static int WORLD_WIDTH = 100;
    public final static int WORLD_HEIGHT = 100;

    public final static int DEFAULT_VIEWPORT_WIDTH = 80;
    public final static int DEFAULT_VIEWPORT_HEIGHT = 45;

    public final static int TILE_SIZE = 16;

    public static boolean DEBUG = false;

    public static Game.GameState gameState = Game.GameState.RUN;
}
