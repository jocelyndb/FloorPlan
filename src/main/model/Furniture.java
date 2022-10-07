package model;

// A piece of furniture with a label, material and colour as well as a given size and location relative to a room
public class Furniture extends PlanObject {
    private String material;
    private String colour;

    // REQUIRES: width > 0, height > 0, coordinateX and coordinateY place furniture within containing room
    // EFFECTS: creates a piece of furniture of a given width and height at a location relative to a room with a label
    //          and material
    public Furniture(int width,
                     int height,
                     int coordinateX,
                     int coordinateY,
                     String label,
                     boolean showLabel,
                     String material,
                     String colour) {
        super(width, height, coordinateX, coordinateY, label, showLabel);
        this.material = material;
        this.colour = colour;
    }

    public String getMaterial() {
        return material;
    }

//    public void setMaterial(String material) {
//        this.material = material;
//    }

    public String getColour() {
        return colour;
    }

//    public void setColour(String material) {
//        this.colour = colour;
//    }
}
