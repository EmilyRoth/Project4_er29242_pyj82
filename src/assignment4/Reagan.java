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
/**Reagan's special characteristic is that it has a life counter and makes separate choices depending on how old
 * it is. Reagans will only reproduce after 25 and their sysmbol will change from 'r' to 'R'
 * Will run or walk depending on age.
 */
public class Reagan extends Critter {

    private int life = 0;
    private int dir;

    /**
     * Constructor
     */
    public Reagan() {
        dir = Critter.getRandomInt(8);
    }

    /**
     * Reagans custom fight
     * @param not_used The name of the critter that Reagan wants to fight
     * @return true if has energy above 30 and younger than 25 steps. False otherwise
     */
    public boolean fight(String not_used) {
        if(getEnergy()>50){
            return true;
        }
        else if(getEnergy() >30 && life <25){
            return true;
        }
        else if(getEnergy() >20) {
            run(dir);
            return false;
        }
        else{
            walk(dir);
            return false;
        }
    }

    /**
     * Overridden toString for Reagan
     * @return R if older than 25 and r if younger than 25
     */
    @Override
    public String toString() {
        if(life >25){
            return "R";
        }
        else {
            return "r";
        }
    }

    /**
     * Overridden method to do timestep for Reagan
     * Runs when young, walks when old
     * Reproduces if old enough and has min energy
     */
    @Override
    public void doTimeStep() {
        //make movement
        if(life<25){
            run(dir);
        }
        else {
            walk(dir);
        }
        if (getEnergy() > Params.min_reproduce_energy && life>25) {
            Reagan child = new Reagan();
            reproduce(child, dir);

        }
        //add to life counter
        life++;

        //pick new direction.
        //if already even number will continue to be even number
        dir= (dir+3)%8;
    }

    /**
     * Run stats for Reagan which shows how many children, how many adults, lifespan, and avg span
     * @param reagans arraylist of all instances of reagan
     */
    public static void runStats(java.util.List<Critter> reagans) {
        int lifeTotal =0;
        int adults =0;
        int children =0;
        int lifeAvg =0;
        for (Object obj : reagans) {
            Reagan reg = (Reagan) obj;
            lifeTotal+= reg.life;
            if( reg.life >25){
              adults++;
            }
            else {
                children++;
            }
        }
        lifeAvg= lifeTotal/reagans.size();
        System.out.println("Total Reagans: " + reagans.size());
        System.out.println("Avg Life Span" + lifeAvg);
        System.out.println("Number of Adults: " + adults );
        System.out.println("Number of Children: "+ children);
    }

    @Override
    public CritterShape viewShape() { return CritterShape.TRIANGLE; }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLUE; }

    @Override
    public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.BLUE; }
}
