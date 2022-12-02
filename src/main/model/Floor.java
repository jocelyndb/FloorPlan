package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.util.HashSet;

// A floor with a label, given size and location on the lot, and number which contains rooms
public class Floor extends PlanObject {
    private int number;
    private HashSet<Room> rooms;

    // REQUIRES: width > 0, height > 0, number is not already taken by another floor
    // EFFECTS: creates a floor with a width, height, label, and number with no rooms
    public Floor(int width, int height, int coordinateX, int coordinateY, String label, boolean showLabel, int number) {
        super(width, height, coordinateX, coordinateY, label, showLabel);
        this.number = number;
        this.rooms = new HashSet<>();
        super.planEvent("Floor (" + number + "): " + label, "the plan");
    }

    // REQUIRES: Room is not already in floor
    // MODIFIES: this
    // EFFECTS: adds a Room to this floor (this.rooms)
    public void addRoom(Room r) {
        rooms.add(r);
    }

    // REQUIRES: width > 0, height > 0, connections must be physically possible and not contain duplicates
    // MODIFIES: this
    // EFFECTS: creates a Room and adds it to this (this.rooms) floor and returns the created room
    public Room addRoom(int width,
                        int height,
                        int coordinateX,
                        int coordinateY,
                        String label,
                        boolean showLabel,
                        HashSet<Room> connections,
                        String colour) {
        Room r = new Room(width, height, coordinateX, coordinateY, label, showLabel, connections, colour);
        rooms.add(r);
        planEvent(label, this.getLabel());
        return r;
    }

    public int getNumber() {
        return number;
    }

    public HashSet<Room> getRooms() {
        return rooms;
    }

    // EFFECTS: converts the floor to json
    @Override
    public JSONObject toJson() {
        // Make a JSONArray of the rooms in the floor
        JSONArray jsonRoomArray = new JSONArray();
        for (Room room : rooms) {
            jsonRoomArray.put(room.toJson());
        }
        JSONObject json = super.toJson();
        json.put("number", number);
        json.put("rooms", jsonRoomArray);
        return json;
    }
}
