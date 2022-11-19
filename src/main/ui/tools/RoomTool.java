package ui.tools;

import model.Floor;
import model.Room;
import ui.PlannerGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

// Represents a tool for creating rooms in an architectural plan
public class RoomTool extends PlanObjectTool {
    public RoomTool(PlannerGui planner, JComponent parent) {
        super(planner, parent);

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected String getLabel() {
        return "Make Room";
    }

    @Override
    protected Room makePlanObject() {
        return null;
    }
}
