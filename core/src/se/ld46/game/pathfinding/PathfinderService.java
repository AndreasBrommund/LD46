package se.ld46.game.pathfinding;

import se.ld46.game.util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class PathfinderService {

    public ArrayList<Location> find(int[][] map, Location start, Location goal) {
        ArrayList<Location> result = astar(map, start, goal);
        Collections.reverse(result);
        return result;
    }

    ArrayList<Location> astar(int[][] map, Location start, Location goal) {

        ArrayList<Location> pathsToGoal = new ArrayList<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[][] openListContains = new boolean[Config.WORLD_HEIGHT][Config.WORLD_WIDTH];
        int[][] travelCosts = new int[Config.WORLD_HEIGHT][Config.WORLD_WIDTH];
        boolean[][] closedList = new boolean[Config.WORLD_HEIGHT][Config.WORLD_WIDTH];

        //put starting location in openlist
        openList.add(new Node(start, 0, null));
        openListContains[start.y][start.x] = true;
        travelCosts[start.y][start.x] = 0;

        boolean foundGoal = false;

        while (!openList.isEmpty() && !foundGoal) {

            Node currentNode = openList.poll();
            Location currentLocation = currentNode.location;
            int currentTravelCost = currentNode.travelCost;

            ArrayList<Location> successors = generateSuccessor(currentLocation, map);
            for (Location successor : successors) { //process each successor location

                if (hasSuccessorReachedGoal(goal, successor)) {
                    pathsToGoal = createPathToGoal(currentNode, successor);
                    foundGoal = true;
                    break;
                }

                // If the successor is not blocked or we have not already processed it, i.e it is in closedList
                if (map[successor.y][successor.x] == 0 && !closedList[successor.y][successor.x]) {
                    int successorTravelCost = calculateTravelCostToSuccessor(currentTravelCost, successor, goal);
                    if (openListContains[successor.y][successor.x]) {
                        //if the succesor location is in the open list check if this is a path with lower cost.
                        int existingSuccessorTravelCost = travelCosts[successor.y][successor.x];
                        if (existingSuccessorTravelCost < successorTravelCost) {
                            openList.add(new Node(successor, successorTravelCost, currentNode));
                            travelCosts[successor.y][successor.x] = successorTravelCost;
                        }
                    } else {
                        //add it to the openlist with new successor F value
                        openList.add(new Node(successor, successorTravelCost, currentNode));
                        openListContains[successor.y][successor.x] = true;
                        travelCosts[successor.y][successor.x] = successorTravelCost;
                    }
                }
            }
            closedList[currentLocation.y][currentLocation.x] = true;
        }
        return pathsToGoal;
    }

    private ArrayList<Location> createPathToGoal(Node startNode, Location successor) {
        ArrayList<Location> steps = new ArrayList<>();
        Node currentNode = startNode;
        steps.add(successor);
        while (currentNode != null) {
            steps.add(currentNode.location);
            currentNode = currentNode.successor;
        }
        return steps;
    }


    private boolean hasSuccessorReachedGoal(Location goal, Location successor) {
        return successor.x == goal.x && successor.y == goal.y;
    }

    //This function basically checks all the potential new locations, one step from the current location and store them in a list
    //In this implementation we can move in all 4 directions and we move with a delta of 1 in each step.
    public ArrayList<Location> generateSuccessor(Location currentLocation, int[][] map) {
        ArrayList<Location> successors = new ArrayList<>();

        //might butcher the up down stuffs but whatever.
        //right
        Location right = new Location(currentLocation.x + 1, currentLocation.y);

        //left
        Location left = new Location(currentLocation.x - 1, currentLocation.y);
        //up
        Location up = new Location(currentLocation.x, currentLocation.y + 1);

        //down
        Location down = new Location(currentLocation.x, currentLocation.y - 1);


        successors.add(up);
        successors.add(down);
        successors.add(left);
        successors.add(right);

        //Remove succesors outside of the world
        successors = successors.stream().filter(l -> 0 <= l.x && l.x < Config.WORLD_WIDTH && 0 <= l.y && l.y < Config.WORLD_HEIGHT).collect(Collectors.toCollection(ArrayList::new));

        //Remove successors that are blocked
        successors = successors.stream().filter(l -> map[l.y][l.x] == 0).collect(Collectors.toCollection(ArrayList::new));

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