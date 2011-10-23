import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Starter bot implementation.
 */
public class MyBot extends Bot {
	public static boolean seenTiles[][];
	public static Ants ants = null;
	

	
	public static AntPopulation antPop = new AntPopulation(); 
	

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
	}


	@Override
	public void doTurn() {
		HashMap<Tile, Tile> orders = new HashMap<Tile, Tile>();
		MyBot.ants = getAnts();
		
		antPop.updateAntPopulation();
		
		updateSeen(ants);

		Util.addToLog("------- Desicions -------");
		for(Ant ant : antPop) {
			List<Aim> desiredMovement = ant.makeMovementDecision();
			
			boolean ableToMove = false;
			for (Aim direction : desiredMovement) {
				if (attemptMovement(ants, orders, ant.getPosition(), direction)) {
					Tile newPos = ants.getTile(ant.getPosition(), direction);
					ant.setPosition(newPos);
					ableToMove = true;
					break;
				}
			}
			
			if(!ableToMove) {
				desiredMovement = ant.isStuck();
				
				for (Aim direction : desiredMovement) {
					if (attemptMovement(ants, orders, ant.getPosition(), direction)) {
						Tile newPos = ants.getTile(ant.getPosition(), direction);
						ant.setPosition(newPos);
						break;
					}
				}
			}
		}
		Util.addToLog("-------------------------");
		
		/*
		for (Tile myAnt : ants.getMyAnts()) {
			
			boolean success = false;
			
			// Look for food tiles
			int distance = Integer.MAX_VALUE;
			Tile food = Util.getClosestTile(myAnt, ants.getFoodTiles());
			
			
			if (food != null) {
				// Found food. Try to move towards it...
				List<Aim> pDir = ants.getDirections(myAnt, food);
				Collections.shuffle(pDir);
				for (Aim direction : pDir) {
					if (attemptMovement(ants, orders, myAnt, direction)) {
						addToLog("Going for food! Ant at " + myAnt + " wants to go to " + food + " (dist: " + Math.sqrt(distance) + ")");
						success = true;
						break;
					}
				}

			} else {
				// Look for unseen tiles
				Tile unseen = Util.getClosestUnseenTile(myAnt);
				
				if (unseen != null) {
					// Found unseen tile. Explore in its direction
					List<Aim> pDir = ants.getDirections(myAnt, unseen);
					Collections.shuffle(pDir);
					for (Aim direction : pDir) {
						if (attemptMovement(ants, orders, myAnt, direction)) {
							addToLog("Going to explore! Ant at " + myAnt + " wants to go to " + unseen + " (dist: " + Math.sqrt(distance) + ")");
							success = true;
							break;
						}
					}

				}
			}
			if (!success) {
				// Crazy move mode
				List<Aim> pDir = Arrays.asList(Aim.values());
				Collections.shuffle(pDir);
				for (Aim direction : pDir) {
					if (attemptMovement(ants, orders, myAnt, direction)) {
						addToLog("Going nuts!");
						setAllUnseen();
						break;
					}
				}
			}
		}
		*/
	}

	public void setAllUnseen() {
		for (int row = 0; row < seenTiles.length; row++) {
			for (int col = 0; col < seenTiles[row].length; col++) {
				seenTiles[row][col] = false;
			}
		}

	}

	public void updateSeen(Ants ants) {
		Util.addToLog("---------- MAP ------------");
		for (int row = 0; row < ants.getRows(); row++) {
			String rowstatus = String.format("%3d ", row);			
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
						if(ants.getFoodTiles().contains(new Tile(row,col))) {
							rowstatus += "f";
						} else {
							rowstatus += " ";
						}
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
			Util.addToLog(rowstatus);
		}
		Util.addToLog("---------------------------");
	}
}
