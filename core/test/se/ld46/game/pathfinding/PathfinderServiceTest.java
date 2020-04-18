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
        ArrayList<Location> steps = pathfinderService.find(map, new Location(startLocationX, startLocationY), new Location(goalLocationX, goalLocationY));

        assertEquals(6, steps.size());
        assertEquals(new Location(1, 1), steps.get(0));
        assertEquals(new Location(1, 2), steps.get(1));
        assertEquals(new Location(1, 3), steps.get(2));
        assertEquals(new Location(1, 4), steps.get(3));
        assertEquals(new Location(2, 4), steps.get(4));
        assertEquals(new Location(3, 4), steps.get(5));
    }


    @Test
    public void will_return_zero_steps_if_not_path_is_possible() {

        //given
        int[][] map = new int[][]{
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 1, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };

        int startLocationX = 1;
        int startLocationY = 1;

        int goalLocationX = 3;
        int goalLocationY = 4;


        //when
        PathfinderService pathfinderService = new PathfinderService();
        ArrayList<Location> steps = pathfinderService.find(map, new Location(startLocationX, startLocationY), new Location(goalLocationX, goalLocationY));

        assertEquals(0, steps.size());
    }

    @Test
    public void find_another_path() {

        //given
        int[][] map = new int[][]{
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };

        int startLocationX = 1;
        int startLocationY = 1;

        int goalLocationX = 1;
        int goalLocationY = 4;


        //when
        PathfinderService pathfinderService = new PathfinderService();
        ArrayList<Location> steps = pathfinderService.find(map, new Location(startLocationX, startLocationY), new Location(goalLocationX, goalLocationY));

        assertEquals(12, steps.size());
    }


    @Test
    public void slow_test_map() {
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

        Location goal = new Location(18, 12);
        Location start = new Location(1, 1);

        PathfinderService pathfinderService = new PathfinderService();
        long startTime = System.currentTimeMillis();
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time: " + totalTime + " ms");
    }


}