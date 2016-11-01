package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

public class FavorDealerStrategy implements IWinStrategy {
	 private int g_maxScore;
		
		public boolean dealerWin(Player player, Dealer dealer) {
			if(player.CalcScore() > g_maxScore) {
				return true;
			} else if(dealer.CalcScore() == player.CalcScore()) {
				return true;
			} else if(dealer.CalcScore() > g_maxScore) {
				return false;
			}
			
			return  dealer.CalcScore() > player.CalcScore();
		}
}
