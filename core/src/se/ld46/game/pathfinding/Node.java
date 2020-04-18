package se.ld46.game.pathfinding;

import java.util.Objects;

public class Node implements Comparable<Node> {
    public Location location;
    public int travelCost;
    public Node successor;

    public Node(Location location, int travelCost, Node successor) {
        this.location = location;
        this.travelCost = travelCost;
        this.successor = successor;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.travelCost, o.travelCost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return travelCost == node.travelCost &&
                Objects.equals(location, node.location) &&
                Objects.equals(successor, node.successor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, travelCost, successor);
    }
}
