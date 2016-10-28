package BlackJack.view;

import BlackJack.model.Observer;

public interface IView
{
  void DisplayWelcomeMessage();
  void DisplayDealerStatus();
  int GetInput();
  void DisplayCard(BlackJack.model.Card a_card);
  void DisplayPlayerHand(Iterable<BlackJack.model.Card> a_hand, int a_score);
  void DisplayDealerHand(Iterable<BlackJack.model.Card> a_hand, int a_score);
  void DisplayGameOver(boolean a_dealerIsWinner);
}