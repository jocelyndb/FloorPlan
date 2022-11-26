package ui;

import model.Floor;
import model.Plan;
import model.PlanObject;
import persistence.PlanReader;
import ui.tools.FloorTool;
import ui.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

// Gui for architectural planner application
public class PlannerGui extends JFrame {
    private static final String JSON_DIRECTORY = "./data/";
    private List<Plan> plans;

    private Plan currentPlan;
    private Floor activeFloor;
    private List<Tool> tools;
    private Tool activeTool;
    private PlanReader planReader;

    // EFFECTS: runs the planner application
    public PlannerGui() {
        super("Architectural Planner");
        ImageIcon icon = new ImageIcon("./src/main/ui/images/blueprint.png");
        this.setIconImage(icon.getImage());
        initializeFields();
        displayMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: displays a menu of existing plans with options
    //          to open any of the plans
    private void displayMainMenu() {
        JFrame mainMenu = new JFrame("Select a Plan");
        JPanel menuPanel = new JPanel();
        mainMenu.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            plans = loadPlanFiles();
        } catch (IOException e) {
            System.out.println("Unable to load files in " + JSON_DIRECTORY);
        }
        mainMenu.setSize(new Dimension(300,100));
        DefaultListModel tempListOfPlans = new DefaultListModel();
        for (Plan plan : plans) {
            tempListOfPlans.addElement(plan.getName());
        }
        JList<String> jlistOfPlans = new JList(tempListOfPlans);
        mainMenu.add(menuPanel);
        menuPanel.add(jlistOfPlans);
        mainMenu.setVisible(true);
        jlistOfPlans.addListSelectionListener(event -> planSelected(mainMenu, jlistOfPlans.getSelectedIndex()));
    }

    // MODIFIES: this
    // EFFECTS: opens the selected plan
    private void planSelected(JFrame mainMenu, int index) {
        mainMenu.getContentPane().removeAll();
        mainMenu.repaint();
        mainMenu.setVisible(false);
        menuItemSelected(index);
    }

