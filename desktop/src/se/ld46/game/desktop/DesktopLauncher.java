package se.ld46.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se.ld46.game.Game;

import static se.ld46.game.util.Config.SCREEN_HEIGHT;
import static se.ld46.game.util.Config.SCREEN_WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SCREEN_WIDTH;
		config.height = SCREEN_HEIGHT;
		new LwjglApplication(new Game(), config);
	}
}
