package model.rules;

import model.Card;
import model.Player;

/**
 * Created by MohamedOsman on 2016-10-24.
 */
public class Soft17Strategy implements  IHitStrategy {
    private final int g_hitLimit = 17;
    private boolean oneAce = false;

    public boolean DoHit(Player a_dealer) {
        if (a_dealer.CalcScore() == g_hitLimit) {
            for (Card c : a_dealer.GetHand()) {
                if (c.GetValue().equals(Card.Value.Ace)) {


                    return true;
                }

            }
        }
        return a_dealer.CalcScore() < g_hitLimit;
    }
}



