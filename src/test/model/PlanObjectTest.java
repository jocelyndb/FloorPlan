package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanObjectTest {
    PlanObject pO1;
    PlanObject pO2;

    @BeforeEach
    void runBefore() {
        pO1 = new PlanObject(1,2,3,4,"Big Plan",false);
        pO2 = new PlanObject(5,6,7,8,"Small Plan",true);
    }

    @Test
    void testPlanObject() {
        checkPlanObjectFields(pO1, 1,2,3,4,"Big Plan",false);
        checkPlanObjectFields(pO2,5,6,7,8,"Small Plan",true);
    }

    private void checkPlanObjectFields(PlanObject pO, int w, int h, int x, int y, String label, boolean showLabel) {
        assertEquals(w, pO.getWidth());
        assertEquals(h, pO.getHeight());
        assertEquals(x, pO.getCoordinateX());
        assertEquals(y, pO.getCoordinateY());
        assertEquals(label, pO.getLabel());
        assertEquals(showLabel,pO.isShowLabel());
    }
}
