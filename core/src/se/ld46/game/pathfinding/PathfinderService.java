package se.ld46.game.pathfinding;

import se.ld46.game.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PathfinderService {

    public ArrayList<Location> find(int[][] map, Location start, Location goal) {
        ArrayList<Location> result = astar(map, start, goal);
        Collections.reverse(result);
        return result;
    }

    ArrayList<Location> astar(int[][] map, Location start, Location goal) {

        ArrayList<Location> pathsToGoal = new ArrayList<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[][] closedList = new boolean[Config.WORLD_HEIGHT][Config.WORLD_WIDTH];

        //put starting location in openlist
        openList.add(new Node(start, 0, null));

        boolean foundGoal = false;

        while (!openList.isEmpty() && !foundGoal) {

            Node currentNode = openList.poll();
            Location currentLocation = currentNode.location;
            int currentTravelCost = currentNode.travelCost;

            ArrayList<Location> successors = generateSuccessor(currentLocation);
            for (Location successor : successors) { //process each successor location

                if (hasSuccessorReachedGoal(goal, successor)) {
                    pathsToGoal = createPathToGoal(currentNode, successor);
                    foundGoal = true;
                    break;
                }

                // If the successor is blocked or we have already processed it, i.e it is in closedList
                if (map[successor.y][successor.x] == 0 && !closedList[successor.y][successor.x]) {
                    int successorTravelCost = calculateTravelCostToSuccessor(currentTravelCost, successor, goal);
                    if (openList.contains(successor)) {
                        //if the succesor location is in the open list check if this is a path with lower cost.
                        Node existingSuccessorNode = getSuccessorNodeFrom(openList, successor);
                        if (existingSuccessorNode.travelCost < successorTravelCost) {
                            openList.remove(successor);
                            openList.add(new Node(successor, successorTravelCost, existingSuccessorNode.successor));
                        }
                    } else {
                        //add it to the openlist with new successor F value
                        openList.add(new Node(successor, successorTravelCost, currentNode));
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

    private Node getSuccessorNodeFrom(PriorityQueue<Node> openlist, Location l) {
        Iterator<Node> nodes = openlist.iterator();
        while (nodes.hasNext()) {
            Node node = nodes.next();
            if (node.location.equals(l)) {
                return node;
            }
        }
        return null;
    }

    //This function basically checks all the potential new locations, one step from the current location and store them in a list
    //In this implementation we can move in all 8 directions and we move with a delta of 1 in each step.
    private ArrayList<Location> generateSuccessor(Location currentLocation) {
        ArrayList<Location> successors = new ArrayList<>();

        //might butcher the up down stuffs but whatever.
        //right
        Location right = new Location(currentLocation.x + 1, currentLocation.y);
        Location rightTop = new Location(currentLocation.x + 1, currentLocation.y - 1);
        Location rightBottom = new Location(currentLocation.x + 1, currentLocation.y + 1);

        //left
        Location left = new Location(currentLocation.x - 1, currentLocation.y);
        Location leftTop = new Location(currentLocation.x - 1, currentLocation.y - 1);
        Location leftBottom = new Location(currentLocation.x - 1, currentLocation.y + 1);

        //up
        Location up = new Location(currentLocation.x, currentLocation.y + 1);

        //down
        Location down = new Location(currentLocation.x, currentLocation.y - 1);


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