package BlackJack.controller;

import BlackJack.view.IView;
import BlackJack.model.Game;
import BlackJack.model.IDealObserver;

public class PlayGame implements IDealObserver {
	private IView a_view;
	
	public PlayGame(IView a_view) {
		a_view = a_view;
	}

  public boolean Play(Game a_game, IView a_view) {
    a_view.DisplayWelcomeMessage();
    
    a_view.DisplayDealerHand(a_game.GetDealerHand(), a_game.GetDealerScore());
    a_view.DisplayPlayerHand(a_game.GetPlayerHand(), a_game.GetPlayerScore());

    if (a_game.IsGameOver())
    {
        a_view.DisplayGameOver(a_game.IsDealerWinner());
    }

    a_view.GetInput();
    
    if (a_view.newGame())
    {
        a_game.NewGame();
    }
    else if (a_view.canHit())
    {
        a_game.Hit();
    }
    else if (a_view.canStand())
    {
        a_game.Stand();
    }

    return ! a_view.canQuit();
  }

@Override
public void update() {
	// TODO Auto-generated method stub
	
}
}