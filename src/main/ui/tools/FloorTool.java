package ui.tools;

import model.Floor;
import model.Plan;
import ui.PlannerGui;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;

// Represents a tool for creating floors in an architectural plan
public class FloorTool extends PlanObjectTool {
    // EFFECTS: creates a tool for making floors
    public FloorTool(PlannerGui planner, JComponent parent) {
        super(planner, parent);
    }

    // EFFECTS: default behaviour does nothing
    @Override
    protected void addListener() {}

    // EFFECTS: returns the label for the floor tool
    @Override
    protected String getLabel() {
        return "Make Floor";
    }

    // MODIFIES: this, planner
    // EFFECTS: makes a new floor in the current plan
    @Override
    protected Floor makePlanObject() {
        Plan plan = planner.getCurrentPlan();
        this.deactivate();
        return makeFloor(plan);
    }

    // Handles mouse presses
    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
        super.mousePressedInDrawingArea(e);
    }

    // MODIFIES: floors
    // EFFECTS: facilitates the creation of a new floor and returns it if its label can be added to the
    //          jlist for display. Otherwise returns null
    private Floor makeFloor(Plan p) {
        if (jlist != null) {
            String label = getStringFromUser("Label");
            int number = Integer.parseInt(getStringFromUser("Floor Number"));
            Floor f = p.addFloor(planObjectPanel.getWidth(),
                    planObjectPanel.getHeight(),
                    planObjectPanel.getX(),
                    planObjectPanel.getY(),
                    label,
                    true,
                    number);
            jlist.addElement(f.getLabel());
            return f;
        }
        return null;
    }
}
