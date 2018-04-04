package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by Emily Roth and Prajakta Joshi
 * Emily Roth
 * er29242
 * 15470
 * Prajakta Joshi
 * pyj82
 * 15470
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {


    static GridPane gp = new GridPane();
    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        Application.launch(args);
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }
        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        // set up the world or critter object
        String input = kb.nextLine();
        //gets rid of beginning and ending whitespace
        input =input.trim();
        // starts loop to keep world going until quit
        while(!input.equals("quit")){
            // process commands
            String[] arguments = input.split("\\s+");
            String command = arguments[0];
            // parse and put into positions
            switch (command){
                // Shows the world
                case "show":
                    if(arguments.length == 1) {
                        Critter.displayWorld();
                    }
                    else {
                        System.out.println("Error Processing: "+ input);
                    }
                    break;

                // Determines how many time steps to run
                case "step":
                    if(arguments.length == 1){
                        Critter.worldTimeStep();
                    }
                    else if(arguments.length ==2){
                        int numOfTimes =0;
                        try {
                            numOfTimes = Integer.parseInt(arguments[1]);
                        }
                        catch (Exception e){
                            System.out.println("Error Processing: "+ input);
                        }
                        for(int i = 0; i< numOfTimes; i++){
                            Critter.worldTimeStep();
                        }
                    }
                    else {
                        System.out.println("Error Processing: "+ input);
                    }
                    break;

                // Set random number seed
                case "seed":
                    if(arguments.length == 2){
                        try {
                            int seed = Integer.parseInt(arguments[1]);
                            Critter.setSeed(seed);
                        }
                        catch (Exception e){
                            System.out.println("Error Processing: "+ input);
                        }
                    }
                    else{
                        System.out.println("Error Processing: "+ input);
                    }
                    break;
                //make given number of critters in given class
                case "make":
                    if(arguments.length >= 2){
                        String critterClass = arguments[1];
                        int numOfCritters = 1; // default amount
                        if(arguments.length == 3){
                            try {
                                numOfCritters = Integer.parseInt(arguments[2]);
                            }
                            catch (Exception e){
                                System.out.println("Error Processing: "+ input);
                            }//catch
                        }// if
                        try{
                            for(int i =0; i< numOfCritters; i++) {

                                Critter.makeCritter(critterClass); // passes to model
                            }//for
                        }//try
                        catch (Exception e){
                            System.out.println("Error Processing: "+ input);
                            break;
                        }//catch
                    }// if
                    else{
                        System.out.println("Error Processing: "+ input);
                    }//else
                    break;
                // displays stats
                case "stats":
                    if(arguments.length == 2){
                        try{
                            // gets the qualified name
                            String qualifiedName = myPackage.toString() + "." + arguments[1];
                            // gets class using qualified name
                            Class<Critter> new_class = (Class<Critter>) Class.forName(qualifiedName);
                            // get the correct method from the class
                            Method m = new_class.getMethod("runStats", List.class);
                            // Invoke the method on the current critter then enter params
                            m.invoke(new_class, Critter.getInstances(arguments[1]));
                        }
                        catch (Exception e){
                            System.out.println("Error Processing: "+ input);
                        }//catch
                    }// if
                    else{
                        // not correct amout of args
                        System.out.println("Error Processing: " + input);
                    }//else
                    break;
                // Quits the program
                case "quit":
                    System.out.println("Error Processing: "+ input);
                    break;
                // Invalid option picked
                default:
                    System.out.println("Invalid Command: "+command);
                    break;
            }//switch
            // get new input
            input = kb.nextLine();
            input =input.trim();
        } // while loop
        /* Write your code above */
        System.out.flush();
    }

    // where the application actually starts
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane rootPane = new BorderPane();
        FlowPane fp = new FlowPane();
        //CritterGrid.paint();
        //
       CritterGrid.createGrid();
        // Step
        gp.setGridLinesVisible(true);
        ChoiceBox makeChoice = new ChoiceBox<>();
        makeChoice.getItems().add("Craig");
        makeChoice.getItems().add("Eugene");
        makeChoice.getItems().add("Elise");
        makeChoice.getItems().add("Reagan");
        makeChoice.getItems().add("Eric");
        makeChoice.getItems().add("Algae");

        Button make = new Button("Make");
        make.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // make a new critter
                System.out.println("Make button");
                String critterSelection = (String) makeChoice.getValue();
                try{
                        Critter.makeCritter(critterSelection); // passes to model

                }//try
                catch (Exception e){
                    System.out.println("How did this happen");
                }//catch
            }
        });

        // checkbox for run stats
        final String[] statsOptions = new String[]{"Craig", "Eugene", "Elise", "Reagan", "Eric", "Algae"};
        ArrayList<String> statsClicked = new ArrayList<>();

        final CheckBox[] cbs = new CheckBox[statsOptions.length];
        for(int i = 0; i< statsOptions.length; i++){
            final CheckBox cb = cbs[i] = new CheckBox(statsOptions[i]);
            int finalI = i;
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    // run stats method
                    if(newValue){
                        // run the stats on this
                        statsClicked.add(statsOptions[finalI]);

                    }else{
                        statsClicked.remove(statsOptions[finalI]);
                    }
                }
            });
        }

        VBox statsBox = new VBox();
        statsBox.getChildren().addAll(cbs);

        // Run Stats
        Button stats = new Button("Run Stats");
        stats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Run stats");
            }
        });

        // display
        Button show = new Button("Show");
        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Show Bttn");
                Critter.displayWorld();

            }
        });

        Button step = new Button("Step");
        step.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // call a world time step
                System.out.println("Step button");
                Critter.worldTimeStep();
                for(String str: statsClicked){
                    try{
                        // gets the qualified name
                        String qualifiedName = myPackage.toString() + "." + str;
                        // gets class using qualified name
                        Class<Critter> new_class = (Class<Critter>) Class.forName(qualifiedName);
                        // get the correct method from the class
                        Method m = new_class.getMethod("runStats", List.class);
                        // Invoke the method on the current critter then enter params
                        m.invoke(new_class, Critter.getInstances(str));
                    }
                    catch (Exception e){
                        System.out.println("Error Processing: ");
                    }//catch
                }
            }
        });


        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        fp.getChildren().add(show);
        fp.getChildren().add(stats);
        fp.getChildren().add(step);
        fp.getChildren().add(makeChoice);
        fp.getChildren().add(statsBox);
        fp.getChildren().add(make);
        fp.getChildren().add(quit);
        rootPane.setRight(gp);
        rootPane.setLeft(fp);
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
