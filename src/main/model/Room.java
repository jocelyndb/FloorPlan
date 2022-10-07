package model;

import java.util.HashSet;

// A room with a label, given size and location relative to a floor connected to other rooms and containing furniture
public class Room extends PlanObject {
    HashSet<Room> connections;
    HashSet<Furniture> furniture;
    String colour;

    // REQUIRES: width > 0, height > 0, connections must be physically possible and not repeated
    // MODIFIES: each connected room
    // EFFECTS: creates a room of given width, height and label with a paint colour and connections to other rooms
    //          The room is added to the connections of rooms it is connected to. The room has no furniture
    public Room(int width,
                int height,
                int coordinateX,
                int coordinateY,
                String label,
                boolean showLabel,
                HashSet<Room> connections,
                String colour) {
        super(width, height, coordinateX, coordinateY, label, showLabel);
        this.connections = connections;
        for (Room r : connections) {
            r.addConnection(this);
        }
        furniture = new HashSet<>();
        this.colour = colour;
    }

    // REQUIRES: connection is physically possible and not already in connections
    // MODIFIES: this
    // EFFECTS:  adds a room to connections
    private void addConnection(Room r) {
        connections.add(r);
    }

    // REQUIRES: furniture is not within margin of other furniture
    // MODIFIES: this
    // EFFECTS: adds a piece of furniture to the room (this.furniture)
    public void addFurniture(Furniture f) {
        furniture.add(f);
    }

    public HashSet<Furniture> getFurniture() {
        return furniture;
    }

    public HashSet<Room> getConnections() {
        return connections;
    }

    public String getColour() {
        return colour;
    }

//    public void paint(String colour) {
//        this.colour = colour;
//    }
}
