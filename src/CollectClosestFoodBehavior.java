
public class CollectClosestFoodBehavior extends Behavior {

	public CollectClosestFoodBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		Tile food = Util.getClosestTile(owner.getPosition(), MyBot.ants.getFoodTiles());

		if (food != null) {
			int distance = MyBot.ants.getDistance(owner.getPosition(), food);
			
			return new BehaviorDecision(food, "Collecting food at " + food, 100);
		} else {
			return BehaviorDecision.NO_DECISION;
		}
	}

}
