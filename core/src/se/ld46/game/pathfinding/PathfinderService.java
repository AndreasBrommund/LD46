package se.ld46.game.pathfinding;

import java.util.*;

public class PathfinderService {

    public ArrayList<Pos> find(int[][] map, int startLocationX, int startLocationY, int goalLocationX, int goalLocationY) {
        ArrayList<Location> result = astar(map, startLocationX, startLocationY, goalLocationX, goalLocationY);

        for (Location location : result) {
            System.out.println(location);
        }

        int shortestPath = Integer.MAX_VALUE;
        Location winner = null;
        for (Location l : result) {
            int path = 0;
            Location currrentSuccesor = l.succesor;
            while (currrentSuccesor != null) {
                path++;
                currrentSuccesor = currrentSuccesor.succesor;
            }

            if (path < shortestPath) {
                shortestPath = path;
                winner = l;
            }

        }

        System.out.println("Winner: " + winner);

        ArrayList<Pos> winnerResult = new ArrayList<>();
        winnerResult.add(new Pos(winner));
        Location currrentSuccesor = winner.succesor;
        while (currrentSuccesor != null) {
            winnerResult.add(new Pos(currrentSuccesor));
            currrentSuccesor = currrentSuccesor.succesor;
        }

        Collections.reverse(winnerResult);
        System.out.println(winnerResult);
        winnerResult.remove(0);
        return winnerResult;
    }


    ArrayList<Location> astar(int[][] map, int startX, int startY, int goalX, int goalY) {

        ArrayList<Location> pathsToGoal = new ArrayList<>();
        HashMap<Location, Integer> openList = new HashMap<>(); //TODO: figure out if this should be a min heap instead.
        HashSet<Location> closedList = new HashSet<>();

        //put starting location in openlist
        openList.put(new Location(startX, startY, null), 0);


        while (!openList.isEmpty()) {

            LocationAndTravelCost locationAndTravelCost = findLocationWithLowestTravelCost(openList);
            Location currentLocation = locationAndTravelCost.location;
            int currentTravelCost = locationAndTravelCost.travelCost;

            openList.remove(currentLocation);

            ArrayList<Location> successors = generateSuccessor(currentLocation);
            for (Location successor : successors) { //process each successor location

                //check if goal then stop!
                if (hasSuccessorReachedGoal(goalX, goalY, successor)) {
                    pathsToGoal.add(successor);
                    break;
                }

                // If the successor is already on the closed
                // list or if it is blocked, we skipp
                if (map[successor.y][successor.x] == 0 && !closedList.contains(successor)) {
                    int successorF = calculateTravelCostToSuccessor(currentTravelCost, successor, new Location(goalX, goalY, null));
                    if (openList.containsKey(successor)) {
                        //if the succesor location is in the open list check if this is a path with lower cost.
                        int potentiallyBetterF = openList.get(successor);
                        if (potentiallyBetterF < successorF) {
                            openList.remove(successor);
                            openList.put(successor, successorF);
                        }
                    } else {
                        //add it to the openlist with new successor F value
                        openList.put(successor, successorF);
                    }
                }
            }
            closedList.add(currentLocation);
        }
        return pathsToGoal;
    }

    private boolean hasSuccessorReachedGoal(int goalX, int goalY, Location successor) {
        return successor.x == goalX && successor.y == goalY;
    }

    //TODO: this could be optimised by using a min heap instead.
    private LocationAndTravelCost findLocationWithLowestTravelCost(HashMap<Location, Integer> openList) {
        int travelCost = Integer.MAX_VALUE;
        Location node = null;

        for (Map.Entry<Location, Integer> locationIntegerEntry : openList.entrySet()) {
            if (locationIntegerEntry.getValue() < travelCost) {
                node = locationIntegerEntry.getKey();
                travelCost = locationIntegerEntry.getValue();
            }
        }
        return new LocationAndTravelCost(node, travelCost);
    }


    //This function basically checks all the potential new locations, one step from the current location and store them in a list
    //In this implementation we can move in all 8 directions and we move with a delta of 1 in each step.
    private ArrayList<Location> generateSuccessor(Location currentLocation) {
        ArrayList<Location> successors = new ArrayList<>();

        //might butcher the up down stuffs but whatever.
        //right
        Location right = new Location(currentLocation.x + 1, currentLocation.y, currentLocation);
        Location rightTop = new Location(currentLocation.x + 1, currentLocation.y - 1, currentLocation);
        Location rightBottom = new Location(currentLocation.x + 1, currentLocation.y + 1, currentLocation);

        //left
        Location left = new Location(currentLocation.x - 1, currentLocation.y, currentLocation);
        Location leftTop = new Location(currentLocation.x - 1, currentLocation.y - 1, currentLocation);
        Location leftBottom = new Location(currentLocation.x - 1, currentLocation.y + 1, currentLocation);

        //up
        Location up = new Location(currentLocation.x, currentLocation.y + 1, currentLocation);

        //down
        Location down = new Location(currentLocation.x, currentLocation.y - 1, currentLocation);


        successors.add(up);
        successors.add(down);
        successors.add(left);
        successors.add(leftBottom);
        successors.add(leftTop);
        successors.add(right);
        successors.add(rightBottom);
        successors.add(rightTop);


        return successors;
    }

    private int calculateTravelCostToSuccessor(int currentTravelCost, Location successor, Location goal) {
        //the 1 could be changed to something better, e.g if we have some blocks that are mud and costs more to travel over..
        int newTravelCost = currentTravelCost + 1;
        //guestimate to add some heuristic to the algorithm
        int heurusitcOfTotalCostLeft = euclidDistance(successor, goal);
        return newTravelCost + heurusitcOfTotalCostLeft;
    }

    private int euclidDistance(Location successor, Location goal) {
        return (int) Math.sqrt(Math.pow(goal.x - successor.x, 2) + Math.pow(goal.y - successor.y, 2));
    }
}