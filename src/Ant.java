import java.beans.DesignMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Ant {
	private Tile position;
	private Tile destination;
	private Tile stuckAt;
	private List<Behavior> behavior = null;
	private boolean wallAvoidanceMode = false;

	public Ant(int row, int col) {
		this.position = new Tile(row, col);
		this.behavior = new ArrayList<Behavior>();
	}
	
	public void addBehavior(Behavior b) {
		this.behavior.add(b);
	}
	
	public void clearBehaviors() {
		this.behavior.clear();
	}
	
	public Tile getPosition() {
		return position;
	}
	
	public void setPosition(Tile position) {
		this.position = position;
	}
	
	public Tile getDestination() {
		return destination;
	}

	public void setDestination(Tile destination) {
		this.destination = destination;
	}

	public void setCol(int col) {
		this.position = new Tile(this.position.getRow(), col);
	}
	
	public void setRow(int row) {
		this.position = new Tile(row, this.position.getCol());
	}
	
	public int getCol() {
		return this.position.getCol();
	}
	
	public int getRow() {
		return this.position.getRow();
	}
	
	public List<Aim> avoidWall(){
		List<Aim> allDirections = Arrays.asList(Aim.values());
		List<Aim> canMove = new ArrayList<Aim>();
		List<Aim> cantMove = new ArrayList<Aim>();
		List<Aim> willMove = new ArrayList<Aim>();
 		for(Aim direction : allDirections){
			if(Util.canMoveDirection(this.position, direction)){
				canMove.add(direction);
			} else {
				cantMove.add(direction);
			}
		}
		if(cantMove.contains(Aim.EAST) && canMove.contains(Aim.SOUTH)){
			willMove.add(Aim.SOUTH); 
		} else if (cantMove.contains(Aim.NORTH) && canMove.contains(Aim.EAST)){
			willMove.add(Aim.EAST);
		} else if (cantMove.contains(Aim.WEST) && canMove.contains(Aim.NORTH)){
			willMove.add(Aim.NORTH);
		} else if (cantMove.contains(Aim.SOUTH) && canMove.contains(Aim.WEST)){
			willMove.add(Aim.WEST);
		} else if (canMove.size() == 1) {
			willMove = canMove;
		}
		return willMove;
	}
	
	public List<Aim> isStuck(){
		this.stuckAt = this.getPosition();
		this.wallAvoidanceMode = true;
		return this.avoidWall();
	}
	
	public List<Aim> makeMovementDecision() {
		if (!this.wallAvoidanceMode){
			List<BehaviorDecision> decisions = new ArrayList<BehaviorDecision>();
			
			for(Behavior b : this.behavior) {
				decisions.add(b.move());
			}
			
			if(!decisions.isEmpty()) {
				Collections.sort(decisions);
				
				BehaviorDecision bestDecision = decisions.get(0); 
				this.setDestination(bestDecision.getDestination());
				
				return bestDecision.getMovements();	
			}
			
			// No idea what to do. Return empty movement list...
			return new ArrayList<Aim>();
			
		} else {
			return this.avoidWall();
		}
	}
}
