package model;

import java.util.HashSet;

// An architectural plan of a given size with a name, furniture margins, and floors
public class Plan {
    private String name;
    private int lotWidth;
    private int lotHeight;
    private int furnitureMargins;
    private HashSet<Floor> floors;

    // REQUIRES: lotWidth > 0, lotHeight > 0, furnitureMargins >= 0 and below lotHeight and lotWidth
    // EFFECTS: creates a new plan of a given lot size with furniture margins
    public Plan(String name, int lotWidth, int lotHeight, int furnitureMargins) {
        this.name = name;
        this.lotWidth = lotWidth;
        this.lotHeight = lotHeight;
        this.furnitureMargins = furnitureMargins;
        floors = new HashSet<>();
    }


    // REQUIRES: width > 0, height > 0, floor number is not already taken by another floor in plan
    // MODIFIES: this
    // EFFECTS: adds a floor with a width, height, label, and number with no rooms to listOfFloor and returns it
    public Floor addFloor(int width,
                    int height,
                    int coordinateX,
                    int coordinateY,
                    String label,
                    boolean showLabel,
                    int number) {
        Floor f = new Floor(width, height, coordinateX, coordinateY, label, showLabel, number);
        floors.add(f);
        return f;
    }

    // REQUIRES: Floor number n must be in listOfFloor
    // EFFECTS: returns the floor with number n
    public Floor getFloor(int n) {
        for (Floor f : floors) {
            if (f.getNumber() == n) {
                return f;
            }
        }
        return new Floor(1,1,0,0,"DNE",false,-1);
    }

    public String getName() {
        return name;
    }

    public int getLotWidth() {
        return lotWidth;
    }

    public int getLotHeight() {
        return lotHeight;
    }

    public int getFurnitureMargins() {
        return furnitureMargins;
    }

    public HashSet<Floor> getFloors() {
        return floors;
    }


}
