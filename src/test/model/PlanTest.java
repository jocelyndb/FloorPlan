package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class PlanTest {
    Plan p1;
    Plan p2;
    Floor f1;
    Floor f2;
    private HashSet<Floor> floors;

    @BeforeEach
    void runBefore() {
        p1 = new Plan("Cool Plan", 50,60,1);
        p2 = new Plan("Lame Plan", 70,80,0);
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
        floors = new HashSet<>();
    }

    @Test
    void testPlan() {
        checkPlanFields(p1,"Cool Plan", 50,60,1, new HashSet<>());
        checkPlanFields(p2,"Lame Plan", 70,80,0, new HashSet<>());
    }

    @Test
    void testAddFloor() {
        assertEquals(new HashSet<>(), p1.getFloors());
        Floor f3 = p1.addFloor(3,4,0,1,"f1",false,1);
        floors.add(f3);
        assertEquals(floors, p1.getFloors());

        Floor f4 = p1.addFloor(5,6,3,7,"f2",true,2);
        floors.add(f4);
        assertEquals(floors, p1.getFloors());
    }

    @Test
    void testGetFloor() {
        Floor f3 = p1.addFloor(3,4,0,1,"f1",false,1);
        Floor f4 = p1.addFloor(5,6,3,7,"f2",true,2);
        floors.add(f3);
        floors.add(f4);

        assertEquals(floors, p1.getFloors());

        assertEquals(f3, p1.getFloor(1));
        assertEquals(f4, p1.getFloor(2));

        assertEquals(floors, p1.getFloors());
    }

    // Condition only necessary for the compiler as error handling has not yet been implemented
    @Test
    void testGetFloorNoFloor() {
        assertNull(p1.getFloor(5));
    }

    private void checkPlanFields(Plan p, String name, int w, int h, int fM, HashSet<Floor> floors) {
        assertEquals(name,p.getName());
        assertEquals(w,p.getLotWidth());
        assertEquals(h,p.getLotHeight());
        assertEquals(fM,p.getFurnitureMargins());
        assertEquals(floors,p.getFloors());
    }
}