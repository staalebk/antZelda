import java.util.List;


public class Ant {
	private Tile position;
	private Behavior behavior;

	public Ant(int row, int col, Behavior behavior) {
		this.position = new Tile(row, col);
		this.behavior = behavior;
	}
	
	public Tile getPosition() {
		return position;
	}

	public void setPosition(Tile position) {
		this.position = position;
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

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}
	
	public List<Aim> makeMovementDecision() {
		return this.behavior.move();
	}
}
