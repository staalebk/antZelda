

public class KillAntHillBehavior extends Behavior {

	public KillAntHillBehavior(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		
		// Ignore enemy anthills if we are under 10 ants alive. Better get some food before we continue...
		if(MyBot.antPop.size() < 10) {
			return BehaviorDecision.NO_DECISION;
		}
		
		Tile anthill = Util.getClosestTile(owner.getPosition(), MyBot.enemyAntHills);		

		if(anthill != null) {
			// Found unseen tile. Explore in its direction
			return new BehaviorDecision(anthill, "Killing anthill at " + anthill, 150);
		}
		
		return BehaviorDecision.NO_DECISION;
	}

}
