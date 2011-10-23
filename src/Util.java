import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;


public class Util {
	
	private static boolean debug = true;
	private static FileWriter logfile = null;
	
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
	
	public static boolean samePosition(Tile a, Tile b) {
		return (a.getCol() == b.getCol() && a.getRow() == b.getRow());
	}
	
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
	

	public static void addToLog(String log) {
		if (debug) {
			if(logfile == null) {
				try {
					logfile = new FileWriter("c:/temp/bot_log.txt");
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
	
	public static boolean canMoveDirection(Tile mypos, Aim direction){
		Tile newLoc = MyBot.ants.getTile(mypos, direction);
		return MyBot.ants.getIlk(newLoc).isPassable();
	}
}
