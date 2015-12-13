package BlackJackSim.Model;

/**
 * Created by Jiakuan on 12/10/15.
 */
public class Dealer implements Gamer{
    private int cardNum = 0;
    private int[] deck;

    public Dealer() {
        deck = new int[5];
    }

    public int points() {
        int total = 0;
        int aces = 0;
        for (int i = 0; i < cardNum; i++) {
            if (deck[i] == 1) {
                aces++;
            }
            else if (deck[i] >= 10) {
                total += 10;
            }
            else {
                total += deck[i];
            }
        }

        while (aces > 0) {
            if (total + 11 > 21) {
                total += 1;
            }
            else {
                total += 11;
            }
            aces--;
        }
        return total;
    }

    public double getBudget(){
        return 0;
    }

    public void insurance(double amount){}

    public boolean betDouble(int card){
        return false;
    }

    public void bet(double amount){}

    public boolean insure(){
        return false;
    }

    public void calculate(double temp) {
        deck = null;
        deck = new int[5];
        cardNum = 0;
    }

    public void addToDeck(int card) {
        if (cardNum < 5) {
            deck[cardNum] = card;
            cardNum++;
        }
    }

    public boolean hit(int card) {
        if (points() <= 16 && cardNum < 5) {
            return true;
        }

        return false;
    }

    public int[] getCard() {
        return deck;
    }

    public int getCardNum() {
        return cardNum;
    }
}
