import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ant {
	private int antID;
	private Tile position;
	private Tile destination;
	private Tile stuckAt;
	private Aim lastDirection;
	private List<Behavior> behavior = null;
	private boolean wallAvoidanceMode = false;

	public Ant(int row, int col) {
		this.antID = AntPopulation.antCount++;
		this.position = new Tile(row, col);
		this.behavior = new ArrayList<Behavior>();
	}

	public void addBehavior(Behavior b) {
		this.behavior.add(b);
	}

	public int getAntID() {
		return antID;
	}

	public void setAntID(int antID) {
		this.antID = antID;
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

	public Aim getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(Aim lastDirection) {
		this.lastDirection = lastDirection;
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

	public List<Aim> avoidWall() {
		List<Aim> allDirections = Arrays.asList(Aim.values());
		List<Aim> canMove = new ArrayList<Aim>();
		List<Aim> cantMove = new ArrayList<Aim>();
		List<Aim> willMove = new ArrayList<Aim>();
		for (Aim direction : allDirections) {
			if (Util.canMoveDirection(this.position, direction)) {
				canMove.add(direction);
			} else {
				cantMove.add(direction);
			}
		}
		if (cantMove.contains(Aim.EAST) && canMove.contains(Aim.SOUTH)) {
			willMove.add(Aim.SOUTH);
		} else if (cantMove.contains(Aim.NORTH) && canMove.contains(Aim.EAST)) {
			willMove.add(Aim.EAST);
		} else if (cantMove.contains(Aim.WEST) && canMove.contains(Aim.NORTH)) {
			willMove.add(Aim.NORTH);
		} else if (cantMove.contains(Aim.SOUTH) && canMove.contains(Aim.WEST)) {
			willMove.add(Aim.WEST);
		} else if (canMove.size() == 1) {
			willMove = canMove;
		} else {
			if (lastDirection == Aim.SOUTH) {
				willMove.add(Aim.EAST);
			} else if (lastDirection == Aim.EAST) {
				willMove.add(Aim.NORTH);
			} else if (lastDirection == Aim.NORTH) {
				willMove.add(Aim.WEST);
			} else if (lastDirection == Aim.WEST) {
				willMove.add(Aim.SOUTH);
			} else if (lastDirection == null) {
				// wtf! stuck on the first turn? suuucks!
				willMove = canMove;
			}
		}
		Util.addToLog("Ant " + antID + ": Avoiding wall, moving " + willMove.get(0).toString());
		return willMove;
	}

	public List<Aim> isStuck() {
		this.stuckAt = this.getPosition();
		this.wallAvoidanceMode = true;
		return this.avoidWall();
	}

	public List<Aim> makeMovementDecision() {

		List<BehaviorDecision> decisions = new ArrayList<BehaviorDecision>();

		for (Behavior b : this.behavior) {
			decisions.add(b.move());
		}

		if (!decisions.isEmpty()) {
			Collections.sort(decisions);

			int d = 0;
			BehaviorDecision bestDecision = null;
			List<Tile> p = null;
			while(p == null && d < decisions.size() - 1) {
				bestDecision = decisions.get(d);
			
				this.setDestination(bestDecision.getDestination());
				AStarTile path = new AStarTile(destination);
				p = path.compute(position);
				d++;
			}

			Util.addToLog("Ant " + antID + ": " + bestDecision.getExplaination());
			
			if(p == null) {
				return new ArrayList<Aim>();
			}
			
			Tile nextTile = p.get(1);
			return MyBot.ants.getDirections(position, nextTile);
		}

		return new ArrayList<Aim>();
	}

	@Override
	public String toString() {
		return "Ant [antID=" + antID + ", position=" + position + "]";
	}
}
