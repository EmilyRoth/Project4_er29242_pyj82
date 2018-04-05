package assignment4;


import javafx.scene.paint.Color;

/**Elise will run or walk depending on Energy
 *Never fights, will go fast depending on energy. Always escapes.
 */
public class Hello extends Critter {
    /**
     * To string to return
     * @return string
     */
    @Override
    public String toString() { return "&"; }
    private int runs =0;
    private int walks =0;
    private int stay =0;
    private int dir;

    /**
     * Constructor
     */
    public Hello() {
        dir = Critter.getRandomInt(5);
    }

    /**
     * Never fights; only attempts to run away
     * @param not_used the string of the critter elise has an encounter with
     * @return False no matter what
     */
    public boolean fight(String not_used) {
        if(getEnergy() >150){
            run(dir);
        }
        else{
            walk(dir);
        }
        return false;
    }

    /**
     * Custom timeStep method for Elise
     * Moves only if has the energy required
     */
    @Override
    public void doTimeStep() {
        //make movement
        if(getEnergy() > 200){
            run(dir);
            runs++;
        }
        else if(getEnergy() > 100){
            walk(dir);
            walks++;
        }
        else {
            //she stays in place
            stay++;
        }
        // to reproduce
        if (getEnergy() > Params.min_reproduce_energy) {
            Elise child = new Elise();
            reproduce(child, Critter.getRandomInt(8));
        }
        //pick new direction.
        dir = Craig.getRandomInt(8);
    }

    /**
     * Shows how many times elises have decided to walk, run and stay
     * @param elises List of all alive instances of elises
     */
    public static String runStats(java.util.List<Critter> elises) {
        String str;
        int totalRunss =0;
        int totalwalkss =0;
        int totalStays=0;
        for(Object obj : elises){
            Hello eli = (Hello) obj;
            totalRunss+=eli.runs;
            totalwalkss+= eli.walks;
            totalStays+= eli.stay;
        }
        str = "total times has run: "+ totalRunss + "\ntotal times has walked: " + totalwalkss + "\ntotal times has stayed: "+ totalStays;
        return str;
    }

    @Override
    public CritterShape viewShape() { return CritterShape.SQUARE; }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() { return Color.VIOLET; }

    @Override
    public javafx.scene.paint.Color viewFillColor() { return Color.VIOLET; }
}
