package model;

import model.rules.*;
import model.rules.IHitStrategy;
import model.rules.INewGameStrategy;
import model.rules.RulesFactory;

public class Dealer extends Player {

    private Deck m_deck;
    private INewGameStrategy m_newGameRule;
    private IHitStrategy m_hitRule;
    private IHitStrategy m_soft17Rule;
    private WinStrategy m_winStrategy;


    public Dealer(RulesFactory a_rulesFactory) {

        m_newGameRule = a_rulesFactory.GetNewGameRule();
        m_hitRule = a_rulesFactory.GetHitRule();
        m_soft17Rule= a_rulesFactory.GetSoft17Rule();
        m_winStrategy = a_rulesFactory.GetNewPlayerWinStrategy();






    
    /*for(Card c : m_deck.GetCards()) {
      c.Show(true);
      System.out.println("" + c.GetValue() + " of " + c.GetColor());
    }    */
    }


    public boolean NewGame(Player a_player) {
        if (m_deck == null || IsGameOver()) {
            m_deck = new Deck();
            ClearHand();
            a_player.ClearHand();
            return m_newGameRule.NewGame(m_deck, this, a_player);
        }
        return false;
    }

    public boolean Hit(Player a_player) {
        if (m_deck != null && a_player.CalcScore() < g_maxScore && !IsGameOver()) {
           dealCardFromDeck(a_player);

            return true;
        }
        return false;
    }

    public boolean IsDealerWinner(Player a_player) {
       return m_winStrategy.WinStrategy(this,a_player);
    }

    public boolean IsGameOver() {
        if (m_deck != null && m_hitRule.DoHit(this) != true) {
            return true;
        }
        return false;
    }

    public boolean stand() {
        if (m_deck != null) {
            ShowHand();
            while (m_hitRule.DoHit(this)) {
                m_hitRule.DoHit(this);
               dealCardFromDeck(this);
            }
            return true;
        }

        return false;
    }


    public void dealCardFromDeck(Player player){
        Card card = m_deck.GetCard();
        card.Show(true);
        player.DealCard(card);

    }

    public void dealCardFromDeck(Player player, boolean show){
        Card card = m_deck.GetCard();
        card.Show(show);
        player.DealCard(card);


    }



}