package se.ld46.game.pathfinding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest {
    @Test
    void make_sure_that_compare_works() {
        Node big = new Node(null, 5, null);
        Node small = new Node(null, 2, null);
        assertEquals(1, big.compareTo(small));
        assertEquals(-1, small.compareTo(big));
        assertEquals(0, small.compareTo(small));
    }
}