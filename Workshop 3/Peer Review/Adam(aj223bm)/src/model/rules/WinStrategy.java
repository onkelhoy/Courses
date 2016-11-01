package model.rules;

import model.Dealer;
import model.Player;

/**
 * Created by MohamedOsman on 2016-10-24.
 */
public  interface WinStrategy   {

  boolean WinStrategy(Dealer dealer, Player player);

}
