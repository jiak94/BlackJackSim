package BlackJackSim.Model;

import java.util.Random;

/**
 * Created by Jiakuan on 12/10/15.
 */
public class Player implements Gamer{
    private double budget = 0;
    private double betAmound = 0;

    private int cardNum = 0;
    private int[] deck;
    private Random rand = new Random();

    public Player(double budget) {
        this.budget = budget;
        this.deck = new int[5];
    }

    public void addToDeck(int card) {
        if (cardNum < 5) {
            deck[cardNum] = card;
            cardNum++;
        }
    }

    public void bet(double amount) {
        betAmound += amount;
        budget -= amount;
    }

    public double getBudget() {
        return budget;
    }

    public boolean insure() {
        return rand.nextBoolean();
    }

    public void calculate(double amount) {
        budget += amount;

        cardNum = 0;
        betAmound = 0;

        deck = null;
        deck = new int[5];
    }

    public boolean betDouble(int card) {
        int mypoints = points();
        if (mypoints == 9 && (card >=3 && card<=6)) {
            return true;
        }
        if (mypoints == 10 && (card >= 2 && card <= 9)) {
            return true;
        }
        if (mypoints == 11 && (card >=2 && card <=10)) {
            return true;
        }
        return false;
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

    public boolean hit(int card) {
        int mypoints = points();
        if (cardNum < 5) {
            if (mypoints <= 11) {
                return true;
            }
            if (mypoints >= 17) {
                return false;
            }

            if (mypoints == 9 && (card == 2 || card >= 7 && card <= 10)) {
                return true;
            }

            if (mypoints == 10 && (card == 10 || card == 11)) {
                return true;
            }

            if ((mypoints >= 12 && mypoints < 17) && card >= 7) {
                return true;
            }
            if (mypoints == 12 && (card == 2 || card == 3)) {
                return true;
            }
        }

        return false;

    }

    public void insurance(double amount) {
        budget -= amount;
    }

    public int[] getCard() {
        return deck;
    }

    public int getCardNum() {
        return cardNum;
    }

    @Override
    public double getBetAmount() {
        return betAmound;
    }
}
