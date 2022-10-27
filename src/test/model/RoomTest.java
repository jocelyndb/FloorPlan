package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTest extends TestFields {
    Room r1;
    Room r2;

    Furniture f1;
    Furniture f2;
    HashSet<Furniture> furniture;

    @BeforeEach
    void runBefore() {
        r1 = new Room(5,
                4,
                0,
                0,
                "r1",
                true,
                new HashSet<>(),
                "green");
        r2 = new Room(5,
                4,
                10,
                0,
                "r2",
                false,
                new HashSet<>(),
                "eggshell");

        f1 = new Furniture(3,
                4,
                2,
                1,
                "couch",
                true,
                "leather",
                "black");
        f2 = new Furniture(7,
                8,
                9,
                10,
                "bed",
                false,
                "pine",
                "walnut oil");
        furniture = new HashSet<>();
    }

    @Test
    void testRoomNoConnections() {
        checkRoomFields(r1,
                5,
                4,
                0,
                0,
                "r1",
                true,
                new HashSet<>(),
                "green");

        checkRoomFields(r2,
                5,
                4,
                10,
                0,
                "r2",
                false,
                new HashSet<>(),
                "eggshell");
    }

    @Test
    void testRoomOneNewConnection() {
        HashSet<Room> r3Connections = new HashSet<>();
        r3Connections.add(r1);
        r3Connections.add(r2);

        Room r3 = new Room(5,
                4,
                5,
                0,
                "r2",
                false,
                r3Connections,
                "eggshell");

        HashSet<Room> setWithR1AndR2 = new HashSet<>();
        setWithR1AndR2.add(r1);
        setWithR1AndR2.add(r2);
        HashSet<Room> setWithR3 = new HashSet<>();
        setWithR3.add(r3);

        assertEquals(setWithR1AndR2, r3.getConnections());
        assertEquals(setWithR3, r1.getConnections());
        assertEquals(setWithR3, r2.getConnections());
    }

    @Test
    void testRoomMultipleNewConnections() {

        HashSet<Room> r3Connections = new HashSet<>();
        r3Connections.add(r1);
        r3Connections.add(r2);

        Room r3 = new Room(5,
                4,
                5,
                0,
                "r2",
                false,
                r3Connections,
                "eggshell");


        HashSet<Room> setWithR1AndR2 = new HashSet<>();
        setWithR1AndR2.add(r1);
        setWithR1AndR2.add(r2);
        HashSet<Room> setWithR3 = new HashSet<>();
        setWithR3.add(r3);

        assertEquals(setWithR1AndR2, r3.getConnections());
        assertEquals(setWithR3, r1.getConnections());
        assertEquals(setWithR3, r2.getConnections());
        HashSet<Room> r4Connections = new HashSet<>();
        r4Connections.add(r1);
        r4Connections.add(r2);
        r4Connections.add(r3);

        Room r4 = new Room(12,
                7,
                0,
                5,
                "r2",
                false,
                r4Connections,
                "eggshell");

        setWithR1AndR2.add(r4);
        setWithR3.add(r4);

        assertEquals(r4Connections, r4.getConnections());
        assertEquals(setWithR1AndR2, r3.getConnections());
        assertEquals(setWithR3, r1.getConnections());
        assertEquals(setWithR3, r2.getConnections());
    }

    @Test
    void testAddFurniture() {
        assertEquals(new HashSet<>(), r1.getFurniture());

        furniture.add(f1);
        r1.addFurniture(f1);

        assertEquals(furniture, r1.getFurniture());

        furniture.add(f2);
        r1.addFurniture(f2);

        assertEquals(furniture, r1.getFurniture());
    }
}
