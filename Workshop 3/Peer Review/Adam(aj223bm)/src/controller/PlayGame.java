package controller;

import model.Card;
import model.IObserver;
import view.IView;
import model.Game;

public class PlayGame  implements IObserver{
    private  Game a_game;
    private IView a_view;


    public boolean Play(Game game, IView view) {
        a_game = game;
        a_view = view;
        a_view.DisplayWelcomeMessage();



        a_view.DisplayDealerHand(a_game.GetDealerHand(),a_game.GetDealerScore());
        a_view.DisplayPlayerHand(a_game.GetPlayerHand(),a_game.GetPlayerScore());



        if (a_game.IsGameOver()) {
            a_view.DisplayGameOver(a_game.IsDealerWinner());
            a_game.clearSuscribers();
        }

        int input = a_view.GetInput();

        if (input == 'p') {
            a_game.suscribe(this);
            a_game.NewGame();




        } else if (input == 'h') {
            a_game.Hit();
        } else if (input == 's') {
            a_game.Stand();
        }

        return input != 'q';
    }

    @Override
    public void cardIsdDealt( ){
        a_view.Pause();
        a_view.DisplayDealerHand(a_game.GetDealerHand(),a_game.GetDealerScore());
        a_view.DisplayPlayerHand(a_game.GetPlayerHand(),a_game.GetPlayerScore());


    }}