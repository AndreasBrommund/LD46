package se.ld46.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.ld46.game.input.TouchDownSubscriber;
import se.ld46.game.pathfinding.Location;
import se.ld46.game.pathfinding.PathfinderService;

import java.util.ArrayList;

public class Orc implements TouchDownSubscriber {

    private int x;
    private int y;

    private final Texture orcImage;
    private final PathfinderService pathfinderService = new PathfinderService();
    private int step;

    private int moveX;
    private int moveY;
    private boolean calculateMove = false;
    private boolean move = false;
    private boolean timeForNextStep = false;
    private float timeSeconds = 0f;
    private float period = 1f;


    private ArrayList<Location> pathToMoveAlong = new ArrayList<>();

    int[][] map = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };


    public Orc() {
        orcImage = new Texture("orc.png");
        Game.INPUT_PROCESSOR.add(this);
        x = 1;
        y = 1;
    }

    @Override
    public void onTouchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Someone clicked on the screen!");


        moveX = screenX / 32;
        moveY = 15 - (screenY / 32);

        calculateMove = true;
    }


    void update(SpriteBatch batch) {

        if (calculateMove) {

            Location start = new Location(x, y);
            Location goal = new Location(moveX, moveY);

            Gdx.app.log("DEBUG", "will find path");
            Gdx.app.log("DEBUG", "GOAL IS: " + goal);
            pathToMoveAlong = pathfinderService.find(map, start, goal);
            Gdx.app.log("DEBUG", "Path found");
            calculateMove = false;
            move = true;

        }

        if (move) {

            //Execute handleEvent each 1 second
            timeSeconds += Gdx.graphics.getRawDeltaTime();
            if (timeSeconds > period) {
                timeSeconds = 0;
                timeForNextStep = true;
                Gdx.app.log("DEBUG", "TIME TO STEP");

            }


            if (timeForNextStep) {
                Location nextLocation = pathToMoveAlong.get(step);
                x = nextLocation.x;
                y = nextLocation.y;
                Gdx.app.log("DEBUG", "UPDATED POS");
                timeForNextStep = false;
                step++;
            }


            if (step == pathToMoveAlong.size()) {
                move = false;
                Gdx.app.log("DEBUG", "REACHED GOAL");
                step = 0;
            }
        }


        batch.draw(orcImage, x, y, 1, 1);
    }

    void dispose() {
        orcImage.dispose();
    }
}
