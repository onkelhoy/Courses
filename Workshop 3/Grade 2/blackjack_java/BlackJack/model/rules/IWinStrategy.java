package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

/**
 * Created by henry on 2016-10-26.
 */
public interface IWinStrategy {
    public boolean DealerWin(Dealer a_dealer, Player a_player);
}
