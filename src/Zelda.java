import java.io.IOException;
import java.util.HashMap;

/**
 * Starter bot implementation.
 */
public class Zelda extends Bot {
    /**
     * Main method executed by the game engine for starting the bot.
     * 
     * @param args command line arguments
     * 
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        new Zelda().readSystemInput();
    }
    
    public boolean doMoveDirection(Ants ants, HashMap<Tile, Tile> orders, Tile antLoc, Aim direction) {
        // Track all moves, prevent collisions
        Tile newLoc = ants.getTile(antLoc, direction);
        if (ants.getIlk(newLoc).isUnoccupied() &&  !orders.containsKey(newLoc)) {
                    ants.issueOrder(antLoc, direction);
            orders.put(newLoc, antLoc);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void doTurn() {
        HashMap<Tile, Tile> orders = new HashMap<Tile, Tile>();
        Ants ants = getAnts();
        for (Tile myAnt : ants.getMyAnts()) {
            for (Aim direction : Aim.values()) {
                if (doMoveDirection(ants, orders, myAnt, direction)) {
                    break;
                }
            }
        }
    }
}
