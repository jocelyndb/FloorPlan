package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

// Architectural planner application
public class PlannerApp {
    private List<Plan> plans;
    private Scanner input;

    // EFFECTS: runs the planner application
    public PlannerApp() {
        runPlanner();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPlanner() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase(Locale.ROOT);

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processMainMenuCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processMainMenuCommand(String command) {
        if (hasInt(command)) {
            int planNumber = Integer.parseInt(command);
            if ((1 <= planNumber) && (planNumber <= plans.size())) {
                doPlan(plans.get(planNumber - 1));
            } else {
                System.out.println("Plan #" + planNumber + " not found...");
            }
        } else if (command.equals("n")) {
            makePlan();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: facilitates creation of a new plan
    private void makePlan() {
        System.out.println("\nNew Plan:");
        System.out.println("\tName: ");
        String name = input.next();
        System.out.println("\tLot Width: ");
        int width = input.nextInt();
        System.out.println("\tLot Height: ");
        int height = input.nextInt();
        System.out.println("\tFurniture Margins:");
        int fm = input.nextInt();

        Plan p = new Plan(name, width, height, fm);
        plans.add(p);
        doPlan(p);
    }

    // MODIFIES: this
    // EFFECTS: allows the user to edit a plan
    private void doPlan(Plan plan) {
        System.out.println("Doing a plan");
        // stub
    }

    // EFFECTS: displays a menu of options to the user for creating new and opening existing plans
    private void displayMainMenu() {
        int selector = 1;
        System.out.println("\n Select from:");
        if (!plans.isEmpty()) {
            for (Plan p : plans) {
                System.out.println("\t[" + selector + "] -> " + p.getName());
                selector++;
            }
        }
        System.out.println("\n\t[n] -> New Plan");
        System.out.println("\n\t[q] -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes plans
    private void init() {
        plans = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
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
}
