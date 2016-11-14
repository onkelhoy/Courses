package BlackJack.controller;

import BlackJack.model.Observer;
import BlackJack.model.Subject;
import BlackJack.view.IView;
import BlackJack.model.Game;

public class PlayGame implements Observer {
  private IView a_view;
  private Game a_game;
  private Subject subject;

  public PlayGame(Subject subject, IView a_view, Game a_game){
    this.subject = subject;
    this.a_view = a_view;
    this.a_game = a_game;

    subject.subscribe(this);
  }
  //implement observer and wait for furture updates
  public boolean Play() {
    a_view.DisplayWelcomeMessage();

    a_view.DisplayDealerHand(a_game.GetDealerHand(), a_game.GetDealerScore());
    a_view.DisplayPlayerHand(a_game.GetPlayerHand(), a_game.GetPlayerScore());

    if (a_game.IsGameOver())
    {
        a_view.DisplayGameOver(a_game.IsDealerWinner());
    }

    int input = a_view.GetInput();
    
    if (input == 1)
    {
        a_game.NewGame();
    }
    else if (input == 2)
    {
        a_game.Hit();
    }
    else if (input == 3)
    {
        a_game.Stand(); //subject as parameter
    }

    return input != 4;
  }

  @Override
  public void Update() {
    if(subject.getState() == 0){
      try {
        Thread.sleep(1800); //wait

        //print cards?
        a_view.DisplayDealerStatus();
        a_view.DisplayDealerHand(a_game.GetDealerHand(), a_game.GetDealerScore());
        a_view.DisplayPlayerHand(a_game.GetPlayerHand(), a_game.GetPlayerScore());


        subject.setState(1);
        subject.Notify();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}