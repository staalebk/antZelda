

public class KillAntHillBehavior extends Behavior {

	public KillAntHillBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		Tile anthill = Util.getClosestTile(owner.getPosition(), MyBot.enemyAntHills);		

		if(anthill != null) {
			// Found unseen tile. Explore in its direction
			return new BehaviorDecision(anthill, "Killing anthill at " + anthill, 150);
		}
		
		return BehaviorDecision.NO_DECISION;
	}

}
