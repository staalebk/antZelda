

public class CollectClosestFoodBehavior extends Behavior {

	public CollectClosestFoodBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		Tile food = Util.getClosestTile(owner.getPosition(), MyBot.ants.getFoodTiles());

		if (food != null) {
			return new BehaviorDecision(MyBot.ants.getDirections(owner.getPosition(), food), 10);
		} else {
			return BehaviorDecision.NO_DECISION;
		}
	}

}
