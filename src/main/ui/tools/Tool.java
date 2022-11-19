package ui.tools;

import ui.PlannerGui;

import javax.swing.*;
import java.awt.event.MouseEvent;

// Represents a tool for an architectural planner application,
// based on the implementation in the SimpleDrawingPlayer example
public abstract class Tool {
    protected JButton button;
    protected PlannerGui planner;
    private boolean active;
    private boolean usable;

    // EFFECTS: creates an inactive tool for the planner
    public Tool(PlannerGui planner, JComponent parent) {
        this.planner = planner;
        createButton(parent);
        addToParent(parent);
        active = false;
        usable = false;
        addListener();
    }

    // EFFECTS: makes a listener for the tool
    protected abstract void addListener();

    // EFFECTS: returns true if the tool is active
    public boolean isActive() {
        return active;
    }

    // EFFECTS: sets active to true
    public void activate() {
        active = true;
    }

    // EFFECTS: sets active to false
    public void deactivate() {
        active = false;
    }

    // EFFECTS: returns true if the tool is usable
    public boolean isUsable() {
        return usable;
    }

    // EFFECTS: sets usable to true
    public void makeUsable() {
        usable = true;
    }

    // EFFECTS: sets usable to false
    public void makeUnusable() {
        active = false;
    }

    // EFFECTS: returns the tool's button
    public JButton getButton() {
        return button;
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // MODIFIES: parent
    // EFFECTS: adds the button for selecting the tool to the parent component
    private void addToParent(JComponent parent) {
        parent.add(button);
    }

    // EFFECTS: makes a button for selecting the tool
    protected abstract void createButton(JComponent parent);

    // EFFECTS: default behaviour does nothing
    public void mousePressedInDrawingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseReleasedInDrawingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseClickedInDrawingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseDraggedInDrawingArea(MouseEvent e) {}

}
