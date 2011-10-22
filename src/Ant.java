import java.util.ArrayList;
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
		return null;
	}
	
	public List<Aim> makeMovementDecision() {
		if (!this.wallAvoidanceMode){
			return this.behavior.move();
		} else {
			return this.avoidWall();
		}
	}
}
