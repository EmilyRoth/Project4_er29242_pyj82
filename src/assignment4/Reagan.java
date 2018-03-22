package assignment4;
/*Reagan's special characteristic is that it has a life counter and makes separate choices depending on how old
 * it is. Reagans will only reproduce after 25 and their sysmbol will change from 'r' to 'R'
 * Will run or walk depending on age.
 */
public class Reagan extends Critter {

    private int life = 0;
    private int dir;

    public Reagan() {
        dir = Critter.getRandomInt(8);
    }

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
    //
    @Override
    public String toString() {
        if(life >25){
            return "R";
        }
        else {
            return "r";
        }
    }

    @Override
    public void doTimeStep() {
        //make movement
        if(life<25){
            run(dir);
        }
        else {
            walk(dir);
        }
        if (getEnergy() > 100 && life>25) {
            Reagan child = new Reagan();
            reproduce(child, dir);

        }
        //add to life counter
        life++;

        //pick new direction.
        //if already even number will continue to be even number
        dir= (dir+3)%8;
    }

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
}
