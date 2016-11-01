package BlackJack.model.rules;

import BlackJack.model.Card;
import BlackJack.model.Player;

public class Soft17HitStrategy implements IHitStrategy {
    private final int g_hitLimit = 17;

    public boolean DoHit(Player a_dealer) {
        int score = a_dealer.CalcScore();
        if(score == g_hitLimit){ //score equals 17, check for ace and 6..
            Iterable<Card> list = a_dealer.GetHand();

            for(Card c : list){
                if(c.GetValue() == Card.Value.Ace) return true; //ace means 11 score that leaves 6 left..
            }
            return false;
        }
        else return score < g_hitLimit;
    }
}
