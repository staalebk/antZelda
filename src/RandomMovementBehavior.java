import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RandomMovementBehavior extends Behavior {

	public RandomMovementBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		// Move a random direction
		List<Aim> pDir = Arrays.asList(Aim.values());
		Collections.shuffle(pDir);
		
		Tile desiredTile = MyBot.ants.getTile(owner.getPosition(), pDir.get(0));
		
		return new BehaviorDecision(desiredTile, "Random movement to " + desiredTile, 1);
	}
}
