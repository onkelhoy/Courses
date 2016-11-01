package model.rules;

public class RulesFactory {

    public IHitStrategy GetHitRule() {
        return new BasicHitStrategy();
    }

    public IHitStrategy soft17Rule() {
        return new BasicHitStrategy();
    }

    public INewGameStrategy GetNewGameRule() {
        return new AmericanNewGameStrategy();
    }

    public Soft17Strategy GetSoft17Rule() {
        return new Soft17Strategy();
    }

    public WinStrategy GetNewPlayerWinStrategy() {

        return new PlayerWinStragey();
    }

    public WinStrategy GetNewDealerWinStrategy() {

        return new DealerWinStrategy();
    }
}