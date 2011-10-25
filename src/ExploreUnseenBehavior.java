

public class ExploreUnseenBehavior extends Behavior {

	public ExploreUnseenBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		// Look for unseen tiles
		Tile unseen = Util.getClosestUnseenTile(owner.getPosition());
		
		if (unseen != null) {
			return new BehaviorDecision(unseen, "Moving to unseen square at " + unseen, 75); 
		} else {
			return BehaviorDecision.NO_DECISION;
		}
	}

}
