package model;

// An object in an architectural plan with a label, a given size and a location on the lot
public abstract class PlanObject {
    private int width;
    private int height;
    private int coordinateX;
    private int coordinateY;
    private String label;
    private boolean showLabel;

    // REQUIRES: width > 0, height > 0, coordinateX [0, width], coordinateY [0, height]
    // EFFECTS: creates a planObject with a width, height, position, and label
    public PlanObject(int width, int height, int coordinateX, int coordinateY, String label, boolean showLabel) {
        this.width = width;
        this.height = height;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.label = label;
        this.showLabel = showLabel;
    }


    public int getWidth() {
        return width;
    }

//    public void setWidth(int width) {
//        this.width = width;
//    }

    public int getHeight() {
        return height;
    }

//    public void setHeight(int height) {
//        this.height = height;
//    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getLabel() {
        return label;
    }

//    public void setLabel(String label) {
//        this.label = label;
//    }

    public boolean isShowLabel() {
        return showLabel;
    }

//    public void setShowLabel(boolean showLabel) {
//        this.showLabel = showLabel;
//    }
}
