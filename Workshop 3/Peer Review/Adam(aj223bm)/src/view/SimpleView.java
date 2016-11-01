package view;

import model.Card;
import model.Game;

public class SimpleView implements IView {

    public void DisplayWelcomeMessage() {
        for (int i = 0; i < 50; i++) {
            System.out.print("\n");
        }
        ;
        System.out.println("Hello Black Jack World");
        System.out.println("Type 'p' to Play, 'h' to Hit, 's' to Stand or 'q' to Quit\n");
    }

    public int GetInput() {
        try {
            int c = System.in.read();
            while (c == '\r' || c == '\n') {
                c = System.in.read();
            }
            return c;
        } catch (java.io.IOException e) {
            System.out.println("" + e);
            return 0;
        }

    }

    public void DisplayCard(Card a_card) {
        System.out.println("" + a_card.GetValue() + " of " + a_card.GetColor());
    }

    public void DisplayPlayerHand(Iterable<Card> a_hand, int a_score) {
        DisplayHand("Player", a_hand, a_score);
    }

    public void DisplayDealerHand(Iterable<Card> a_hand, int a_score) {

        DisplayHand("Dealer", a_hand, a_score);
    }

    private void DisplayHand(String a_name, Iterable<Card> a_hand, int a_score) {
        System.out.println(a_name + " Has: ");
        for (Card c : a_hand) {

            DisplayCard(c);
        }
        System.out.println("Score: " + a_score);
        System.out.println("");
    }

    public void DisplayGameOver(boolean a_dealerIsWinner) {
        System.out.println("GameOver: ");
        if (a_dealerIsWinner) {
            System.out.println("Dealer Won!");
        } else {
            System.out.println("You Won!");
        }


    }

    @Override
    public void Pause() {
        System.out.print("Dealing.");
        int i =0;
        try {
            Thread.sleep(800);
            while(i<1500){
                System.out.println(" ");
                i++;
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    }




