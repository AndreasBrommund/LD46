package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.ld46.game.Game;
import se.ld46.game.components.Countdown;
import se.ld46.game.components.Rescuabled;
import se.ld46.game.util.Config;

public class WinSystem extends IteratingSystem {

    private ComponentMapper<Countdown> countdownComponentMapper = ComponentMapper.getFor(Countdown.class);

    public WinSystem() {
        super(Family.all(Countdown.class, Rescuabled.class).get());
    }


    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        final Countdown countdown = countdownComponentMapper.get(entity);
        if (countdown.timeLeft <= 0) {
            Config.gameState = Game.GameState.WON;
        }
    }
}
