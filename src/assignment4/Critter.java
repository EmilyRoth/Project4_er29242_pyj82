package assignment4;
/* CRITTERS Critter.java
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
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
    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

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

    private void deductEnergy(int loss){
        energy-=loss;
        if (energy <= 0){
            System.out.println(this.toString() + " Has died");
            population.remove(this);
        }
    }

    protected final void walk(int direction) {
        System.out.println("Walk");
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
                    System.out.println("Able to run away");
                    x_coord = newX;
                    y_coord = newY;
                }
            }else {
                x_coord = newX;
                y_coord = newY;
            }
        } // if critter has not moved

    }

    protected final void run(int direction) {
        System.out.println("Run");
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
                    System.out.println("Able to run away");
                    x_coord = newX;
                    y_coord = newY;
                }
            }else {
                x_coord = newX;
                y_coord = newY;
            }
        } // if critter has not moved


    }

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

        System.out.println("EXITS");
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
            String qualifiedName = myPackage.toString() + "." + critter_class_name;
            Class<Critter> new_class = (Class<Critter>) Class.forName(qualifiedName);
            Critter critterNew = new_class.newInstance();
            // when new critter is created, does it go into population or babies?
            // ARE WE ALLOWED TO DO THIS
            critterNew.x_coord = (rand.nextInt(Params.world_width));
            critterNew.y_coord = (rand.nextInt(Params.world_height));
            critterNew.energy = Params.start_energy;
            critterNew.runningAway = false;
            critterNew.moved = false;
            population.add(critterNew);
        }catch (ClassNotFoundException e){
            //Figure out what to do here
            //throws InvalidCritterException(e);
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


    /* the TestCritter class allows some critters to "cheat". If you want to
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
        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }


        /*
         * This method getPopulation has to be modified by you if you are not using the population
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
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
        protected static List<Critter> getBabies() {
            return babies;
        }
    }

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        // Complete this method.
    }
    // TODO: MAKE THE RUNNING AWAY VALUE TRUE IF RUNNING AWAY
    private static void encounter(Critter A, Critter B){
        System.out.println("Encounter");
        // if both want to fight
        if(A.fight(B.toString()) && B.fight(A.toString())){
            int aRoll = rand.nextInt(A.energy);
            int bRoll = rand.nextInt(B.energy);
            if(aRoll >= bRoll){
                System.out.println("A beat B");
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
                    System.out.println("wtf");
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
        Algae newAlgae = new Algae();
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
    }

    public static void displayWorld() {

        // print the top row
        System.out.print("+");
        for(int i = 0; i<Params.world_width; i++){
            System.out.print("-");
        }
        System.out.println("+");

        // for loop for row
        for(int c = 0; c< Params.world_height; c++){
            //print the border
            System.out.print("|");
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
                    }
                } // find if theres sometthing

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

    private static boolean sameSpot(Critter A, Critter B){
        return (A.x_coord == B.x_coord && A.y_coord == B.y_coord);
    }

    private static boolean notOccupied(int x, int y){
        for(Critter crit : population){
            if(crit.x_coord == x && crit.y_coord == y){
                return false;
            }
        }
        return true;
    }


    /**
     * TODO: Make critter method
     */
}


