import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Starter bot implementation.
 */
public class MyBot extends Bot {
	boolean seenTiles[][];
	FileWriter logfile = null;
	boolean debug = false;

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
		new MyBot().readSystemInput();
	}

	public boolean attemptMovement(Ants ants, HashMap<Tile, Tile> orders, Tile antLoc, Aim direction) {
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
		setAllUnseen();
		if (debug) {		
			try {
				logfile = new FileWriter("c:/temp/bot_log.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addToLog(String log) {
		if (debug) {
			try {
				logfile.write(log + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doTurn() {
		HashMap<Tile, Tile> orders = new HashMap<Tile, Tile>();
		Ants ants = getAnts();

		updateSeen(ants);

		for (Tile myAnt : ants.getMyAnts()) {
			// Look for food tiles
			int distance = Integer.MAX_VALUE;
			Tile food = null;
			for (Tile foodTile : ants.getFoodTiles()) {
				int d = ants.getDistance(foodTile, myAnt);
				if (d < distance) {
					distance = d;
					food = foodTile;
				}
			}

			if (food != null) {
				// Found food. Try to move towards it...
				List<Aim> pDir = ants.getDirections(myAnt, food);
				Collections.shuffle(pDir);
				for (Aim direction : pDir) {
					if (attemptMovement(ants, orders, myAnt, direction)) {
						addToLog("Going for food! Ant at " + myAnt + " wants to go to " + food + " (dist: " + Math.sqrt(distance) + ")");
						break;
					}
				}

			} else {
				// Look for unseen tiles
				Tile unseen = null;
				distance = Integer.MAX_VALUE;
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
					// Found unseen tile. Explore in its direction
					List<Aim> pDir = ants.getDirections(myAnt, unseen);
					Collections.shuffle(pDir);
					for (Aim direction : pDir) {
						if (attemptMovement(ants, orders, myAnt, direction)) {
							addToLog("Going to explore! Ant at " + myAnt + " wants to go to " + unseen + " (dist: " + Math.sqrt(distance) + ")");
							break;
						}
					}

				} else {
					// Crazy move mode
					for (Aim direction : Aim.values()) {
						if (attemptMovement(ants, orders, myAnt, direction)) {
							addToLog("Going nuts!");
							setAllUnseen();
							break;
						}
					}
				}

			}
		}
	}

	public void setAllUnseen() {
		for (int row = 0; row < seenTiles.length; row++) {
			for (int col = 0; col < seenTiles[row].length; col++) {
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

					Ilk target = ants.getIlk(newLoc);
					switch (target) {
					case DEAD:
						rowstatus += "x";
						break;
					case ENEMY_ANT:
						rowstatus += "e";
						break;
					case FOOD:
						rowstatus += "f";
						break;
					case LAND:
						rowstatus += " ";
						break;
					case MY_ANT:
						rowstatus += "M";
						break;
					case WATER:
						rowstatus += "w";
						break;
					}

				} else {
					rowstatus += "?";
				}

			}
			addToLog(rowstatus);
			rowstatus = "";
		}
		addToLog("------------------------------");
	}
}
