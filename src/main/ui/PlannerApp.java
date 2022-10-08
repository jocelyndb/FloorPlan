package ui;

import model.*;

import java.util.*;

// Architectural planner application
public class PlannerApp {
    private List<Plan> plans;
    private Scanner input;

    // EFFECTS: runs the planner application
    public PlannerApp() {
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

    // MODIFIES: this
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

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processPlanMenuCommand(floors, command);
            }
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
        System.out.println("\n\t[q]: Exit to Main Menu");
    }

    // REQUIRES: Hashset order has not been changed between the set of rooms being printed and command inputted
    // MODIFIES: this
    // EFFECTS: processes user command on plan menu
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

    // MODIFIES: this
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

    // MODIFIES: this
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

    // MODIFIES: this
    // EFFECTS: processes user command on floor menu
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

    // MODIFIES: this
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
//        System.out.print(s);
        String received;

        while (true) {
            System.out.print(s);
            received = input.next();
            if (hasInt(received)) {
                return Integer.parseInt(received);
            }
        }
    }

    // EFFECTS: returns an integer from the user with a specific prompt.
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

    // MODIFIES: this
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

    // EFFECTS: processes user command on room menu
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

    // MODIFIES: this
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
    // EFFECTS: initializes plans
    @SuppressWarnings("methodlength")
    private void init() {
        plans = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        Plan p1 = new Plan("Musqueam Project",100,100,2);
        plans.add(p1);

        Floor f1 = p1.addFloor(50,60,10,10,"Ground Floor",true,1);
        Floor f2 = p1.addFloor(50,30,10,10,"Mezzanine",true,2);
        Floor f3 = p1.addFloor(10,20,20,10,"Attic",true,3);

        Room greatRoom = new Room(10,10,0,0,"Great Room", true, new HashSet<>(),"eggshell");
        Room tinyRoom = new Room(5,5,0,0,"Tiny Room", true, new HashSet<>(),"off-white");
        Room drawingRoom = new Room(20,10,0,0,"Drawing Room", true,new HashSet<>(),"ivory");
        Room bedroom = new Room(10,10,0,0,"Bedroom", true,new HashSet<>(),"lightest grey");
        Room storage = new Room(10,10,0,0,"Storage", true,new HashSet<>(),"Ash");

        f1.addRoom(greatRoom);
        f1.addRoom(tinyRoom);
        f2.addRoom(drawingRoom);
        f2.addRoom(bedroom);
        f3.addRoom(storage);

        Furniture couch = new Furniture(3,6,0,0,"couch",true,"leather","brown");
        Furniture lamp = new Furniture(1,1,0,0,"lamp",true,"metal","white");
        Furniture bedsideTable = new Furniture(3,1,0,0,"bedside table",true,"wood","pine");
        Furniture settee = new Furniture(3,8,0,0,"settee",true,"corduroy","mauve");

        greatRoom.addFurniture(settee);
        greatRoom.addFurniture(lamp);
        bedroom.addFurniture(bedsideTable);
        bedroom.addFurniture(lamp);
        drawingRoom.addFurniture(couch);
    }

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