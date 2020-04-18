package se.ld46.game.pathfinding;

import java.util.Objects;

public class Location {
    public final int x;
    public final int y;
    public final Location succesor;

    public Location(int x, int y, Location succesor) {
        this.x = x;
        this.y = y;
        this.succesor = succesor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", succesor=" + succesor +
                '}';
    }
}
