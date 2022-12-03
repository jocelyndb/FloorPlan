package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PlanObjectTest extends TestFields {
    PlanObject pO1;
    PlanObject pO2;
    Room r;
    Furniture f;

    EventLog el;

    @BeforeEach
    void runBefore() {
        el = EventLog.getInstance();
        pO1 = new Furniture(1,
                2,
                3,
                4,
                "sofa",
                false,
                "nylon",
                "black");
        pO2 = new Furniture(
                5,
                6,
                7,
                8,
                "lamp",
                true,
                "wicker",
                "brown");
        r = new Room(5,
                5,
                2,
                3,
                "sample",
                true,
                new HashSet<>(),
                "green");
        f = new Furniture(
                5,
                6,
                7,
                8,
                "lamp",
                true,
                "wicker",
                "brown");
        el.clear();
        r.addFurniture(f);
    }

    @Test
    void testPlanObject() {
        checkPlanObjectFields(pO1, 1,2,3,4,"sofa",false);
        checkPlanObjectFields(pO2,5,6,7,8,"lamp",true);
    }

    @Test
    void testPlanEvent() {
        int count = 0;
        for (Event e : el) {
            if (count == 0) {
                assertEquals("Event log cleared.", e.getDescription());
            }
            if (count == 1) {
                assertEquals("lamp added to sample", e.getDescription());
            }
            if (count > 1) {
                fail();
            }
            count++;
        }
    }
}
