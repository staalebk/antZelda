import java.util.ArrayList;
import java.util.List;


public class BehaviorDecision implements Comparable<BehaviorDecision> {
	
	public static BehaviorDecision NO_DECISION = new BehaviorDecision(new ArrayList<Aim>(), 0);
	
	private List<Aim> movements;
	private int urgency = 0;
	
	public BehaviorDecision(List<Aim> movements, int urgency) {
		this.movements = movements;
		this.urgency = urgency;
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
		
		return (this.urgency > o.getUrgency()) ? 1 : -1;
	}
}
