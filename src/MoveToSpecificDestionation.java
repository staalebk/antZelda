import java.util.List;


public class MoveToSpecificDestionation extends Behavior {
	
	public MoveToSpecificDestionation(Ant ant) {
		super(ant);
	}

	@Override
	public BehaviorDecision move() {
		List<Aim> movement = MyBot.ants.getDirections(owner.getPosition(), owner.getDestination()); 
		return  new BehaviorDecision(movement, 15);
	}

}
