package assignment4;

/*Eric will always move diagonally, except for the first step where he can only move down or to the sides.
 *Eric will always fight
 */
public class Eric extends Critter {
    @Override
    public String toString() { return "$"; }
    private int left =0;
    private int right = 0;
    private int dir;

    public Eric() {
        dir = Critter.getRandomInt(5);
    }

    public boolean fight(String not_used) {
        return true;
    }

    @Override
    public void doTimeStep() {
        //make movement
        run(dir);

        if (getEnergy() > 2000) {
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
