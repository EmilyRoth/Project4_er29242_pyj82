package assignment4;

/* This Critter randomly chooses a direction at its creation then only ever goes diagonally or straight(North, South,
 *  East, West) depending
 * the direction it originally choose.
 * Eugene runs.
 */
public class Eugene extends Critter {

    @Override
    public String toString() { return "E"; }

    private int dir;

    public Eugene() {
        dir = Critter.getRandomInt(8);
    }

    public boolean fight(String not_used) {
        if(dir%2 == 0)
            return true;
        else {
            run(dir);
            return false;
        }
    }

    @Override
    public void doTimeStep() {
        //make movement
        run(dir);

        if (getEnergy() > 150 && Critter.getRandomInt(10)%2 == 0) {
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
}
