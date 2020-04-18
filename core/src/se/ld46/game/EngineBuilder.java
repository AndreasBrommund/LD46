package se.ld46.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;

import java.util.ArrayList;

public class EngineBuilder {

    public Engine engine;
    public ArrayList<Entity> entities;
    public ArrayList<EntitySystem> entitySystems;

    private EngineBuilder() {
        engine = new Engine();
        entities = new ArrayList<>();
        entitySystems = new ArrayList<>();
    }

    public static EngineBuilder engineBuilder() {
        return new EngineBuilder();
    }

    public EngineBuilder withEntity(Entity entity) {
        entities.add(entity);
        return this;
    }

    public EngineBuilder withEntitySystem(EntitySystem entitySystem) {
        entitySystems.add(entitySystem);
        return this;
    }

    public Engine build() {
        entities.forEach(entity -> engine.addEntity(entity));
        entitySystems.forEach(entitySystem -> engine.addSystem(entitySystem));
        return engine;
    }
}
