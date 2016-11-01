package BlackJack.model.rules;

import BlackJack.model.Card;
import BlackJack.model.Player;

public class Soft17Strategy implements IHitStrategy  {
	 private final int g_hitLimit = 17;
	 private boolean hasAce;

	 public boolean DoHit(Player a_dealer) {
		 
		 if(a_dealer.CalcScore() < g_hitLimit) {
			 return true;
		 }
		 
		 for(Card card : a_dealer.GetHand()) {
		       if(card.GetValue() == Card.Value.Ace) {
		    	   hasAce = true;
		       }
		    }
		 
		 return a_dealer.CalcScore() == g_hitLimit && hasAce;
		 
		 
		 
	}
}

