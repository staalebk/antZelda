import java.util.ArrayList;
import java.util.List;


public class BehaviorDecision implements Comparable<BehaviorDecision> {
	
	public static BehaviorDecision NO_DECISION = new BehaviorDecision(new ArrayList<Aim>(), null, 0);
	
	private List<Aim> movements;
	private int urgency = 0;
	private Tile destination = null;
	
	public BehaviorDecision(List<Aim> movements, Tile destination, int urgency) {
		this.movements = movements;
		this.urgency = urgency;
		this.destination = destination;
	}
	
	public Tile getDestination() {
		return destination;
	}

	public void setDestination(Tile destination) {
		this.destination = destination;
	}

	public List<Aim> getMovements() {
		return movements;
	}

	public void setMovements(List<Aim> movements) {
		this.movements = movements;
	}

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	@Override
	public int compareTo(BehaviorDecision o) {
		if(this.urgency == o.getUrgency()) {
			return 0;
		}
		
		return (this.urgency > o.getUrgency()) ? -1 : 1;
	}
}
