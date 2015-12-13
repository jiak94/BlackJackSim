package BlackJackSim.Model;

/**
 * Created by Jiakuan on 12/10/15.
 */
public interface Gamer {
    public void addToDeck(int card);
    public void bet(double amount);
    public double getBudget();
    public boolean insure();
    public void calculate(double amount);
    public boolean betDouble(int card);
    public int points();
    public boolean hit(int card);
    public void insurance(double amount);
    public int[] getCard();
}
