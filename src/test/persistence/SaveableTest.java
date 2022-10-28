package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveableTest extends TestFields {
    final String DIRECTORY = "./data/test/";
    PlanReader reader;
    Plan simplePlan;
    Plan emptyPlan;
    Floor f1;
    Floor f2;
    Room r1;
    Room r2;
    Room r3;
    Furniture furniture1;

    @BeforeEach
    void runBefore() {
        reader = new PlanReader();

        simplePlan = new Plan("Simple Plan",60,40,2);
        emptyPlan = new Plan("Empty Plan",50,50,5);

        f1 = simplePlan.addFloor(60,40,0,0,"1",true,1);
        f2 = simplePlan.addFloor(40,40,0,0,"2",true,2);

        r1 = new Room(20,
                        20,
                        5,
                        5,
                        "r1",
                        true,
                         new HashSet<>(),
                        "blue");
        HashSet<Room> setWithR1 = new HashSet<>();
        setWithR1.add(r1);
        r2 = new Room(15,
                        20,
                        20,
                        20,
                        "r2",
                        true,
                        setWithR1,
                        "brown");
        r3 = new Room(15,
                        20,
                        20,
                        20,
                        "r3",
                        true,
                        new HashSet<>(),
                        "green");
        f1.addRoom(r1);
        f1.addRoom(r2);
        f1.addRoom(r3);


        furniture1 = new Furniture(3,
                                    4,
                                    0,
                                    0,
                                    "f1",
                                    true,
                                    "leather",
                                    "black");
        r1.addFurniture(furniture1);
    }

    @Test
    void testSaveOverInvalidFile() {
        try {
            simplePlan.saveOver(DIRECTORY + "an\0illegal:fileName.json");
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testSaveOverEmptyPlan() {
        try {
            emptyPlan.saveOver(DIRECTORY + "Empty Plan.json");
            Plan p = reader.read(DIRECTORY + "Empty Plan.json");
            checkPlanFields(p, "Empty Plan",50,50, 5, new HashSet<>());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testSaveOverSimplePlan() {
        try {
            simplePlan.saveOver(DIRECTORY + "Simple Plan.json");
            // Checking the plan
            Plan p = reader.read(DIRECTORY + "Simple Plan.json");
            HashSet<Floor> floors = p.getFloors();
            checkPlanFieldsNoCollections(p, "Simple Plan",60,40, 2);

            // Checking the floors
            assertEquals(2,floors.size());
            Floor f1 = p.getFloor(1);
            Floor f2 = p.getFloor(2);
            checkFloorFields(f1,60,40,0,0,"1",true,1);
            checkFloorFields(f2,40,40,0,0,"2",true,2);

            // Checking the rooms
            HashSet<Room> f1Rooms = f1.getRooms();
            HashSet<Room> f2Rooms = f2.getRooms();

            assertEquals(3,f1Rooms.size());
            assertEquals(0,f2Rooms.size());
            int copiesOfR1InF1 = 0;
            int copiesOfR2InF1 = 0;
            int copiesOfR3InF1 = 0;
            Room r1 = null;
            Room r2 = null;
            Room r3 = null;
            for (Room room : f1Rooms) {
                if (matchingRoomFieldsNoCollections(room,20,20,5,5,"r1",true,"blue")) {
                    r1 = room;
                    copiesOfR1InF1++;
                } else if (matchingRoomFieldsNoCollections(room,
                        15,
                        20,
                        20,
                        20,
                        "r2",
                        true,
                        "brown")) {
                    r2 = room;
                    copiesOfR2InF1++;
                } else if (matchingRoomFieldsNoCollections(room,
                        15,
                        20,
                        20,
                        20,
                        "r3",
                        true,
                        "green")) {
                    r3 = room;
                    copiesOfR3InF1++;
                }
            }
            assertEquals(1,copiesOfR1InF1);
            assertEquals(1,copiesOfR2InF1);
            assertEquals(1,copiesOfR3InF1);
            assert r1 != null;
            assert r2 != null;
            assert r3 != null;
            assertTrue(r1.getConnections().contains(r2));
            assertTrue(r2.getConnections().contains(r1));
            assertEquals(1,r1.getConnections().size());
            assertEquals(1,r2.getConnections().size());
            assertTrue(r3.getConnections().isEmpty());

            // Checking the furniture
            HashSet<Furniture> r1Furniture = r1.getFurniture();
            HashSet<Furniture> r2Furniture = r2.getFurniture();
            assertEquals(1,r1Furniture.size());
            assertEquals(0,r2Furniture.size());
            int copiesOfFurniture1InR1 = 0;
//            Furniture furniture1 = null;
            for (Furniture f : r1Furniture) {
                if (matchingFurnitureFieldsNoCollections(f,
                        3,
                        4,
                        0,
                        0,
                        "f1",
                        true,
                        "leather",
                        "black")) {
//                    furniture1 = f;
                    copiesOfFurniture1InR1++;
                }
            }
            assertEquals(1,copiesOfFurniture1InR1);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testSaveNewNoExistingFile() {
        String path = DIRECTORY + "Empty Plan.json";
        File emptyPlanFile = new File(path);
        assertTrue(emptyPlanFile.delete());
        try {
            emptyPlan.saveNew(path);
            Plan p = reader.read(path);
            checkPlanFields(p, "Empty Plan",50,50, 5, new HashSet<>());
        } catch (FileAlreadyExistsException e) {
            fail("File was not deleted prior to test");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testSaveNewInvalidFile() {
        try {
            simplePlan.saveNew(DIRECTORY + "an\0illegal:fileName.json");
            fail("FileNotFoundException was expected");
        } catch (FileAlreadyExistsException e) {
            fail("File already exists – test filename is valid");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testSaveNewFileAlreadyExists() {
        try {
            simplePlan.saveNew(DIRECTORY + "Simple Plan.json");
            fail("FileAlreadyExistsException not thrown");
        } catch (FileAlreadyExistsException e) {
            // pass
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
