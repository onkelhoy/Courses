package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

/**
 * Created by henry on 2016-10-26.
 */
public class PlayerAdvantegeWinStrategy implements IWinStrategy {
    @Override
    public boolean DealerWin(Dealer a_dealer, Player a_player) {
        return a_dealer.CalcScore() > a_player.CalcScore(); //dealer wins only if grater score then player
    }
}