    // MODIFIES: this
    // EFFECTS: Configures the planner for and loads the newly selected plan at index
    private void menuItemSelected(int index) {
        currentPlan = plans.get(index);
        this.getContentPane().removeAll();
        this.repaint();
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS: resizes the window for working on the current plan
    private void resizeWindow() {
        Dimension planSize = new Dimension(currentPlan.getLotWidth(), currentPlan.getLotHeight());
        this.setSize(planSize);
        this.setMaximumSize(planSize);
        this.setMinimumSize(planSize);
    }

    // MODIFIES: this
    // EFFECTS:  creates a DrawingMouseListener for the JFrame
    private void initializeInteraction() {
        DrawingMouseListener dml = new DrawingMouseListener();
        addMouseListener(dml);
        addMouseMotionListener(dml);
    }


    // MODIFIES: this
    // EFFECTS: draws the JFrame window the ArchitecturalPlanner
    //          operates in
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        resizeWindow();
        displayFloorMenu();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: displays a list of floors from which the user can select a floor to display and edit, open to tool for
    //          creating a new floor, and delete a floor
    private void displayFloorMenu() {
        JFrame floorMenu = new JFrame("Floors");
        floorMenu.setLayout(new BorderLayout());
        floorMenu.setSize(100,400);
        JPanel floorList = new JPanel();
        DefaultListModel listOfFloors = new DefaultListModel();
        for (Floor floor : currentPlan.getFloors()) {
            listOfFloors.addElement(floor.getLabel());
        }
        JPanel floorButtons = new JPanel();
        floorButtons.setLayout(new GridLayout(0,1));
        JList<String> jlistOfFloors = new JList(listOfFloors);
//        this.add(floorMenu);
        JButton delete = new JButton("Delete");
        JButton select = new JButton("Select");
        JButton quit = new JButton("Quit");

        FloorTool floorTool = initializeFloorTool(listOfFloors, floorButtons);

        floorButtons.add(delete);
        floorButtons.add(select);
        floorButtons.add(quit);

        floorTool.getButton().addActionListener((event -> makeFloor(floorTool)));
        delete.addActionListener(event -> floorDelete(event, jlistOfFloors.getSelectedValue(), listOfFloors));
        select.addActionListener(event -> selectFloor(event, jlistOfFloors.getSelectedValue()));
        quit.addActionListener(event -> quit(floorMenu));

        initializeFloorMenu(floorMenu, floorList, floorButtons, jlistOfFloors);
    }


    // MODIFIES: this
    // EFFECTS: creates a new floor tool and adds it to tools
    private FloorTool initializeFloorTool(DefaultListModel listOfFloors, JPanel floorButtons) {
        FloorTool floorTool = new FloorTool(this, floorButtons);
        floorTool.setJlist(listOfFloors);
        tools.add(floorTool);
        return floorTool;
    }

    // MODIFIES: this
    // EFFECTS: initializes the floor menu JComponents
    private void initializeFloorMenu(JFrame floorMenu,
                                     JPanel floorList,
                                     JPanel floorButtons,
                                     JList<String> jlistOfFloors) {
        floorList.add(jlistOfFloors);
        floorMenu.add(floorList, BorderLayout.NORTH);
        floorMenu.add(floorButtons, BorderLayout.SOUTH);
        floorMenu.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: facilitates quitting the current plan to main meny
    private void quit(JFrame menu) {
        handleSave();
        this.getContentPane().removeAll();
        this.repaint();
        this.setVisible(false);
        menu.getContentPane().removeAll();
        menu.repaint();
        menu.setVisible(false);
        displayMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: allows the user to save a plan
    private void handleSave() {
        String path = JSON_DIRECTORY + currentPlan.getName() + ".json";
        try {
            currentPlan.saveNew(path);
        } catch (FileAlreadyExistsException e) {
            boolean doSaveOver = getYNFromUser("Would you like to save over the existing "
                    + currentPlan.getName()
                    + " plan?");
            if (doSaveOver) {
                try {
                    currentPlan.saveOver(path);
                } catch (FileNotFoundException ex) {
                    System.out.println("There was a problem saving your plan");
                }
            }
        } catch (IOException e) {
            System.out.println("There was a problem saving your plan");
        }
    }

    // MODIFIES: this
    // EFFECTS: returns a yes or no response to a question in a dialog box as a boolean
    private boolean getYNFromUser(String s) {
        return JOptionPane.showConfirmDialog(null, s, "Confirm", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION ? true : false;
    }

    // MODIFIES: this
    // EFFECTS: Sets the active tool to a FloorTool and clears the plan screen
    private void makeFloor(FloorTool floorTool) {
        setActiveTool(floorTool);
        this.getContentPane().removeAll();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: opens the selected floor
    private void selectFloor(ActionEvent e, String label) {
        for (Floor floor : currentPlan.getFloors()) {
            if (floor.getLabel().equals(label)) {
                this.getContentPane().removeAll();
                this.repaint();
                renderFloor(floor);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays a floor in the architectural planner
    public void renderFloor(Floor floor) {
        JPanel planObjectEditor = new JPanel();
        JPanel planObjectPanel = new JPanel();
//        rectangle.setLocation(e.getPoint());
        planObjectPanel.setLocation(floor.getCoordinateX(),floor.getCoordinateY());
        planObjectPanel.setBounds(floor.getCoordinateX(),
                floor.getCoordinateY(),
                floor.getWidth(),
                floor.getHeight());
        JLabel planObjectLabel = new JLabel("Floor " + floor.getNumber() + ": " + floor.getLabel());
        if (floor.isShowLabel()) {
            planObjectLabel.setVisible(true);
        } else {
            planObjectLabel.setVisible(false);
        }
        planObjectEditor.add(planObjectLabel);
        this.add(planObjectPanel);
        this.add(planObjectEditor);
//        paintComponent();
        planObjectPanel.setBorder(BorderFactory.createLineBorder(new Color(200,30,50), 3));

        planObjectPanel.setVisible(true);
        planObjectEditor.setVisible(true);
    }

    // MODIFIES: this, list
    // EFFECTS: deletes the selected floor
    private void floorDelete(ActionEvent e, String label, DefaultListModel list) {
        int count = 0;
        for (Floor floor : currentPlan.getFloors()) {
            if (floor.getLabel().equals(label)) {
                currentPlan.getFloors().remove(floor);
                break;
            }
            count++;
        }
        list.remove(count);
    }

    // MODIFIES: this
    // EFFECTS: Handles saving and quitting to the main menu
    private void setDefaultCloseOperation() {
        // TODO: consider a global saving behaviour
    }

    // MODIFIES: this
    // EFFECTS: instantiates planReader with as a new PlanReader
    private void initializeFields() {
        planReader = new PlanReader();
        tools = new ArrayList<Tool>();
//        setMinimumSize(new Dimension(WIDTH / 2, HEIGHT / 2));
//        setMaximumSize(new Dimension(WIDTH / 2, HEIGHT / 2));
//        setSize(new Dimension(WIDTH / 2, HEIGHT / 2));
    }

    // MODIFIES: this
    // EFFECTS: adds all valid plans in the data folder to plans
    private List<Plan> loadPlanFiles() throws IOException {
        List<Plan> plans = new ArrayList<>();
        File dataDirectory = new File(JSON_DIRECTORY);
        File[] filesInDirectory = dataDirectory.listFiles();
        for (File file : filesInDirectory) {
            String fileName = file.getPath();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (fileExtension.equals("json")) {
                Plan p = planReader.read(fileName);
                if (!(null == p)) {
                    plans.add(p);
                }
            }
        }
        return plans;
    }

    // EFFECTS: returns true if a string only contains an integer
    private static boolean hasInt(String s) {
        if (s == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // EFFECTS: if activeTool != null, mousePressedInDrawingArea  is called for the active tool and the viewport
    //          is updated
    private void handleMousePressed(MouseEvent e)  {
        if (activeTool != null) {
            activeTool.mousePressedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, mouseReleasedInDrawingArea is called for the active tool and the viewport
    //    //          is updated
    private void handleMouseReleased(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseReleasedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, mouseClickedInDrawingArea is called for the active tool and the viewport
    //          is updated
    private void handleMouseClicked(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseClickedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, then mouseDraggedInDrawingArea is called for the active tool
    private void handleMouseDragged(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseDraggedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: returns the current plan
    public Plan getCurrentPlan() {
        return currentPlan;
    }

    // MODIFIES: this
    // EFFECTS: sets tool as the activeTool
    public void setActiveTool(Tool tool) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        tool.activate();
        activeTool = tool;
    }

    // Handles mouse input on architecture planner canvas
    private class DrawingMouseListener extends MouseAdapter {

        // EFFECTS: Forward mouse pressed event to the active tool
        public void mousePressed(MouseEvent e) {
            handleMousePressed(e);
        }

        // EFFECTS: Forward mouse released event to the active tool
        public void mouseReleased(MouseEvent e) {
            handleMouseReleased(e);
        }

        // EFFECTS:Forward mouse clicked event to the active tool
        public void mouseClicked(MouseEvent e) {
            handleMouseClicked(e);
        }

        // EFFECTS:Forward mouse dragged event to the active tool
        public void mouseDragged(MouseEvent e) {
            handleMouseDragged(e);
        }
    }
}
