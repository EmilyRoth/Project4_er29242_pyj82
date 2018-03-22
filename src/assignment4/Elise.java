package assignment4;
/*Elise will run or walk depending on Energy
 *
 */
public class Elise extends Critter {
    @Override
    public String toString() { return "&"; }
    private int runs =0;
    private int walks =0;
    private int stay =0;
    private int dir;

    public Elise() {
        dir = Critter.getRandomInt(5);
    }

    public boolean fight(String not_used) {
        if(getEnergy() >150){
            run(dir);
        }
        else{
            walk(dir);
        }
        return false;
    }

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

        if (getEnergy() > 200) {
            Elise child = new Elise();
            reproduce(child, Critter.getRandomInt(8));
        }


        //pick new direction.
        dir = Craig.getRandomInt(8);
    }

    public static void runStats(java.util.List<Critter> elises) {
        int totalRunss =0;
        int totalwalkss =0;
        int totalStays=0;
        for(Object obj : elises){
            Elise eli = (Elise) obj;
            totalRunss+=eli.runs;
            totalwalkss+= eli.walks;
            totalStays+= eli.stay;
        }
        System.out.println("total steps towards left"+ totalRunss);
        System.out.println("total steps towars right" + totalwalkss);
        System.out.println("total steps towards left"+ totalStays);
        System.out.println();
    }
}
