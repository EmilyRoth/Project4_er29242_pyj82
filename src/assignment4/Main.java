package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

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
        
        System.out.println("GLHF");
        // set up the world or critter object
        String input = kb.nextLine();
        while(!input.equals("quit")){
            // process commands
            String[] arguments = input.split("\\s+");
            String command = arguments[0];
            // parse and put into positions
            switch (command){
                // Shows the world
                case "show":
                    Critter.displayWorld();
                    break;

                // Determines how many time steps to run
                case "step":
                    if(arguments.length == 1){
                        Critter.worldTimeStep();
                    }
                    else{
                        int numOfTimes = Integer.parseInt(arguments[1]);
                        for(int i = 0; i< numOfTimes; i++){
                            Critter.worldTimeStep();
                        }
                    }
                    break;

                // Set random number seed
                case "seed":
                    if(arguments.length == 2){
                        int seed = Integer.parseInt(arguments[1]);
                        Critter.setSeed(seed);
                    }
                    else{
                        System.out.println("Invalid Input");
                    }
                    break;

                //make given number of critters in given class
                case "make":
                    if(arguments.length == 2 || arguments.length == 3){
                        String critterClass = arguments[1];
                        int numOfCritters = 1; // default amount
                        if(arguments.length == 3){
                            try {
                                numOfCritters = Integer.parseInt(arguments[2]);
                            }
                            catch (Exception e){
                                numOfCritters = 1;
                            }
                        }
                        try{
                            for(int i =0; i< numOfCritters; i++) {
                                Critter.makeCritter(critterClass);
                                //todo: qualified name- Handled in critter
                            }
                        }
                        catch (Exception e){
                            System.out.println("Invalid Input: Not a Critter Class");
                            break;
                        }

                    }
                    else{
                        System.out.println("Invalid Input");
                    }
                    break;

                // displays states
                case "stats":
                    if(arguments.length == 2){
                        try{
                            Critter.runStats(Critter.getInstances(arguments[1]));
                            //todo: qualified name and runStats
                        }
                        catch (InvalidCritterException e){
                            System.out.println("Invalid Input: Not a Critter Class");
                        }

                    }
                    else{
                        System.out.println("Invalid Input");
                    }
                    break;

                default:
                    System.out.println("Invalid Command: "+command);
                    break;
            }

            // todo: method to parse it immediately to trim white space
            // menu to do each action
            // uses parse

            // get new input
            input = kb.nextLine();
        }


        /* Write your code above */
        System.out.flush();

    }
}
