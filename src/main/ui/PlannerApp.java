package ui;

import model.*;
import persistence.PlanReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

// Architectural planner application
public class PlannerApp {
    private static final String JSON_DIRECTORY = "./data/";
    private List<Plan> plans;
    private Scanner input;
    private PlanReader planReader;

    // EFFECTS: runs the planner application
    public PlannerApp() {
        planReader = new PlanReader();
        runPlanner();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPlanner() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase(Locale.ROOT);

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processMainMenuCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command on main menu
    private void processMainMenuCommand(String command) {
        if (hasInt(command)) {
            int planNumber = Integer.parseInt(command);
            if ((1 <= planNumber) && (planNumber <= plans.size())) {
                runPlan(plans.get(planNumber - 1));
            } else {
                System.out.println("Plan [" + planNumber + "] not found...");
            }
        } else if (command.equals("n")) {
            makePlan();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: facilitates creation of a new plan
    private void makePlan() {
        System.out.println("\nNew Plan:");
        String name = getStringFromUser("\tName: ");
        int width = getIntFromUser("\tLot Width: ");
        int height = getIntFromUser("\tLot Height: ");
        int fm = getIntFromUser("\tFurniture Margins: ");

        Plan p = new Plan(name, width, height, fm);
        plans.add(p);
        runPlan(p);
    }

    // MODIFIES: plan
    // EFFECTS: allows the user to edit a plan
    private void runPlan(Plan plan) {
        boolean keepGoing = true;
        String command;

        HashSet<Floor> floors = plan.getFloors();

        while (keepGoing) {
            System.out.println("\n\nPlan: " + plan.getName());
            displayPlan(floors);
            displayPlanMenu(floors);
            command = input.next();
            command = command.toLowerCase(Locale.ROOT);

            if (command.equals("s")) {
                handleSave(plan);
                keepGoing = false;
            } else if (command.equals("q")) {
                keepGoing = false;
            } else {
                processPlanMenuCommand(floors, command);
            }
        }
    }

    // EFFECTS: allows the user to save a plan
    private void handleSave(Plan plan) {
        String path = JSON_DIRECTORY + plan.getName() + ".json";
        try {
            plan.saveNew(path);
        } catch (FileAlreadyExistsException e) {
            boolean doSaveOver = getYNFromUser("Would you like to replace the existing "
                                                + plan.getName()
                                                + " plan? [y/n]");
            if (doSaveOver) {
                try {
                    plan.saveOver(path);
                } catch (FileNotFoundException ex) {
                    System.out.println("There was a problem saving your plan");
                }
            }
        } catch (IOException e) {
            System.out.println("There was a problem saving your plan");
        }
    }

    // EFFECTS: displays the floors, the rooms they contain, and the furniture those rooms contain in a plan
    private void displayPlan(HashSet<Floor> floors) {
        for (Floor floor : floors) {
            if (floor.isShowLabel()) {
                System.out.println("\tFloor " + floor.getNumber() + " (" + floor.getLabel() + "): ");
            } else {
                System.out.println("\tFloor " + floor.getNumber() + ": ");
            }
            displayFloor(floor);
        }
    }

    // EFFECTS: displays the rooms and furniture in a floor
    private void displayFloor(Floor floor) {
        for (Room room : floor.getRooms()) {
            System.out.print("\t\t" + room.getLabel() + " (" + room.getColour() + "): ");
            displayRoom(room);
        }
    }

    // EFFECTS: displays the furniture in a room
    private void displayRoom(Room room) {
        for (Furniture furniture : room.getFurniture()) {
            System.out.print(furniture.getLabel()
                    + " (" + furniture.getMaterial()
                    + ", " + furniture.getColour()
                    + "), ");
        }
        System.out.println("\b\b");
    }

    // EFFECTS: displays a menu of options to the user for creating and removing floors
    private void displayPlanMenu(HashSet<Floor> floors) {
        int selector = 1;
        System.out.println("\n Select from:");

        for (Floor floor : floors) {
            System.out.println("\t[" + selector + "]: " + floor.getLabel());
            selector++;
        }

        System.out.println("\n\t[n]: New Floor");
        System.out.println("\n\t[s]: Save and Exit to Main Menu");
        System.out.println("\t[q]: Exit to Main Menu without saving. WARNING: You will lose all unsaved progress");
    }

    // REQUIRES: Hashset order has not been changed between the set of rooms being printed and command inputted
    // MODIFIES: floors
    // EFFECTS: processes user command on plan menu,
    //              if user selects a floor, that floor is opened
    //              if user selects to create a new floor, that floor is made
    //              if selection is not valid, they are notified
    private void processPlanMenuCommand(HashSet<Floor> floors, String command) {
        if (hasInt(command)) {
            int floorNumber = Integer.parseInt(command);
            if ((1 <= floorNumber) && (floorNumber <= floors.size())) {
                int i = 1;
                for (Floor floor : floors) {
                    if (i == floorNumber) {
                        runFloor(floor);
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                System.out.println("Floor [" + floorNumber + "] not found...");
            }
        } else if (command.equals("n")) {
            makeFloor(floors);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: floors
    // EFFECTS: facilitates the creation of a new floor
    private void makeFloor(HashSet<Floor> floors) {
        System.out.println("\nNew Floor:");
        String label = getStringFromUser("\tLabel: ");
        int number = getIntFromUser("\tNumber: ");
        int width = getIntFromUser("\tWidth: ");
        int height = getIntFromUser("\tHeight: ");
        int coordinateX = getIntFromUser("\tLot Coordinate X: ");
        int coordinateY = getIntFromUser("\tLot Coordinate Y: ");

        Floor f = new Floor(width, height, coordinateX, coordinateY, label, true, number);
        floors.add(f);
        runFloor(f);
    }

    // MODIFIES: floor
    // EFFECTS: allows the user to edit a floor
    private void runFloor(Floor floor) {
        boolean keepGoing = true;
        String command;

        HashSet<Room> rooms = floor.getRooms();

        while (keepGoing) {
            System.out.println("\n\nFloor: " + floor.getLabel() + ", modify room at [#] or create new room");
            displayFloor(floor);
            displayFloorMenu(rooms);
            command = input.next();
            command = command.toLowerCase(Locale.ROOT);

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processFloorMenuCommand(rooms, command);
            }
        }
    }

    // EFFECTS: displays a menu of options to the user for creating and modifying rooms on a floor
    private void displayFloorMenu(HashSet<Room> rooms) {
        System.out.println("\n Select from:");
        displayRoomsWithSelector(rooms);
        System.out.println("\n\t[n]: New Room");
        System.out.println("\n\t[q]: Return to Plan Menu");
    }

    // EFFECTS: Displays a list of rooms with numbers for selection
    private void displayRoomsWithSelector(HashSet<Room> rooms) {
        int selector = 1;
        for (Room room : rooms) {
            System.out.println("\t[" + selector + "]: " + room.getLabel());
            selector++;
        }
    }

    // REQUIRES: Hashset order has not been changed between the set of rooms being printed and command inputted
    // MODIFIES: rooms
    // EFFECTS: processes user command on floor menu,
    //              if user selects a room, that room is opened
    //              if user selects to create a new room, that room is made
    //              if selection is not valid, they are notified
    private void processFloorMenuCommand(HashSet<Room> rooms, String command) {
        if (hasInt(command)) {
            int roomNumber = Integer.parseInt(command);
            if ((1 <= roomNumber) && (roomNumber <= rooms.size())) {
                int i = 1;
                for (Room room : rooms) {
                    if (i == roomNumber) {
                        runRoom(room);
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                System.out.println("Room [" + roomNumber + "] not found...");
            }
        } else if (command.equals("n")) {
            makeRoom(rooms);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: rooms
    // EFFECTS: facilitates the creation of a new room
    private void makeRoom(HashSet<Room> rooms) {
        System.out.println("\nNew Room:");
        String label = getStringFromUser("\tLabel: ");
        String colour = getStringFromUser("\tPaint Colour: ");
        int width = getIntFromUser("\tWidth: ");
        int height = getIntFromUser("\tHeight: ");
        int coordinateX = getIntFromUser("\tLot Coordinate X: ");
        int coordinateY = getIntFromUser("\tLot Coordinate Y: ");

        HashSet<Room> connections = getRoomConnections(rooms);

        Room r = new Room(width, height, coordinateX, coordinateY, label, true, connections, colour);
        rooms.add(r);
        runRoom(r);
    }

    // EFFECTS: returns a prompted integer from the user.
    //          Continues to prompt the user for an integer until one is provided
    private int getIntFromUser(String s) {
        String received;

        while (true) {
            System.out.print(s);
            received = input.next();
            if (hasInt(received)) {
                return Integer.parseInt(received);
            }
            System.out.println("This parameter requires an integer value!");
        }
    }

    // EFFECTS: returns a prompted "y" or "n" from user as a boolean, where y: true, n: false
    //          Continues to prompt the user for a "y" or an "n" until one is provided
    private Boolean getYNFromUser(String s) {
        String received;

        while (true) {
            System.out.print(s);
            received = input.next();
            if (received.equalsIgnoreCase("y")) {
                return true;
            } else if (received.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Please provide a [y/n] response!");
        }
    }

    // EFFECTS: returns a string from the user with a specific prompt.
    private String getStringFromUser(String s) {
        System.out.print(s);
        return input.next();
    }

    // EFFECTS: gets new room's connections from user
    private HashSet<Room> getRoomConnections(HashSet<Room> rooms) {
        boolean keepGoing = true;
        String command;
        HashSet<Room> connections = new HashSet<>();

        while (keepGoing) {
            System.out.println("Select all rooms that to be connected to the room being created:");
            displayRoomsWithSelector(rooms);
            System.out.println("\n\t[d]: Done adding connections");
            command = input.next();
            command = command.toLowerCase(Locale.ROOT);
            if (command.equals("d")) {
                keepGoing = false;
            } else {
                processRoomConnections(rooms, connections, command);
            }
        }
        return connections;
    }

    // MODIFIES: connections
    // EFFECTS: processes user input when selecting connected rooms
    private void processRoomConnections(HashSet<Room> rooms, HashSet<Room> connections, String command) {
        if (hasInt(command)) {
            int roomNumber = Integer.parseInt(command);
            if ((1 <= roomNumber) && (roomNumber <= rooms.size())) {
                int i = 1;
                for (Room room : rooms) {
                    if (i == roomNumber) {
                        connections.add(room);
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                System.out.println("Room [" + roomNumber + "] not found...");
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: room
    // EFFECTS: allows the user to edit a room
    private void runRoom(Room room) {
        boolean keepGoing = true;
        String command;

        HashSet<Furniture> furniture = room.getFurniture();

        while (keepGoing) {
            System.out.println("\n\nRoom: " + room.getLabel() + ", remove furniture at [#] or create new furniture");
            displayRoom(room);
            displayRoomMenu(furniture);
            command = input.next();
            command = command.toLowerCase(Locale.ROOT);

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processRoomMenuCommand(furniture, command);
            }
        }
    }


    // REQUIRES: Hashset order has not been changed between the set of rooms being printed and command inputted
    // MODIFIES: furniture
    // EFFECTS: processes user command on room menu,
    //              if user selects a piece of furniture, that furniture is removed
    //              if user selects to create a new piece of furniture, that furniture is made
    //              if selection is not valid, they are notified
    private void processRoomMenuCommand(HashSet<Furniture> furniture, String command) {
        if (hasInt(command)) {
            int furnitureNumber = Integer.parseInt(command);
            if ((1 <= furnitureNumber) && (furnitureNumber <= furniture.size())) {
                int i = 1;
                for (Furniture f : furniture) {
                    if (i == furnitureNumber) {
                        furniture.remove(f);
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                System.out.println("Furniture [" + furnitureNumber + "] not found...");
            }
        } else if (command.equals("n")) {
            makeFurniture(furniture);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: furniture
    // EFFECTS: facilitates the creation of a new piece of furniture
    private void makeFurniture(HashSet<Furniture> furniture) {
        System.out.println("\nNew Furniture:");
        String label = getStringFromUser("\tLabel: ");
        String material = getStringFromUser("\tMaterial: ");
        String colour = getStringFromUser("\tColour: ");
        int width = getIntFromUser("\tWidth: ");
        int height = getIntFromUser("\tHeight: ");
        int coordinateX = getIntFromUser("\tLot Coordinate X: ");
        int coordinateY = getIntFromUser("\tLot Coordinate Y: ");

        Furniture f = new Furniture(width, height, coordinateX, coordinateY, label, true, material, colour);
        furniture.add(f);

    }

    // EFFECTS: displays a menu of options to the user for creating and removing furniture from a room
    private void displayRoomMenu(HashSet<Furniture> furniture) {
        System.out.println("\n Select from:");
        displayFurnitureWithSelector(furniture);
        System.out.println("\n\t[n]: New Furniture");
        System.out.println("\n\t[q]: Return to Floor Menu");
    }

    // EFFECTS: Displays a list of furniture with numbers for selection
    private void displayFurnitureWithSelector(HashSet<Furniture> furniture) {
        int selector = 1;
        for (Furniture f : furniture) {
            System.out.println("\t[" + selector + "]: " + f.getLabel());
            selector++;
        }
    }

    // EFFECTS: displays a menu of options to the user for creating new and opening existing plans
    private void displayMainMenu() {
        try {
            plans = loadPlanFiles();
        } catch (IOException e) {
            System.out.println("Unable to load files in " + JSON_DIRECTORY);
        }
        int selector = 1;
        System.out.println("\n Select from:");
        if (!plans.isEmpty()) {
            for (Plan p : plans) {
                System.out.println("\t[" + selector + "]: " + p.getName());
                selector++;
            }
        } else {
            System.out.println("\tNo Existing Plans");
        }
        System.out.println("\n\t[n]: New Plan");
        System.out.println("\n\t[q]: Quit");
    }

    // MODIFIES: this
    // EFFECTS: adds all valid plans in the data folder to plans
    private List<Plan> loadPlanFiles() throws IOException {
        List<Plan> plans = new ArrayList<>();
        File dataDirectory = new File(JSON_DIRECTORY);
        File[] filesInDirectory = dataDirectory.listFiles();
        for (File file : filesInDirectory) {
            String fileName = file.getPath();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (fileExtension.equals("json")) {
                Plan p = planReader.read(fileName);
                if (!(null == p)) {
                    plans.add(p);
                }
            }
        }
        return plans;
    }
    /*
    public class ListOfFiles {
   public static void main(String args[]) throws IOException {
      //Creating a File object for directory
      File directoryPath = new File("D:\ExampleDirectory");
      //List of all files and directories
      File filesList[] = directoryPath.listFiles();
      System.out.println("List of files and directories in the specified directory:");
      for(File file : filesList) {
         System.out.println("File name: "+file.getName());
         System.out.println("File path: "+file.getAbsolutePath());
         System.out.println("Size :"+file.getTotalSpace());
         System.out.println(" ");
      }
   }
}
     */

    // MODIFIES: this
    // EFFECTS: initializes plans
    private void init() {
        plans = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");

//        initPlan();


    }

//    // MODIFIES: this
//    // EFFECTS: makes a preexisting plan
//    private void initPlan() {
//        Plan p1 = new Plan("Musqueam Project",100,100,2);
//        plans.add(p1);
//
//        initFloors(p1);
//    }
//
//    // MODIFIES: p1
//    // EFFECTS: makes a preexisting plan
//    private void initFloors(Plan p1) {
//        Floor f1 = p1.addFloor(50,60,10,10,"Ground Floor",true,1);
//        Floor f2 = p1.addFloor(50,30,10,10,"Mezzanine",true,2);
//        Floor f3 = p1.addFloor(10,20,20,10,"Attic",true,3);
//
//        initRooms(f1, f2, f3);
//    }
//
//    // MODIFIES: f1, f2, f3
//    // EFFECTS: Adds existing rooms to three floors
//    private void initRooms(Floor f1, Floor f2, Floor f3) {
//        Room greatRoom = new Room(10,10,0,0,"Great Room", true, new HashSet<>(),"eggshell");
//        Room tinyRoom = new Room(5,5,0,0,"Tiny Room", true, new HashSet<>(),"off-white");
//        Room drawingRoom = new Room(20,10,0,0,"Drawing Room", true,new HashSet<>(),"ivory");
//        Room bedroom = new Room(10,10,0,0,"Bedroom", true,new HashSet<>(),"lightest grey");
//        Room storage = new Room(10,10,0,0,"Storage", true,new HashSet<>(),"Ash");
//
//        greatRoom.addConnection(tinyRoom);
//        tinyRoom.addConnection(greatRoom);
//
//        f1.addRoom(greatRoom);
//        f1.addRoom(tinyRoom);
//        f2.addRoom(drawingRoom);
//        f2.addRoom(bedroom);
//        f3.addRoom(storage);
//
//        initFurniture(greatRoom, drawingRoom, bedroom);
//    }
//
//    // MODIFIES: r1, r2, r3
//    // EFFECTS: adds furniture to the rooms
//    private void initFurniture(Room r1, Room r2, Room r3) {
//        Furniture couch = new Furniture(3,6,0,0,"couch",true,"leather","brown");
//        Furniture lamp = new Furniture(1,1,0,0,"lamp",true,"metal","white");
//        Furniture bedsideTable = new Furniture(3,1,0,0,"bedside table",true,"wood","pine");
//        Furniture settee = new Furniture(3,8,0,0,"settee",true,"corduroy","mauve");
//
//        r1.addFurniture(settee);
//        r1.addFurniture(lamp);
//        r3.addFurniture(bedsideTable);
//        r3.addFurniture(lamp);
//        r2.addFurniture(couch);
//    }

    // EFFECTS: returns true if a string only contains an integer
    private static boolean hasInt(String s) {
        if (s == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}