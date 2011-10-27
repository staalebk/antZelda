import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 * @author olekvi
 *
 */
/**
 * @author olekvi
 *
 */
public class Util {
	
	private static boolean debug = false;
	private static FileWriter logfile = null;
	private static String logfileName = null;
	
	/**
	 * Get the closest location from a list of locations. Used to locate the closest food etc.
	 * @param myPos The current location of the ant
	 * @param tiles A {@link List} of {@link Tile} that you should search 
	 * @return The closest Tile
	 */
	public static Tile getClosestTile(Tile myPos, Set<Tile> tiles) {
		
		int distance = Integer.MAX_VALUE;
		Tile closest = null;
		

		for (Tile currentTile : tiles) {
			int d = MyBot.ants.getDistance(currentTile, myPos);
			
			if (d < distance) {
				distance = d;
				closest = currentTile;
			}
		}
		
		return closest;
	}
	
	/**
	 * Compares if Tile A and Tile B is the same location
	 * @param a Tile A
	 * @param b Tile B
	 * @return boolean indicating if its the same location
	 */
	public static boolean samePosition(Tile a, Tile b) {
		return (a.getCol() == b.getCol() && a.getRow() == b.getRow());
	}
	
	/**
	 * Get the closest unseen tile based on the current location
	 * @param myPos The current location of the ant
	 * @return Tile indicating the closest unseen location
	 */
	public static Tile getClosestUnseenTile(Tile myPos) {
		Tile unseen = null;
		
		int distance = Integer.MAX_VALUE;
		for (int row = 0; row < MyBot.ants.getRows(); row++) {
			for (int col = 0; col < MyBot.ants.getCols(); col++) {
				if (!MyBot.seenTiles[row][col]) {
					Tile unseenTile = new Tile(row, col);
					int d = MyBot.ants.getDistance(unseenTile, myPos);
					if (d < distance) {
						distance = d;
						unseen = unseenTile;
					}
				}
			}
		}
		
		return unseen;
	}

	/**
	 * Iterates through desired movements and removes those that are illegal based on the known map
	 * @param desiredMovement
	 * @param antPos
	 * @return List containing of legal moves
	 */
	public static List<Aim> removeIllegalMoves(List<Aim> desiredMovement, Tile antPos) {
		// Reverse iterate through all movements and remove illegal ones
		for(int i = desiredMovement.size() - 1; i >= 0; i--) {
			Aim direction = desiredMovement.get(i);
			
			Tile newLoc = MyBot.ants.getTile(antPos, direction);
			
			if(!MyBot.ants.getIlk(newLoc).isPassable()) {
				desiredMovement.remove(i);
			}
		}
		
		// Return list with legal moves
		return desiredMovement;
	}
	

	/**
	 * Sends a String to the log file if debug parameter is enabled 
	 * @param log
	 */
	public static void addToLog(String log) {
		if (debug) {
			if(logfile == null) {
				setLogfileName();
				try {
					logfile = new FileWriter(logfileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				logfile.write(log + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Checks if a move is allowed
	 * @param mypos
	 * @param direction
	 * @return boolean indicating if the move is allowed
	 */
	public static boolean canMoveDirection(Tile mypos, Aim direction){
		Tile newLoc = MyBot.ants.getTile(mypos, direction);
		return MyBot.ants.getIlk(newLoc).isPassable();
	}
	
	/**
	 * Sets logfile name based on operating system
	 * @
	 */
	private static void setLogfileName() {
		logfileName = "/tmp/bot_log.txt";
		if (isWindows()) {
			logfileName = "c:/temp/bot_log.txt";
		}
	}
	
	/**
	 * Check to find if we are on a Windows system
	 * @return true if Windows
	 */
	private static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf( "win" ) >= 0);
	}
}
