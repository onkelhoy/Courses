package BlackJack.model.rules;

import BlackJack.model.Deck;
import BlackJack.model.Dealer;
import BlackJack.model.Player;
import BlackJack.model.Card;  

class InternationalNewGameStrategy extends CardHandle implements INewGameStrategy {

  public boolean NewGame(Deck a_deck, Dealer a_dealer, Player a_player) {

    GetShowDeal(a_deck, a_player, true);
    GetShowDeal(a_deck, a_dealer, true);
    GetShowDeal(a_deck, a_player, true);

    /*
    Card c;
    c = a_deck.GetCard();
    c.Show(true);
    a_player.DealCard(c);
  
    c = a_deck.GetCard();
    c.Show(true);
    a_dealer.DealCard(c);
  
    c = a_deck.GetCard();
    c.Show(true);
    a_player.DealCard(c);
    */
  
    return true;
  }
}