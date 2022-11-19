package ui.tools;

import model.Floor;
import model.Plan;
import ui.PlannerGui;

import javax.swing.*;
import java.util.HashSet;

// Represents a tool for creating rooms in an architectural plan
public class FloorTool extends PlanObjectTool {
    public FloorTool(PlannerGui planner, JComponent parent) {
        super(planner, parent);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected String getLabel() {
        return "Make Floor";
    }

    @Override
    protected Floor makePlanObject() {
        Plan plan = planner.getCurrentPlan();
        return makeFloor(plan.getFloors());
//        return plan.addFloor(1,1,1,1,
//                "TEMPORARY",
//                true,
//                1);

    }

    // MODIFIES: floors
    // EFFECTS: facilitates the creation of a new floor
    private Floor makeFloor(HashSet<Floor> floors) {
        System.out.println("\nNew Floor:");
        String label = getStringFromUser("\tLabel: ");
//        int number = getIntFromUser("\tNumber: ");
//        int width = getIntFromUser("\tWidth: ");
//        int height = getIntFromUser("\tHeight: ");
//        int coordinateX = getIntFromUser("\tLot Coordinate X: ");
//        int coordinateY = getIntFromUser("\tLot Coordinate Y: ");
        int number = Integer.parseInt(getStringFromUser("\tNumber: "));
        int width = Integer.parseInt(getStringFromUser("\tWidth: "));
        int height = Integer.parseInt(getStringFromUser("\tHeight: "));
        int coordinateX = Integer.parseInt(getStringFromUser("\tLot Coordinate X: "));
        int coordinateY = Integer.parseInt(getStringFromUser("\tLot Coordinate Y: "));

        Floor f = new Floor(width, height, coordinateX, coordinateY, label, true, number);
        floors.add(f);
//        planObjectPanel.setVisible(false);
//        planner.setActive
        return f;
    }

//
//    planObjectPanel.getWidth(),
//            planObjectPanel.getHeight(),
//            planObjectPanel.getX(),
//            planObjectPanel.getY()
}
