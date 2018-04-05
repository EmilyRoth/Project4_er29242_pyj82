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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.awt.Label;
import java.awt.TextField;
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
          }

    // where the application actually starts
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane rootPane = new BorderPane();
        FlowPane fp = new FlowPane();
        //create grid
        CritterGrid.createGrid();
        gp.setGridLinesVisible(true);

        /**
         *  find all critters, sets arraylist of strings to all critter names
         */
        ArrayList<String> names = new ArrayList<String>();
        File dir = new File("C:\\Users\\Emily\\IdeaProjects\\Project4_er29242_pyj82\\src\\assignment4");
        File[] listOfFiles = dir.listFiles();
        try{
            for (File file :listOfFiles) {
                if (file.isFile() &&file.getName().endsWith(".java")) {
                    String classname = file.getName().substring(0,file.getName().length()-5);

                    if(classname.equals("MyCritter7")){
                        //dont add to the list, not a critter class
                    }
                    else if(classname.equals("MyCritter6")){
                        //dont
                    }
                    else if(classname.equals("MyCritter1")){
                        //dont
                    }
                    else if(classname.equals("Main")){
                        //dont
                    }
                    else if(classname.equals("InvalidCritterException")){
                        //dont
                    }
                    else if(classname.equals("Header")){
                        //dont
                    }
                    else if(classname.equals("CritterGrid")){

                    }
                    else if(classname.equals("Params")){

                    }
                    else if(classname.equals("Critter")){

                    }
                    else {
                        //only valid critter names
                        names.add(classname);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        /**
         * choiceBoxes for make
         */
        ChoiceBox makeChoice = new ChoiceBox<>();
        makeChoice.getItems().addAll(names);

        javafx.scene.control.TextField makeNum = new javafx.scene.control.TextField("1");

        Button make = new Button("Make");
        make.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // make a new critter
                System.out.println("Make button");
                String critterSelection = (String) makeChoice.getValue();
                try{
                        int numCrit =0;
                        try{
                            numCrit = Integer.parseInt(makeNum.getText());
                        }
                        catch (Exception e){
                            System.out.println("not a number");
                        }
                        for(int i=0; i< numCrit;i++) {
                            Critter.makeCritter(critterSelection); // passes to model
                        }
                        Critter.displayWorld();

                }//try
                catch (Exception e){
                    System.out.println("How did this happen");
                }//catch
            }
        });
        /**
         * formats them in a row
         */
        HBox makeBox = new HBox();
        makeBox.getChildren().add(makeNum);
        makeBox.getChildren().add(makeChoice);
        makeBox.getChildren().add(make);

        /**
         *  checkbox for run stats
         */

        ArrayList<String> statsClicked = new ArrayList<>();

        final CheckBox[] cbs = new CheckBox[names.size()];
        for(int i = 0; i< names.size(); i++){
            final CheckBox cb = cbs[i] = new CheckBox(names.get(i));
            int finalI = i;
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    // run stats method
                    if(newValue){
                        // run the stats on this
                        statsClicked.add(names.get(finalI));

                    }else{
                        statsClicked.remove(names.get(finalI));
                    }
                }
            });
        }

        VBox statsBox = new VBox();
        statsBox.getChildren().addAll(cbs);
        String[] stringStats = new String[1];

        // text box to put the run stats into
        Text runStats = new Text();



        //step
        Button step = new Button("Step");
        step.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // call a world time step
                System.out.println("Step button");
                Critter.worldTimeStep();
                Critter.displayWorld();
                String statsOut = "";
                for(String str: statsClicked){
                    try{
                        // gets the qualified name
                        String qualifiedName = myPackage.toString() + "." + str;
                        // gets class using qualified name
                        Class<Critter> new_class = (Class<Critter>) Class.forName(qualifiedName);
                        // get the correct method from the class
                        Method m = new_class.getMethod("runStats", List.class);
                        // Invoke the method on the current critter then enter params
                        Object value = m.invoke(new_class, Critter.getInstances(str));
                        statsOut = statsOut + "\n" +(String) value;
                    }
                    catch (Exception e){
                        System.out.println("Error Processing: ");
                    }//catch
                }
                runStats.setText(statsOut);
            }
        });
        /**
         * animation: slider to adjust number of steps per frame
         */

        Slider slider = new Slider(1, 100, 1);
        slider.setMajorTickUnit(10);
        slider.setBlockIncrement(1);

        Timeline animateWorld = new Timeline(
                new KeyFrame(Duration.millis(200), event -> {
                    for(int i=0; i< slider.getValue(); i++) {
                        Critter.worldTimeStep();
                    }
                    Critter.displayWorld();
                    String statsOut = "";
                    for (String str : statsClicked) {
                        try {
                            // gets the qualified name
                            String qualifiedName = myPackage.toString() + "." + str;
                            // gets class using qualified name
                            Class<Critter> new_class = (Class<Critter>) Class.forName(qualifiedName);
                            // get the correct method from the class
                            Method m = new_class.getMethod("runStats", List.class);
                            // Invoke the method on the current critter then enter params
                            Object value = m.invoke(new_class, Critter.getInstances(str));
                            statsOut = statsOut + "\n" +(String) value;
                           // runStats.setText((String) value);
                        } catch (Exception e) {
                            System.out.println("Error Processing: ");
                        }//catch
                    }
                    runStats.setText(statsOut);
                })
        );

        /**
         * animation: start stop button for animation
         */

        animateWorld.setCycleCount(Animation.INDEFINITE);
        ToggleButton animate = new ToggleButton("start");

        animate.setOnAction(event -> {
            if (animate.isSelected()) {
                step.setDisable(true);
                statsBox.setDisable(true);
                makeChoice.setDisable(true);
                make.setDisable(true);
                slider.setDisable(true);
                animateWorld.play();
            } else {
                animateWorld.stop();
                step.setDisable(false);
                statsBox.setDisable(false);
                makeChoice.setDisable(false);
                make.setDisable(false);
                slider.setDisable(false);
            }
        });

        /**
         * quit button. exits program
         */
        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        /**
         * formating on flowpane
         * make
         * animation
         * stats boxes
         * quit
         * stats result
         */
        gp.setGridLinesVisible(true);

        String finalStats = stringStats[0];
        System.out.println("Final stats: "+finalStats);

        runStats.setFont(Font.font("Comic Sans MS", 20));

        VBox down = new VBox();

        HBox across = new HBox();
        across.getChildren().add(step);
        across.getChildren().add(animate);
        across.getChildren().add(slider);


        down.getChildren().add(makeBox);
        down.getChildren().add(across);
        down.getChildren().add(statsBox);
        down.getChildren().add(quit);
        statsBox.getChildren().add(runStats);
        down.getChildren().add(runStats);

        fp.getChildren().add(down);
        rootPane.setRight(gp);
        rootPane.setLeft(fp);
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
