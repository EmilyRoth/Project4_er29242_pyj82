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

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 * Abstract class
 */
public abstract class Critter {
    private static String myPackage;
    // is population the list that they want
    private	static List<Critter> population = new java.util.ArrayList<Critter>();
    private static List<Critter> babies = new java.util.ArrayList<Critter>();

    // Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    private static java.util.Random rand = new java.util.Random();

    public enum CritterShape {
        CIRCLE,
        SQUARE,
        TRIANGLE,
        DIAMOND,
        STAR
    }

    /* the default color is white, which I hope makes critters invisible by default
     * If you change the background color of your View component, then update the default
     * color to be the same as you background
     *
     * critters must override at least one of the following three methods, it is not
     * proper for critters to remain invisible in the view
     *
     * If a critter only overrides the outline color, then it will look like a non-filled
     * shape, at least, that's the intent. You can edit these default methods however you
     * need to, but please preserve that intent as you implement them.
     */
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.WHITE;
    }

    public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
    public javafx.scene.paint.Color viewFillColor() { return viewColor(); }

    public abstract CritterShape viewShape();

    /**
     * Gets a ranom int from a range 0 to max
     * @param max the max value the random int can be
     * @return int between 0 and max
     */
    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    /**
     * Sets the seed of the Random object. mainly used for testing
     * @param new_seed number to make as the new see
     */
    public static void setSeed(long new_seed) {
        rand = new java.util.Random(new_seed);
    }


    /* a one-character long string that visually depicts your critter in the ASCII interface */
    public String toString() { return ""; }

    private int energy = 0;
    protected int getEnergy() { return energy; }


    private int x_coord;
    private int y_coord;
    private boolean moved = false;
    private boolean runningAway = false;

    /**Deducts energy from the critter it is called on.
     * Removes them immediately from the population if their energy drops below 0
     * @param loss the energy amount to deduct from the critter
     */
    private void deductEnergy(int loss){
        energy-=loss;
        if (energy <= 0){
            population.remove(this);
        }
    }

    /**
     * Walk: change coordinates if applicable and calls deductEnergy
     * @param direction direction to walk in 0-7
     */
    protected final void walk(int direction) {
        this.deductEnergy(Params.walk_energy_cost);
        int newX = x_coord;
        int newY = y_coord;
        if(!moved){
            moved = true;
            switch (direction){
                // straight right (Increase X; no change Y)
                case 0:
                    newX++;
                    newX = newX % Params.world_width;
                    break;
                // Diagonally Up to the Right (Increase X; Decrease Y)
                case 1:
                    newX++;
                    newX = newX % Params.world_width;

                    newY--;
                    if(newY < 0){
                        newY =+ Params.world_height;
                    }
                    break;
                // Straight Up (No change X; Decrease Y)
                case 2:
                    newY--;
                    if(newY < 0){
                        newY =+ Params.world_height;
                    }
                    break;
                // Diagonally left Up (Decrease X; Decrease Y)
                case 3:
                    newX--;
                    if(newX < 0){
                        newX =+ Params.world_width;
                    }

                    newY--;
                    if(newY < 0){
                        newY =+ Params.world_height;
                    }
                    break;
                // Straight Left (Decrease X; no change Y)
                case 4:
                    newX--;
                    if(newX < 0){
                        newX =+ Params.world_width;
                    }
                    break;
                // Diagonally Down Left (Decrease X; Increase Y)
                case 5:
                    newX--;
                    if(newX < 0){
                        newX =+ Params.world_width;
                    }
                    newY++;
                    newY = newY % Params.world_height;
                    break;
                // Straight Down (No change X; Increase Y)
                case 6:
                    newY++;
                    newY = newY % Params.world_height;
                    break;
                // Diagonally down left (Increase X; Increase Y)
                case 7:
                    newX++;
                    newX = newX % Params.world_width;
                    newY++;
                    newY = newY % Params.world_height;
                    break;
                // Will never be default if random number is set up properly
                default:
                    break;
            }// switch menu
            // see if critter is in a fight or not
            if(runningAway){
                if(notOccupied(newX, newY)){
                    // able to run away
                    x_coord = newX;
                    y_coord = newY;
                }
            }else {
                // moves into new position
                x_coord = newX;
                y_coord = newY;
            }
        } // if critter has not moved
    }

    /**
     * Run: changes coordinates if applicable and calls deductEnergy
     * @param direction direction specified to run in
     */
    protected final void run(int direction) {
        this.deductEnergy(Params.run_energy_cost);
        int newX = x_coord;
        int newY = y_coord;
        if(!moved){
            moved = true;
            switch (direction){
                // straight right (Increase X; no change Y)
                case 0:
                    newX+=2;
                    newX = newX % Params.world_width;
                    break;
                // Diagonally Up to the Right (Increase X; Decrease Y)
                case 1:
                    newX+=2;
                    newX = newX % Params.world_width;

                    newY-=2;
                    if(newY < 0){
                        newY =+ Params.world_height;
                    }
                    break;
                // Straight Up (No change X; Decrease Y)
                case 2:
                    newY-=2;
                    if(newY < 0){
                        newY =+ Params.world_height;
                    }
                    break;
                // Diagonally left Up (Decrease X; Decrease Y)
                case 3:
                    newX-=2;
                    if(newX < 0){
                        newX =+ Params.world_width;
                    }

                    newY-=2;
                    if(newY < 0){
                        newY =+ Params.world_height;
                    }
                    break;
                // Straight Left (Decrease X; no change Y)
                case 4:
                    newX-=2;
                    if(newX < 0){
                        newX =+ Params.world_width;
                    }
                    break;
                // Diagonally Down Left (Decrease X; Increase Y)
                case 5:
                    newX-=2;
                    if(newX < 0){
                        newX =+ Params.world_width;
                    }

                    newY+=2;
                    newY = newY % Params.world_height;
                    break;
                // Straight Down (No change X; Increase Y)
                case 6:
                    newY+=2;
                    newY = newY % Params.world_height;
                    break;
                // Diagonally down left (Increase X; Increase Y)
                case 7:
                    newX+=2;
                    newX = newX % Params.world_width;

                    newY+=2;
                    newY = newY % Params.world_height;
                    break;
                // Will never be default if random number is set up properly
                default:
                    break;
            }// switch menu

            // see if critter is in a fight or not
            if(runningAway){
                if(notOccupied(newX, newY)){
                    // able to run
                    x_coord = newX;
                    y_coord = newY;
                }
            }else {
                x_coord = newX;
                y_coord = newY;
            }
        } // if critter has not moved


    }

    /**
     * Reproduces
     * @param offspring
     * @param direction
     */
    protected final void reproduce(Critter offspring, int direction) {
        offspring.energy = this.energy/2;
        this.energy = this.energy - offspring.energy;
        offspring.x_coord = this.x_coord;
        offspring.y_coord = this.y_coord;
        // to determine the baby coordinates

        switch (direction){
            // straight right (Increase X; no change Y)
            case 0:
                offspring.x_coord++;
                offspring.x_coord = offspring.x_coord % Params.world_width;
                break;
            // Diagonally Up to the Right (Increase X; Decrease Y)
            case 1:
                offspring.x_coord++;
                offspring.x_coord = offspring.x_coord % Params.world_width;

                offspring.y_coord--;
                if(offspring.y_coord < 0){
                    offspring.y_coord =+ Params.world_height;
                }
                break;
            // Straight Up (No change X; Decrease Y)
            case 2:
                offspring.y_coord--;
                if(offspring.y_coord < 0){
                    offspring.y_coord =+ Params.world_height;
                }
                break;
            // Diagonally left Up (Decrease X; Decrease Y)
            case 3:
                offspring.x_coord--;
                if(offspring.x_coord < 0){
                    offspring.x_coord =+ Params.world_width;
                }

                offspring.y_coord--;
                if(offspring.y_coord < 0){
                    offspring.y_coord =+ Params.world_height;
                }
                break;
            // Straight Left (Decrease X; no change Y)
            case 4:
                offspring.x_coord--;
                if(offspring.x_coord < 0){
                    offspring.x_coord =+ Params.world_width;
                }
                break;
            // Diagonally Down Left (Decrease X; Increase Y)
            case 5:
                offspring.x_coord--;
                if(offspring.x_coord < 0){
                    offspring.x_coord =+ Params.world_width;
                }

                offspring.y_coord++;
                offspring.y_coord = offspring.y_coord % Params.world_height;
                break;
            // Straight Down (No change X; Increase Y)
            case 6:
                offspring.y_coord++;
                offspring.y_coord = offspring.y_coord % Params.world_height;
                break;
            // Diagonally down left (Increase X; Increase Y)
            case 7:
                offspring.x_coord++;
                offspring.x_coord = offspring.x_coord % Params.world_width;

                offspring.y_coord++;
                offspring.y_coord = offspring.y_coord % Params.world_height;
                break;
            // Will never be default if random number is set up properly
            default:
                break;
        }
        babies.add(offspring);

    }

    public abstract void doTimeStep();
    public abstract boolean fight(String oponent);

    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
     * an InvalidCritterException must be thrown.
     * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
     * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
     * an Exception.)
     * @param critter_class_name
     * @throws InvalidCritterException
     */
    public static void makeCritter(String critter_class_name) throws InvalidCritterException {
        try {
            // produces qualified name
            String qualifiedName = myPackage.toString() + "." + critter_class_name;
            //Gets the class of that name
            Class<Critter> new_class = (Class<Critter>) Class.forName(qualifiedName);
            // creates new instance
            Critter critterNew = new_class.newInstance();
            // pick coordinates to place
            critterNew.x_coord = (rand.nextInt(Params.world_width));
            critterNew.y_coord = (rand.nextInt(Params.world_height));
            critterNew.energy = Params.start_energy;
            critterNew.runningAway = false;
            critterNew.moved = false;
            // add to list
            population.add(critterNew);
        }catch (ClassNotFoundException e){
            throw new InvalidCritterException(critter_class_name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of critters of a specific type.
     * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
     * @return List of Critters.
     * @throws InvalidCritterException
     */
    public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
        List<Critter> result = new java.util.ArrayList<Critter>();
        try{
            // produces qualified name
            String qualifiedName = myPackage.toString() + "." + critter_class_name;
            Class<Critter> new_class = (Class<Critter>) Class.forName(qualifiedName);
            Critter critterNew = new_class.newInstance();
            for(Critter crit : population){
                if(critterNew.toString().equals(crit.toString())){
                    result.add(crit);
                }
            }
        }catch (Exception e){

        }
        return result;
    }

    /**
     * Prints out how many Critters of each type there are on the board.
     * @param critters List of Critters.
     */
    public static void runStats(List<Critter> critters) {
        System.out.print("" + critters.size() + " critters as follows -- ");
        java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
        for (Critter crit : critters) {
            String crit_string = crit.toString();
            Integer old_count = critter_count.get(crit_string);
            if (old_count == null) {
                critter_count.put(crit_string,  1);
            } else {
                critter_count.put(crit_string, old_count.intValue() + 1);
            }
        }
        String prefix = "";
        for (String s : critter_count.keySet()) {
            System.out.print(prefix + s + ":" + critter_count.get(s));
            prefix = ", ";
        }
        System.out.println();
    }


    /** the TestCritter class allows some critters to "cheat". If you want to
     * create tests of your Critter model, you can create subclasses of this class
     * and then use the setter functions contained here.
     *
     * NOTE: you must make sure that the setter functions work with your implementation
     * of Critter. That means, if you're recording the positions of your critters
     * using some sort of external grid or some other data structure in addition
     * to the x_coord and y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {
        /**
         * Setter method for energy
         * @param new_energy_value the new value to be stored
         */
        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        /**
         * Setter for x_coor
         * @param new_x_coord new x position to be stored
         */
        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        /**
         * setter for y coordinate
         * @param new_y_coord new y position to be stored
         */
        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        /**
         * Getter method for X coordinate
         * @return the x coordinate
         */
        protected int getX_coord() {
            return super.x_coord;
        }

        /**
         * Getter method for Y coordinate
         * @return the y coordinate
         */
        protected int getY_coord() {
            return super.y_coord;
        }

        /*
         * This method getPopulation has to be modified by you if you are not using the population
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */

        /**
         * getter method for population
         * @return the list of critters in the population collection
         */
        protected static List<Critter> getPopulation() {
            return population;
        }

        /*
         * This method getBabies has to be modified by you if you are not using the babies
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.  Babies should be added to the general population
         * at either the beginning OR the end of every timestep.
         */

        /**
         * Getter method
         * @return list of babies
         */
        protected static List<Critter> getBabies() {
            return babies;
        }
    }

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        // Complete this method.
        population.clear();
        babies.clear();
    }

    /**
     * To have an encounter between two critters
     * @param A The first Critter
     * @param B The second critter
     */
    private static void encounter(Critter A, Critter B){
        // if both want to fight
        if(A.fight(B.toString()) && B.fight(A.toString())){
            int aRoll = rand.nextInt(A.energy);
            int bRoll = rand.nextInt(B.energy);
            if(aRoll >= bRoll){
                // a wins or there's a tie
                A.energy += (B.energy/2);
                population.remove(B);
            } else if (bRoll > aRoll){
                // b wins
                B.energy += (A.energy/2);
                population.remove(A);
            }
        } else if(A.fight(B.toString())){
            // A wants to fight
            // B may or may not have ran away
            if(A.x_coord == B.x_coord && A.y_coord == B.y_coord){
                A.energy += (B.energy/2);
                population.remove(B);
            }
        } else if(B.fight(A.toString())){
            // B wants to fight
            if(A.x_coord == B.x_coord && A.y_coord == B.y_coord){
                B.energy += (A.energy/2);
                population.remove(A);
            }
        } else{
            // Neither want to fight but if in same position they do
            if(A.x_coord == B.x_coord && A.y_coord == B.y_coord){
                int aRoll = rand.nextInt(A.energy);
                int bRoll = rand.nextInt(B.energy);
                if(aRoll >= bRoll){
                    // a wins or there's a tie
                    A.energy += (B.energy/2);
                    population.remove(B);
                } else if (bRoll > aRoll){
                    // b wins
                    B.energy += (A.energy/2);
                    population.remove(A);
                }
            }
        }
    }

    /**
     * Goes through and has all the critters do their steps, checks encounters, deducts rest energy, generates algae then add babies
     */
    public static void worldTimeStep() {
        // Complete this method.
        // iterate through every creature to do time step
        for(Critter crit : new ArrayList<>(population)){
            crit.doTimeStep();
        }
        // do fights
        for (Critter outer : new ArrayList<>(population)){
            for (Critter inner : new ArrayList<>(population)){
                if(sameSpot(outer,inner) && outer!=inner){
                    outer.runningAway = true;
                    inner.runningAway = true;
                    encounter(outer, inner);
                }
            }
        }
        // update rest energy
        for(Critter crit : new ArrayList<>(population)){
            crit.deductEnergy(Params.rest_energy_cost);
        }
        // generate algae
        try {
            makeCritter("Algae");
        } catch (InvalidCritterException e) {
            e.printStackTrace();
        }
        // move babies
        population.addAll(babies);
        babies.clear();
        // Reset moved and running away back to false
        for(Critter crit:population){
            crit.moved = false;
            crit.runningAway = false;
        }
        displayWorld();
    }

    /**
     * Displays the grid with  the critters and a border
     */
    public static void displayWorld() {
        // get the arraylist of critters
        // print the top row

        // for loop for row
        for(int c = 0; c< Params.world_height; c++){
            // Get array of critters on the row
            ArrayList<Critter> sameHeight = new ArrayList<>();
            for (Critter critter : population){
                if(critter.y_coord == c){
                    sameHeight.add(critter);
                } // to get all the critters in that row
            }
            for(int r = 0; r<Params.world_width; r++){
                // check if there's a critter there
                String filler = " ";
                // method to check if theres a critter there
                for(Critter sameHeightCritters : sameHeight){
                    if(sameHeightCritters.x_coord == r){
                        filler = sameHeightCritters.toString();
                        Shape s = createShape(sameHeightCritters.viewShape());
                        s.setFill(sameHeightCritters.viewFillColor());
                        s.setStroke(sameHeightCritters.viewOutlineColor());
                        Main.gp.add(s, r, c);
                    }
                } // find if theres something
                System.out.print(filler);
            }
            // print the end of the row
            System.out.println("|");
        }
        // print the bottom row
        System.out.print("+");
        for(int i = 0; i<Params.world_width; i++){
            System.out.print("-");
        }
        System.out.println("+");
    }

    /**
     * Checks if two critters are in the same spot
     * @param A One of the critters
     * @param B The other critter
     * @return true if in the same spot, false otherwise
     */
    private static boolean sameSpot(Critter A, Critter B){
        return (A.x_coord == B.x_coord && A.y_coord == B.y_coord);
    }

    /**
     * Checks that the space a critter wants
     * @param x The x coordinate of the position being checked
     * @param y The y coordinates of the position being checked
     * @return true of the space is available, false if the space is occupied
     */
    private static boolean notOccupied(int x, int y){
        for(Critter crit : population){
            if(crit.x_coord == x && crit.y_coord == y){
                return false;
            }
        }
        return true;
    }

    private static Shape createShape(CritterShape s){
        Shape newShape;
        switch (s){
            case SQUARE:
                newShape = new Rectangle(2, 2);
                return newShape;
            case STAR:
                double cor[] = {10, 85,
                        85, 75,
                        110, 10,
                        135, 75,
                        210, 85,
                        160, 125,
                        170, 190,
                        110, 150,
                        50, 190,
                        60, 125};

                newShape = new Polygon(cor);
                return newShape;
            case CIRCLE:
                newShape = new Circle(10);
                return newShape;
            case DIAMOND:

                break;
            case TRIANGLE:
                break;
            default:
                break;
        }
        return newShape = new Rectangle(10, 10);
    }
}


