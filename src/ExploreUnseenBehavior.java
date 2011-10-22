import java.util.Collections;
import java.util.List;


public class ExploreUnseenBehavior extends Behavior {

	public ExploreUnseenBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		// Look for unseen tiles
		Tile unseen = Util.getClosestUnseenTile(owner.getPosition());
		
		if (unseen != null) {
			// Found unseen tile. Explore in its direction
			List<Aim> pDir = MyBot.ants.getDirections(owner.getPosition(), unseen);
			Collections.shuffle(pDir);
			
			return new BehaviorDecision(pDir, 9); 
		} else {
			return BehaviorDecision.NO_DECISION;
		}
	}

}
