import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Starter bot implementation.
 */
public class Zelda extends Bot {
	boolean seenTiles[][];
	FileWriter logfile = null;

	/**
	 * Main method executed by the game engine for starting the bot.
	 * 
	 * @param args
	 *            command line arguments
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		new Zelda().readSystemInput();
	}

	public boolean doMoveDirection(Ants ants, HashMap<Tile, Tile> orders, Tile antLoc, Aim direction) {
		// Track all moves, prevent collisions
		Tile newLoc = ants.getTile(antLoc, direction);
		if (ants.getIlk(newLoc).isUnoccupied() && !orders.containsKey(newLoc)) {
			ants.issueOrder(antLoc, direction);
			orders.put(newLoc, antLoc);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setup(int loadTime, int turnTime, int rows, int cols, int turns, int viewRadius2, int attackRadius2, int spawnRadius2) {
		super.setup(loadTime, turnTime, rows, cols, turns, viewRadius2, attackRadius2, spawnRadius2);
		seenTiles = new boolean[rows][cols];
		try {
			logfile = new FileWriter("c:/kjell.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printf(String log) {
		try {
			logfile.write(log + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void doTurn() {
		HashMap<Tile, Tile> orders = new HashMap<Tile, Tile>();
		Ants ants = getAnts();
		updateSeen(ants);
		for (Tile myAnt : ants.getMyAnts()) {
			int distance = 666;
			Tile food = null;
			for (Tile foodTile : ants.getFoodTiles()) {
				int d = ants.getDistance(foodTile, myAnt);
				if (d < distance) {
					distance = d;
					food = foodTile;
				}
			}
			if (food != null) {
				List<Aim> pDir = ants.getDirections(myAnt, food);
				Collections.shuffle(pDir);
				for (Aim direction : pDir) {
					if (doMoveDirection(ants, orders, myAnt, direction)) {
						printf("Going for food!");
						break;
					}
				}
			} else {
				Tile unseen = null;
				distance = 666;
				for (int row = 0; row < ants.getRows(); row++) {
					for (int col = 0; col < ants.getCols(); col++) {
						if (!seenTiles[row][col]) {
							Tile unseenTile = new Tile(row, col);
							int d = ants.getDistance(unseenTile, myAnt);
							if (d < distance) {
								distance = d;
								unseen = unseenTile;
							}
						}
					}
				}
				if (unseen != null) {
					List<Aim> pDir = ants.getDirections(myAnt, unseen);
					Collections.shuffle(pDir);
					for (Aim direction : pDir) {
						if (doMoveDirection(ants, orders, myAnt, direction)) {
							printf("Going to explore! " + distance + " " + unseen.getRow() + " " + unseen.getCol());
							break;
						}
					}
				} else {
					for (Aim direction : Aim.values()) {
						if (doMoveDirection(ants, orders, myAnt, direction)) {
							printf("Going nuts!");
							setAllUnseen(ants);
							break;
						}
					}
				}
			}
		}
	}

	public void setAllUnseen(Ants ants) {
		for (int row = 0; row < ants.getRows(); row++) {
			for (int col = 0; col < ants.getCols(); col++) {
				seenTiles[row][col] = false;
			}
		}

	}

	public void updateSeen(Ants ants) {
		String rowstatus = "";
		for (int row = 0; row < ants.getRows(); row++) {
			for (int col = 0; col < ants.getCols(); col++) {
				for (Tile ant : ants.getMyAnts()) {
					if (ants.getDistance(ant, new Tile(row, col)) < ants.getViewRadius2()) {
						seenTiles[row][col] = true;
					}
				}
				if (seenTiles[row][col]) {
					Tile newLoc = new Tile(row, col);
					if (ants.getIlk(newLoc) == Ilk.DEAD)
						rowstatus += "x";
					else if (ants.getIlk(newLoc) == Ilk.ENEMY_ANT)
						rowstatus += "e";
					else if (ants.getIlk(newLoc) == Ilk.FOOD)
						rowstatus += "f";
					else if (ants.getIlk(newLoc) == Ilk.LAND)
						rowstatus += " ";
					else if (ants.getIlk(newLoc) == Ilk.MY_ANT)
						rowstatus += "M";
					else if (ants.getIlk(newLoc) == Ilk.WATER)
						rowstatus += "w";
					else
						rowstatus += "_";
				} else {
					rowstatus += "?";
				}

			}
			printf(rowstatus);
			rowstatus = "";
		}
		printf("------------------------------");
	}
}

