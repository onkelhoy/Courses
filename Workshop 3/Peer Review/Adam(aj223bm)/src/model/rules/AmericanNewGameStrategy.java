package model.rules;

import model.Deck;
import model.Dealer;
import model.Player;
import model.Card;

class AmericanNewGameStrategy implements INewGameStrategy {

    public boolean NewGame(Deck a_deck, Dealer a_dealer, Player a_player) {


        a_dealer.dealCardFromDeck(a_player);

        a_dealer.dealCardFromDeck(a_dealer);

        a_dealer.dealCardFromDeck(a_player);

        a_dealer.dealCardFromDeck(a_dealer,false);


        return true;
    }
}