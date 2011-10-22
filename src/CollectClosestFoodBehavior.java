import java.util.Collections;
import java.util.List;



public class CollectClosestFoodBehavior extends Behavior {

	public CollectClosestFoodBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		Tile food = Util.getClosestTile(owner.getPosition(), MyBot.ants.getFoodTiles());

		if (food != null) {
			List<Aim> pDir = MyBot.ants.getDirections(owner.getPosition(), food);
			Collections.shuffle(pDir);
			
			return new BehaviorDecision(pDir, food, 10);
		} else {
			return BehaviorDecision.NO_DECISION;
		}
	}

}
