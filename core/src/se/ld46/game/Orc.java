package se.ld46.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import se.ld46.game.input.TouchDownSubscriber;
import se.ld46.game.pathfinding.Location;
import se.ld46.game.pathfinding.PathfinderService;

import java.util.ArrayList;

import static se.ld46.game.AssetManagerWrapper.assetManagerWrapper;
import static se.ld46.game.input.GameInputProcessor.gameInputProcessor;

public class Orc implements TouchDownSubscriber, Disposable, Renderable {

    private int x;
    private int y;

    private final PathfinderService pathfinderService = new PathfinderService();
    private int step;

    private int moveX;
    private int moveY;
    private boolean calculateMove = false;
    private boolean move = false;
    private boolean timeForNextStep = false;
    private float timeSeconds = 0f;
    private float period = 300f;


    private ArrayList<Location> pathToMoveAlong = new ArrayList<>();

    int[][] map = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };


    public Orc() {
        gameInputProcessor().add(this);
        x = 1;
        y = 1;
    }

    @Override
    public void onTouchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("Debug", "Orc noticed click");
        Gdx.app.log("debug", "SCREEN: " + screenX + " : " + screenY);
        Gdx.app.log("debug", "SCREEN to world:" + WorldCamera.worldCamera().value.unproject(new Vector3(screenX, screenY, 0)));
        moveX = 18;
        moveY = 12;

        calculateMove = true;
    }


    @Override
    public void render(SpriteBatch batch) {

//        if (calculateMove) {
//
//            Location start = new Location(x, y);
//            Location goal = new Location(moveX, moveY);
//
//            Gdx.app.log("DEBUG", "will find path");
//            Gdx.app.log("DEBUG", "GOAL IS: " + goal);
//            pathToMoveAlong = pathfinderService.find(map, start, goal);
//            Gdx.app.log("DEBUG", "Path found");
//            calculateMove = false;
//            move = true;
//
//        }

        if (move) {
            Location nextLocation = pathToMoveAlong.get(step);
            x = nextLocation.x;
            y = nextLocation.y;
            Gdx.app.log("DEBUG", "UPDATED POS");
            timeForNextStep = false;
            step++;


            if (step == pathToMoveAlong.size()) {
                move = false;
                Gdx.app.log("DEBUG", "REACHED GOAL");
                step = 0;
            }
        }


        batch.draw((Texture) assetManagerWrapper().get("orc.png"), x, y, 1, 1);
    }

    @Override
    public void dispose() {

    }
}
