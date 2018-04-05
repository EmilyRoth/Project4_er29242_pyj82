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

import javafx.scene.paint.Color;

/** This Critter randomly chooses a direction at its creation then only ever goes diagonally or straight(North, South,
 *  East, West) depending
 * the direction it originally choose.
 * Eugene runs.
 */
public class Eugene extends Critter {

    /**
     * To string to override the one in critter
     * @return E
     */
    @Override
    public String toString() { return "E"; }

    private int dir;

    /**
     * Constructor
     */
    public Eugene() {
        dir = Critter.getRandomInt(8);
    }

    /**
     * Eugene custom fight method
     * @param not_used the string name of the critter eugene wants to fight
     * @return true if direction is even, false if direction is odd
     */
    public boolean fight(String not_used) {
        if(dir%2 == 0)
            return true;
        else {
            run(dir);
            return false;
        }
    }

    /**
     * Custom do time step method
     * Toggles between even and odd directions
     */
    @Override
    public void doTimeStep() {
        //make movement
        run(dir);
        if (getEnergy() > Params.min_reproduce_energy && Critter.getRandomInt(10)%2 == 0) {
            Eugene child = new Eugene();
            reproduce(child, Critter.getRandomInt(8));

        }
        //pick new direction.
        //if already even number will continue to be even number
        if(dir%2 ==0){
            dir = Critter.getRandomInt(4)*2;
        }
        //or diagonally, odd number
        else{
            dir = Critter.getRandomInt(4)*2 +1;
        }
    }

    /**
     * Shows the number of eugenes then the ones going in a line and the ones going diagonally
     * @param eugenes list
     */
    public static void runStats(java.util.List<Critter> eugenes) {
        int line =0;
        int diagonal = 0;
        for (Object obj : eugenes) {
            Eugene eug = (Eugene) obj;
            if (eug.dir%2 == 0 ){
                line++;
            }
            else
                diagonal++;
        }
        System.out.println("Total Eugenes: " + eugenes.size());
        System.out.println("Total going in Line: " +line);
        System.out.println("Total going Diagonally: "+ diagonal);
    }

    @Override
    public CritterShape viewShape() { return CritterShape.CIRCLE; }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() { return Color.BROWN; }

    @Override
    public javafx.scene.paint.Color viewFillColor() { return Color.BROWN; }
}
