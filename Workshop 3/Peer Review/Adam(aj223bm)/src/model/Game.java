package model;

import model.rules.RulesFactory;

public class Game {

  private Dealer m_dealer;
  private Player m_player;

  public Game()
  {
    m_dealer = new Dealer(new RulesFactory());
    m_player = new Player();
  }
    
    
  public boolean IsGameOver()
  {
    return m_dealer.IsGameOver();
  }
  
  public boolean IsDealerWinner()
  {
    return m_dealer.IsDealerWinner(m_player);
  }
  
  public boolean NewGame()
  {
    return m_dealer.NewGame(m_player);
  }
  
  public boolean Hit()
  {
    return m_dealer.Hit(m_player);
  }
  
  public boolean Stand()
  {
    return m_dealer.stand();
  }

  public  void suscribe(IObserver observer){
    m_dealer.Addsub(observer);
    m_player.Addsub(observer);
  }

  public  void clearSuscribers(){
    m_player.getM_observers().clear();
    m_dealer.getM_observers().clear();
  }
  public Iterable<Card> GetDealerHand()
  {
    return m_dealer.GetHand();
  }
  
  public Iterable<Card> GetPlayerHand()
  {
    return m_player.GetHand();
  }
  
  public int GetDealerScore()
  {
    return m_dealer.CalcScore();
  }
  
  public int GetPlayerScore()
  {
    return m_player.CalcScore();
  }


    
  
}