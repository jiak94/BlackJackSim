package BlackJackSim.View;

import BlackJackSim.Main;
import BlackJackSim.Model.Dealer;
import BlackJackSim.Model.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Controller {
    @FXML
    private Label betLabel;
    @FXML
    private Label roundLabel;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label insuranceLabel;
    @FXML
    private Label doubleLabel;
    @FXML
    private Label dlPoints;
    @FXML
    private Label plPoints;
    @FXML
    private Button startSim;
    @FXML
    private Button playOneRound;
    @FXML
    private Button simBroke;
    @FXML
    private ImageView plc1;
    @FXML
    private ImageView plc2;
    @FXML
    private ImageView plc3;
    @FXML
    private ImageView plc4;
    @FXML
    private ImageView plc5;
    @FXML
    private ImageView dlc1;
    @FXML
    private ImageView dlc2;
    @FXML
    private ImageView dlc3;
    @FXML
    private ImageView dlc4;
    @FXML
    private ImageView dlc5;
    @FXML
    private TextField roundInput;

    private Main main;

    private double playerBet;
    private Random rand = new Random();
    private boolean insure = false;
    private boolean betDouble = false;
    private double insurance = 0;
    private boolean plHit = true;
    private boolean dlHit = true;
    private int round = 0;

    private Player pl;
    private Dealer dl;


    public Controller() {
    }

    @FXML
    private void initialize() {
        pl = new Player(1000);
        dl = new Dealer();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private void setRound(int round) {
        roundLabel.setText(Integer.toString(round));
    }

    private void setCard(ImageView cardSlot, int card) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int flower = rand.nextInt(4);
//
//                String imgName = "file:res/" + Integer.toString(flower) + Integer.toString(card) + ".png";
//
//                cardSlot.setImage(new Image(imgName));
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        int flower = rand.nextInt(4);

        String imgName = "file:res/" + Integer.toString(flower) + Integer.toString(card) + ".png";

        cardSlot.setImage(new Image(imgName));
    }

    private void setBudgetLabel(double budget) {
        budgetLabel.setText(Double.toString(budget));
    }

    private void setBetLabel(int bet) {
        betLabel.setText(Integer.toString(bet));
    }

    private void setBack(ImageView cardSlot) {
        cardSlot.setImage(new Image("file:res/back.png"));
    }

    private void setInsureLabel(boolean insure) {

        insuranceLabel.setText(Boolean.toString(insure));
    }

    private void setDoubleLabel(boolean betDouble) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        doubleLabel.setText(Boolean.toString(betDouble));
                    }
                });
            }
        }).start();
    }

    private void plUpdatePoints(int points) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                plPoints.setText(Integer.toString(points));
            }
        });
    }
    private void dlUpdatePoints(int points) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dlPoints.setText(Integer.toString(points));
            }
        });
    }

    private int points(int[] deck, int cardNum) {
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

    private void resetLabel() {
        setBudgetLabel(pl.getBudget());
        insurance = 0;
        insure = false;
        betDouble = false;
        setBetLabel(0);
        setRound(round);
        setInsureLabel(insure);
        setDoubleLabel(betDouble);
    }

    @FXML
    public void playToBroke() {
        resetCardSlots();
        resetLabel();
        while (pl.getBudget() >= 20) {
            round++;
            resetCardSlots();

            playOneRound();
        }
    }

    @FXML
    public void startSim() {
        resetCardSlots();
        resetLabel();
        round = 0;
        int playRound = 100;
        pl.setBudget(1000);
        if (roundInput.getText() != null && !roundInput.getText().equals("")) {
            playRound = Integer.parseInt(roundInput.getText());
        }

        while (pl.getBudget() >= 20 && round < playRound) {
            round++;
            resetCardSlots();


            sleep();

            playOneRound();

        }

    }

    @FXML
    public void simOneRound() {
        round++;
        resetLabel();
        resetCardSlots();
        playOneRound();
    }

    private void playOneRound() {
        if (pl.getBudget() >= 20) {
            //player bet
            pl.bet(20);
            playerBet = 20;
            setBetLabel(20);

            sleep();

            //add card to dealer and player, face up
            dl.addToDeck(randCard());
            pl.addToDeck(randCard());

            dlUpdatePoints(points(dl.getCard(), dl.getCardNum()));
            plUpdatePoints(points(pl.getCard(), pl.getCardNum()));


            setCard(dlc1, dl.getCard()[0]);
            setCard(plc1, pl.getCard()[0]);

            plUpdatePoints(points(pl.getCard(), pl.getCardNum()));


            sleep();

            //add card to dealer face down
            dl.addToDeck(randCard());
            setBack(dlc2);

            //add card to player face up
            pl.addToDeck(randCard());
            setCard(plc2, pl.getCard()[1]);

            if (dl.getCard()[0] >= 10 || dl.getCard()[0] == 1) {
                insure = pl.insure();
                setInsureLabel(insure);
                //if player purchase insurance
                if (insure) {
                    insurance = playerBet / 2;
                    pl.bet(insurance);
                    setBudgetLabel(pl.getBudget());


                    //flip dealer second card
                    setCard(dlc2, dl.getCard()[1]);
                    dlUpdatePoints(points(dl.getCard(), dl.getCardNum()));

                    if (points(dl.getCard(), dl.getCardNum()) == 21) {
                        pl.calculate(insurance * 2);
                        dl.calculate(0);
                    } else {
                        pl.calculate(0);
                        dl.calculate(0);
                    }

                    return;
                }
            }


            //player actions
            plHit = pl.hit(dl.getCard()[0]);

            while (plHit) {
                int card = randCard();
                pl.addToDeck(card);
                plUpdatePoints(points(pl.getCard(), pl.getCardNum()));

                int carNum = pl.getCardNum();
                if (carNum == 3) {
                    setCard(plc3, pl.getCard()[carNum - 1]);
                } else if (carNum == 4) {
                    setCard(plc4, pl.getCard()[carNum - 1]);
                } else if (carNum == 5) {
                    setCard(plc5, pl.getCard()[carNum - 1]);
                }

                plHit = pl.hit(dl.getCard()[0]);
            }

            plUpdatePoints(points(pl.getCard(), pl.getCardNum()));

            if (points(pl.getCard(), pl.getCardNum()) == 21) {
                dl.calculate(0);
                pl.calculate(playerBet * 3);

                return;
            }

            if (points(pl.getCard(), pl.getCardNum()) > 21) {
                dl.calculate(0);
                pl.calculate(0);

                return;
            }


            //before dealer action, flip the section card
            setCard(dlc2, dl.getCard()[1]);
            dlUpdatePoints(points(dl.getCard(), dl.getCardNum()));

            //dealer actions
            dlHit = dl.hit(0);

            while (dlHit) {
                int card = randCard();
                dl.addToDeck(card);
                dlUpdatePoints(points(dl.getCard(), dl.getCardNum()));

                int cardNum = dl.getCardNum();
                if (cardNum == 3) {
                    setCard(dlc3, dl.getCard()[cardNum - 1]);
                } else if (cardNum == 4) {
                    setCard(dlc4, dl.getCard()[cardNum - 1]);
                } else if (cardNum == 5) {
                    setCard(dlc5, dl.getCard()[cardNum - 1]);
                }

                dlHit = dl.hit(0);
            }

            dlUpdatePoints(points(dl.getCard(), dl.getCardNum()));

            if (points(dl.getCard(), dl.getCardNum()) == 21) {
                pl.calculate(0);
                dl.calculate(0);

                return;
            }

            if (points(dl.getCard(), dl.getCardNum()) > 21) {
                dl.calculate(0);
                pl.calculate(playerBet * 2);

                return;
            }

            if (points(dl.getCard(), dl.getCardNum()) == points(pl.getCard(), pl.getCardNum())) {
                dl.calculate(0);
                pl.calculate(playerBet);

                return;
            }

            if (points(dl.getCard(), dl.getCardNum()) < points(pl.getCard(), pl.getCardNum())) {
                dl.calculate(0);
                pl.calculate(playerBet * 2);

                return;
            }

            if (points(dl.getCard(), dl.getCardNum()) > points(pl.getCard(), pl.getCardNum())) {
                dl.calculate(0);
                pl.calculate(0);

            }
        }
    }

    private void sleep() {

    }

    private void resetCardSlots() {
        plc1.setImage(null);
        plc2.setImage(null);
        plc3.setImage(null);
        plc4.setImage(null);
        plc5.setImage(null);

        dlc1.setImage(null);
        dlc2.setImage(null);
        dlc3.setImage(null);
        dlc4.setImage(null);
        dlc5.setImage(null);
    }

    private int randCard() {
        return rand.nextInt(13) + 1;
    }
}
