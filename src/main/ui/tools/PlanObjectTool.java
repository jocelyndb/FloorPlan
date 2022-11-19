package ui.tools;

import model.Floor;
import model.PlanObject;
import ui.PlannerGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

// Represents a tool for creating PlanObjects in an architectural plan
public abstract class PlanObjectTool extends Tool {
    protected Rectangle rectangle;
    protected JPanel planObjectPanel;

    public PlanObjectTool(PlannerGui planner, JComponent parent) {
        super(planner, parent);
        rectangle = null;
    }

    // MODIFIES: this
    // EFFECTS: creates new button and adds to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton(getLabel());
        button = customizeButton(button);
    }

    // MODIFIES: this
    // EFFECTS: associate button with a listener
    @Override
    protected void addListener() {
        button.addActionListener(new PlanObjectToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS: a PlanObject is created when a MouseEvent occurs, which is represented by a rectangle
    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
//        rectangle = new Rectangle();
        planObjectPanel = new JPanel();
//        rectangle.setLocation(e.getPoint());
        planObjectPanel.setLocation(e.getPoint());
//        paintComponent();
        planObjectPanel.setBorder(BorderFactory.createLineBorder(new Color(200,30,50), 3));
        planner.add(planObjectPanel);
        planObjectPanel.setVisible(true);
//        planner.addToDrawing(shape);
    }

    protected void paintComponent(Graphics g) {
        planObjectPanel.paintComponents(g);
        g.drawString("This is my custom Panel!",10,20);
        g.setColor(Color.RED);
        g.fillRect(planObjectPanel.getX(),
                planObjectPanel.getY(),
                planObjectPanel.getWidth(),
                planObjectPanel.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(planObjectPanel.getX(),
                planObjectPanel.getY(),
                planObjectPanel.getWidth(),
                planObjectPanel.getHeight());
    }


    // MODIFIES: this
    // EFFECTS: When the mouse is released, instantiates the plan object;
    @Override
    public void mouseReleasedInDrawingArea(MouseEvent e) {
        makePlanObject();
    }

    // MODIFIES: this
    // EFFECTS: sets the bounds of the rectangle to where the mouse is dragged to
    @Override
    public void mouseDraggedInDrawingArea(MouseEvent e) {
//        diagram.setBounds(e.getPoint());
//        rectangle.setSize((int)(e.getX() - rectangle.getX()),(int)(e.getY() - rectangle.getY()));
        planObjectPanel.setSize(e.getX() - planObjectPanel.getX(),e.getY() - planObjectPanel.getY());
        planner.paint(planObjectPanel.getGraphics());
//        planner.paint
//        planner.paintComponents(e.getPoint());

    }

    // EFFECTS: Returns the string for the label.
    protected abstract String getLabel();

    // EFFECTS: Makes a plan object
    protected abstract PlanObject makePlanObject();

    // MODIFIES: this
    // EFFECTS: Makes and returns a new rectangle
    private Rectangle makeRectangle(MouseEvent e) {
        rectangle = new Rectangle(e.getPoint());
        return rectangle;
    }

    private class PlanObjectToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the PlanObject tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            planner.setActiveTool(PlanObjectTool.this);
        }
    }

    // EFFECTS: returns a string from the user with a specific prompt.
    protected String getStringFromUser(String s) {
        System.out.print(s);
        return JOptionPane.showInputDialog(planObjectPanel,"Enter Name");
    }
}
