package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import se.ld46.game.components.Countdown;

public class CountdownSystem extends IntervalIteratingSystem {
    private ComponentMapper<Countdown> timeLeftUntilRescuedComponentMapper = ComponentMapper.getFor(Countdown.class);
    public final static int intervalTime = 1;

    public CountdownSystem() {
        super(Family.all(Countdown.class).get(), intervalTime);
    }

    @Override
    protected void processEntity(final Entity entity) {
        final Countdown countdown = timeLeftUntilRescuedComponentMapper.get(entity);
        countdown.timeLeft = countdown.timeLeft - intervalTime;
    }
}
