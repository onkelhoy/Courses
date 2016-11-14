package BlackJack.model;

import BlackJack.model.rules.*;

public class Dealer extends Player implements Observer {
  //implement observer and update when getting a new card
  private Deck m_deck;
  private INewGameStrategy m_newGameRule;
  private IHitStrategy m_hitRule;
  private IWinStrategy m_winRule;
  private Subject subject;

  public Dealer(Subject subject, RulesFactory a_rulesFactory) {
  
    m_newGameRule = a_rulesFactory.GetNewGameRule();
    m_hitRule = a_rulesFactory.GetHitRule();
    m_winRule = a_rulesFactory.GetWinRule();
    this.subject = subject;

    subject.subscribe(this);
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

      a_player.GetShowDeal(m_deck, true);
      
      return true;
    }
    return false;
  }

  public boolean IsDealerWinner(Player a_player) {
    if (a_player.CalcScore() > g_maxScore) {
      return true;
    } else if (CalcScore() > g_maxScore) {
      return false;
    }
    return m_winRule.DealerWin(this, a_player);
  }

  public boolean IsGameOver() {
    if (m_deck != null && m_hitRule.DoHit(this) != true) {
        return true;
    }
    return false;
  }

  public boolean Stand() { //subject in parameter
    if(m_deck != null){
      ShowHand();

      for(Card c : GetHand()){
        c.Show(true);
      }
      subject.setState(0);
      subject.Notify(); //it will call the playGame notify
    }
    return true;
  }

  @Override
  public void Update() {
    if(subject.getState() == 1){
      if(m_hitRule.DoHit(this)){
        //getting a new card
        // notify(); // from observer

        GetShowDeal(m_deck, true);

        subject.setState(0);
      }
    }
  }
}