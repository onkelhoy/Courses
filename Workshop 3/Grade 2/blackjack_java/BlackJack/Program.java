package BlackJack;

import BlackJack.model.Game;
import BlackJack.model.Subject;
import BlackJack.view.*;
import BlackJack.controller.*;

public class Program
{

  public static void main(String[] a_args)
  {
    //create subject object
    Subject subject = new Subject();
    Game g = new Game(subject); //pass subject as param
    IView v = new SwedishView(); //new SwedishView();
    PlayGame ctrl = new PlayGame(subject, v, g); //pass subject as param

    
    while (ctrl.Play());
  }
}