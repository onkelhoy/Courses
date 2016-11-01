package model.rules;

import model.Dealer;
import model.Player;

/**
 * Created by MohamedOsman on 2016-10-24.
 */
public class PlayerWinStragey  implements  WinStrategy {
    private  int g_maxScore = 21;
    @Override
    public boolean WinStrategy(Dealer dealer, Player player) {

        if (player.CalcScore() > g_maxScore) {
            return true;
        } else if (dealer.CalcScore() > g_maxScore) {
            return false;
        }
        return !(dealer.CalcScore() <= player.CalcScore());
    }
}
