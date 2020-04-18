package se.ld46.game.pathfinding;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathfinderServiceTest {


    @Test
    public void test_simple_path_finding() {

        //given
        int[][] map = new int[][]{
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };

        int startLocationX = 1;
        int startLocationY = 1;

        int goalLocationX = 3;
        int goalLocationY = 4;


        //when
        PathfinderService pathfinderService = new PathfinderService();
        ArrayList<Pos> steps = pathfinderService.find(map, startLocationX, startLocationY, goalLocationX, goalLocationY);

        assertEquals(4, steps.size());
        assertEquals(new Pos(1, 2), steps.get(0));
        assertEquals(new Pos(1, 3), steps.get(1));
        assertEquals(new Pos(2, 4), steps.get(2));
        assertEquals(new Pos(3, 4), steps.get(3));
    }

}