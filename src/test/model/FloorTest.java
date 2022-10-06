package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

public class FloorTest {
    Floor f1;
    Floor f2;
    HashSet<Room> rooms;
    Room r1 = new Room(5,
                    4,
                    1,
                    1,
                    "r1",
                    true,
                    new HashSet<>(),
                    "green");
    Room r2 = new Room(4,
            5,
            0,
            0,
            "r2",
            false,
            new HashSet<>(),
            "eggshell");


    @BeforeEach
    void runBefore() {
        rooms = new HashSet<>();
        f1 = new Floor(9,
                10,
                11,
                12,
                "f1",
                true,
                3);
        f2 = new Floor(7,
                6,
                5,
                3,
                "f2",
                false,
                1);
    }

    @Test
    void testFloor() {
        checkFloorFields(f1, 9,10,11,12,"f1",true,3);
        checkFloorFields(f2, 7,6,5,3,"f2",false,1);
    }

    private void checkFloorFields(Floor f, int w, int h, int x, int y, String label, boolean showLabel, int n) {
        assertEquals(w, f.getWidth());
        assertEquals(h, f.getHeight());
        assertEquals(x, f.getCoordinateX());
        assertEquals(y, f.getCoordinateY());
        assertEquals(label, f.getLabel());
        assertEquals(showLabel,f.isShowLabel());
        assertEquals(n, f.getNumber());
    }

    @Test
    void testAddRoom_Room() {
        assertEquals(new HashSet<>(), f1.getRooms());

        f1.addRoom(r1);
        rooms.add(r1);
        assertEquals(rooms, f1.getRooms());

        f1.addRoom(r2);
        rooms.add(r2);
        assertEquals(rooms, f1.getRooms());
    }

    @Test
    void testAddRoom_int_int_int_int_String_boolean_HashSetOfRoom_String() {
        assertEquals(new HashSet<>(), f1.getRooms());

        Room r3 = f1.addRoom(5,
                4,
                1,
                1,
                "r1",
                true,
                new HashSet<>(),
                "green");
        rooms.add(r3);
        assertEquals(rooms, f1.getRooms());

        Room r4 = f1.addRoom(4,
                5,
                0,
                0,
                "r2",
                false,
                new HashSet<>(),
                "eggshell");
        rooms.add(r4);
        assertEquals(rooms, f1.getRooms());
    }
}
