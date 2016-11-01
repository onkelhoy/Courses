package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

public interface IWinStrategy {
    boolean dealerWin(Player player, Dealer dealer);
}