import java.util.List;


public class CollectSpecificFoodBehavior implements Behavior {
	private Ant owner = null;
	
	public CollectSpecificFoodBehavior(Ant owner, Tile target) {
		this.owner = owner;
	}
	
	@Override
	public List<Aim> move() {
		return MyBot.ants.getDirections(owner.getPosition(), owner.getDestination());
	}

}
