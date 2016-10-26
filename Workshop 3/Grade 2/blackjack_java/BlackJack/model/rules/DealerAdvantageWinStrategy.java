package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

/**
 * Created by henry on 2016-10-26.
 */
public class DealerAdvantageWinStrategy implements IWinStrategy {

    @Override
    public boolean DealerWin(Dealer a_dealer, Player a_player) {
        return a_dealer.CalcScore() >= a_player.CalcScore(); //dealer wins if has grater or equal score compare to player
    }
}
