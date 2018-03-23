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
/**Eric will always move diagonally, except for the first step where he can only move down or to the sides.
 *Eric will always fight
 * Will rarely reproduce
 */
public class Eric extends Critter {
    /**
     * To string method to get a $
     * @return $
     */
    @Override
    public String toString() { return "$"; }
    private int left =0;
    private int right = 0;
    private int dir;

    /**
     * Constructor
     */
    public Eric() {
        dir = Critter.getRandomInt(5);
    }

    /**
     * Custom fight; eric always wants to fight
     * @param not_used string of critter that eric wants to fight
     * @return True
     */
    public boolean fight(String not_used) {
        return true;
    }

    /**
     * Custom Time Step which runs then checks reproduce then changes direction
     */
    @Override
    public void doTimeStep() {
        //make movement
        run(dir);
        if (getEnergy() > Params.min_reproduce_energy*5) {
             Eric child = new Eric();
            reproduce(child, Critter.getRandomInt(8));
        }
        //pick new direction.
        //if already even number will be odd number
        if(dir%2 ==0){
            dir = (Critter.getRandomInt(4)*2)%8;
        }
        //or diagonally, odd number
        else{
            dir = Critter.getRandomInt(4)*2 +1;
        }
        if(dir<2 || dir > 6){
            right++;
        }
        else {
            left++;
        }
    }

    /**
     * Stats showing the steps all the erics have taken to the left and right
     * @param erics list of all instances of eric
     */
    public static void runStats(java.util.List<Critter> erics) {
        int totalRights =0;
        int totallefts =0;
        for(Object obj : erics){
            Eric err = (Eric) obj;
            totallefts+=err.left;
            totalRights+= err.right;
        }
        System.out.println("total steps towards left"+ totallefts);
        System.out.println("total steps towars right" + totalRights);
        System.out.println();
    }
}
