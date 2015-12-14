package BlackJackSim.Model;

/**
 * Created by Jiakuan on 12/13/15.
 */
public class Strategy implements Gamer {
    private int rounds = 100;
    private int [][] strategy = new int[11][17];


    @Override
    public void addToDeck(int card) {

    }

    @Override
    public void bet(double amount) {

    }

    @Override
    public double getBudget() {
        return 0;
    }

    @Override
    public boolean insure() {
        return false;
    }

    @Override
    public void calculate(double amount) {

    }

    @Override
    public boolean betDouble(int card) {
        return false;
    }

    @Override
    public int points() {
        return 0;
    }

    @Override
    public boolean hit(int card) {
        return false;
    }

    @Override
    public void insurance(double amount) {

    }

    @Override
    public int[] getCard() {
        return new int[0];
    }

    public static void setRound(int rounds) {
        this.rounds = rounds;
    }
}
