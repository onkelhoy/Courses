package BlackJack.model.rules;

import BlackJack.model.Card;
import BlackJack.model.Deck;
import BlackJack.model.Player;

/**
 * Created by henry on 2016-10-26.
 */
abstract class CardHandle {
    protected void GetShowDeal(Deck a_deck, Player a_player, Boolean a_show){
        Card c = a_deck.GetCard();
        c.Show(a_show);
        a_player.DealCard(c);
    }
}
