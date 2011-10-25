
public class CollectClosestFoodBehavior extends Behavior {

	public CollectClosestFoodBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		Tile food = Util.getClosestTile(owner.getPosition(), MyBot.ants.getFoodTiles());

		if (food != null) {
			int distance = (int)Math.sqrt((double)MyBot.ants.getDistance(owner.getPosition(), food)) * 5;
			
			return new BehaviorDecision(food, "Collecting food at " + food, Math.max(10, 100 - distance));
		} else {
			return BehaviorDecision.NO_DECISION;
		}
	}

}
