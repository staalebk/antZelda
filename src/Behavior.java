

public abstract class Behavior {
	
	protected Ant owner;
	
	public Behavior(Ant ant) {
		this.owner = ant;
	}
	
	// This should be overwritten
	public abstract BehaviorDecision move();
	
	//
	public Ant getOwner() {
		return this.owner;
	}
}
