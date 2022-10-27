package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FurnitureTest extends TestFields {
    Furniture f1;
    Furniture f2;

    @BeforeEach
    void runBefore() {
        f1 = new Furniture(3,
                4,
                5,
                6,
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
    }

    @Test
    void testFurniture() {
        checkFurnitureFields(f1,3,4,5,6,"couch",true,"leather","black");
        checkFurnitureFields(f2,7,8,9,10,"bed",false,"pine","walnut oil");
    }
}
