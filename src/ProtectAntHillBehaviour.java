import java.util.Collections;
import java.util.List;


public class ProtectAntHillBehaviour extends Behavior {
	public ProtectAntHillBehaviour(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		
		// Ignore anthills if we are under 10 ants alive. Better get some food before we continue...
		if(MyBot.antPop.size() < 10) {
			return BehaviorDecision.NO_DECISION;
		}
		if(owner.getHillDefend() != null){
			return new BehaviorDecision(owner.getHillDefend(), "Protecting anthill at " + owner.getHillDefend(), 200);
		}
		
		Tile anthill = Util.getClosestTile(owner.getPosition(), MyBot.ants.getMyHills());

		if(anthill != null) {
			for(int i = 1; i < MyBot.ants.getCols(); i++){
				List<Tile> t = Util.getSurroundingTiles(anthill, i);
				Collections.shuffle(t);
				for(Tile spot : t){
					if(!MyBot.defendTile.contains(t) && MyBot.ants.getIlk(spot).isPassable()){
						MyBot.defendTile.add(spot);
						owner.setHillDefend(spot);
						return new BehaviorDecision(spot, "Protecting anthill at " + spot, 200);
					}
				}
			}
		}
		
		return BehaviorDecision.NO_DECISION;
	}

}
