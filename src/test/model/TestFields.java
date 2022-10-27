package model;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFields {
    public void checkFloorFields(Floor f, int w, int h, int x, int y, String label, boolean showLabel, int n) {
        assertEquals(w, f.getWidth());
        assertEquals(h, f.getHeight());
        assertEquals(x, f.getCoordinateX());
        assertEquals(y, f.getCoordinateY());
        assertEquals(label, f.getLabel());
        assertEquals(showLabel,f.isShowLabel());
        assertEquals(n, f.getNumber());
    }

    public void checkFurnitureFields(Furniture f,
                                     int w,
                                     int h,
                                     int x,
                                     int y,
                                     String label,
                                     boolean showLabel,
                                     String material,
                                     String colour) {
        assertEquals(w, f.getWidth());
        assertEquals(h, f.getHeight());
        assertEquals(x, f.getCoordinateX());
        assertEquals(y, f.getCoordinateY());
        assertEquals(label, f.getLabel());
        assertEquals(showLabel,f.isShowLabel());
        assertEquals(material, f.getMaterial());
        assertEquals(colour, f.getColour());
    }

    public void checkPlanObjectFields(PlanObject pO, int w, int h, int x, int y, String label, boolean showLabel) {
        assertEquals(w, pO.getWidth());
        assertEquals(h, pO.getHeight());
        assertEquals(x, pO.getCoordinateX());
        assertEquals(y, pO.getCoordinateY());
        assertEquals(label, pO.getLabel());
        assertEquals(showLabel,pO.isShowLabel());
    }

    public void checkPlanFields(Plan p, String name, int w, int h, int fM, HashSet<Floor> floors) {
        assertEquals(name,p.getName());
        assertEquals(w,p.getLotWidth());
        assertEquals(h,p.getLotHeight());
        assertEquals(fM,p.getFurnitureMargins());
        assertEquals(floors,p.getFloors());
    }

    public void checkPlanFieldsNoCollections(Plan p, String name, int w, int h, int fM) {
        assertEquals(name,p.getName());
        assertEquals(w,p.getLotWidth());
        assertEquals(h,p.getLotHeight());
        assertEquals(fM,p.getFurnitureMargins());
    }

    public void checkRoomFields(Room r,
                                int w,
                                int h,
                                int x,
                                int y,
                                String label,
                                boolean showLabel,
                                HashSet<Room> connections,
                                String c) {
        assertEquals(w, r.getWidth());
        assertEquals(h, r.getHeight());
        assertEquals(x, r.getCoordinateX());
        assertEquals(y, r.getCoordinateY());
        assertEquals(label, r.getLabel());
        assertEquals(showLabel,r.isShowLabel());
        assertEquals(connections,r.getConnections());
        assertEquals(c,r.getColour());
        assertEquals(new HashSet<>(),r.getFurniture());
    }

    public void checkRoomFieldsNoCollections(Room r,
                                int w,
                                int h,
                                int x,
                                int y,
                                String label,
                                boolean showLabel,
                                String c) {
        assertEquals(w, r.getWidth());
        assertEquals(h, r.getHeight());
        assertEquals(x, r.getCoordinateX());
        assertEquals(y, r.getCoordinateY());
        assertEquals(label, r.getLabel());
        assertEquals(showLabel,r.isShowLabel());
        assertEquals(c,r.getColour());
    }

    public boolean matchingRoomFieldsNoCollections(Room r,
                                                   int w,
                                                   int h,
                                                   int x,
                                                   int y,
                                                   String label,
                                                   boolean showLabel,
                                                   String c) {
        return w == r.getWidth()
                && h == r.getHeight()
                && x == r.getCoordinateX()
                && y == r.getCoordinateY()
                && label.equals(r.getLabel())
                && showLabel == r.isShowLabel()
                && c.equals(r.getColour());
    }

    public boolean matchingFurnitureFieldsNoCollections(Furniture f,
                                                        int w,
                                                        int h,
                                                        int x,
                                                        int y,
                                                        String label,
                                                        boolean showLabel,
                                                        String material,
                                                        String colour) {
        return w == f.getWidth()
                && h == f.getHeight()
                && x == f.getCoordinateX()
                && y == f.getCoordinateY()
                && label.equals(f.getLabel())
                && showLabel == f.isShowLabel()
                && material.equals(f.getMaterial())
                && colour.equals(f.getColour());
    }
}
