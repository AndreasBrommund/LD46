package se.ld46.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import se.ld46.game.Config;
import se.ld46.game.Game;

import static se.ld46.game.Config.*;
import static se.ld46.game.Config.SCREEN_WIDTH;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Game();
        }
}