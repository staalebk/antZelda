
public class AntHill {
	private Tile position;
	private int owner;
	
	public AntHill(int row, int col, int owner){
		this.position = new Tile(row, col);
		this.owner = owner;
	}
	
	public Tile getPosition() {
		return position;
	}
	
	public int getCol() {
		return this.position.getCol();
	}
	
	public int getRow() {
		return this.position.getRow();
	}
	
	public int getOwner() {
		return this.owner;
	}
	
}
