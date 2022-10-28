package persistence;

import model.Floor;
import model.Furniture;
import model.Plan;
import model.Room;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.stream.Stream;

// Represents a reader that converts JSON files containing saved plans into Plan objects
public class PlanReader {

    // EFFECTS: reads and returns plan from file
    //          throws IOException if an error occurs reading the file
    public Plan read(String path) throws IOException {
        String jsonData = readFile(path);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlan(jsonObject);
    }

    // EFFECTS: reads and returns source file as string
    //          throws IOException if an error occurs reading data from file
    private String readFile(String path) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses and returns plan from JSON object
    private Plan parsePlan(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int lotWidth = jsonObject.getInt("width");
        int lotHeight = jsonObject.getInt("height");
        int furnitureMargins = jsonObject.getInt("furniture margins");
        Plan p = new Plan(name, lotWidth, lotHeight, furnitureMargins);
        addFloors(p, jsonObject);
        return p;
    }

    // MODIFIES: p
    // EFFECTS: parses floors from JSON object and adds them to the plan
    private void addFloors(Plan p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("floors");
        for (Object json : jsonArray) {
            JSONObject nextFloor = (JSONObject) json;
            addFloor(p, nextFloor);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses floor from JSON object and adds it to the plan
    private void addFloor(Plan p, JSONObject jsonObject) {
        int width = jsonObject.getInt("width");
        int height = jsonObject.getInt("height");
        int coordinateX = jsonObject.getInt("coordinateX");
        int coordinateY = jsonObject.getInt("coordinateY");
        String label = jsonObject.getString("label");
        boolean showLabel = jsonObject.getBoolean("showLabel");
        int number = jsonObject.getInt("number");
        Floor f = p.addFloor(width, height, coordinateX, coordinateY, label, showLabel, number);
        addRooms(f, jsonObject);
    }

    // MODIFIES: f
    // EFFECTS: parses rooms from JSON object and adds them to the floor
    private void addRooms(Floor f, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rooms");
        for (Object json : jsonArray) {
            JSONObject nextRoom = (JSONObject) json;
            addRoom(f, nextRoom);
        }

    }

    // MODIFIES: f
    // EFFECTS: parses room from JSON object and adds it to the floor
    private void addRoom(Floor f, JSONObject jsonObject) {
        int width = jsonObject.getInt("width");
        int height = jsonObject.getInt("height");
        int coordinateX = jsonObject.getInt("coordinateX");
        int coordinateY = jsonObject.getInt("coordinateY");
        String label = jsonObject.getString("label");
        boolean showLabel = jsonObject.getBoolean("showLabel");
        String colour = jsonObject.getString("colour");
        Room room = new Room(width, height, coordinateX, coordinateY, label, showLabel, new HashSet<>(), colour);
        JSONArray jsonArray = jsonObject.getJSONArray("connections");
        for (Room r : f.getRooms()) {
            for (Object json : jsonArray) {
                boolean roomMatchesLabel = r.getLabel().equals(json.toString());
                if (roomMatchesLabel) {
                    room.addConnection(r);
                    r.addConnection(room);
                }
            }
        }
        addFurniture(room,jsonObject);
        f.addRoom(room);
    }

    // MODIFIES: r
    // EFFECTS: parses furniture from JSON object and adds it to the room
    private void addFurniture(Room r, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("furniture");
        for (Object json : jsonArray) {
            JSONObject nextFurniture = (JSONObject) json;
            addOneFurniture(r, nextFurniture);
        }
    }

    // MODIFIES: r
    // EFFECTS: parses a piece of furniture from JSON object and adds it to the room
    private void addOneFurniture(Room r, JSONObject jsonObject) {
        int width = jsonObject.getInt("width");
        int height = jsonObject.getInt("height");
        int coordinateX = jsonObject.getInt("coordinateX");
        int coordinateY = jsonObject.getInt("coordinateY");
        String label = jsonObject.getString("label");
        boolean showLabel = jsonObject.getBoolean("showLabel");
        String material = jsonObject.getString("material");
        String colour = jsonObject.getString("colour");
        Furniture f = new Furniture(width, height, coordinateX, coordinateY, label, showLabel, material,colour);
        r.addFurniture(f);
    }
}
