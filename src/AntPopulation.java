import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


@SuppressWarnings("serial")
public class AntPopulation extends ArrayList<Ant> {
	
	public Random rand = new Random();
	public static int antCount = 0;
	
	// Called each round to move data from myAnts to AntPopulation
	public void updateAntPopulation() {
		Set<Tile> myAnts = MyBot.ants.getMyAnts();
		
		createNewSpawnedAnts(myAnts);
	}
	
	public void createNewSpawnedAnts(Set<Tile> myAnts) {
		for(Tile myAnt : myAnts) {
			Ant a = getAntAtRowCol(myAnt.getRow(), myAnt.getCol());
			
			if(a != null && MyBot.enemyAntHills.contains(a.getPosition())) {
				MyBot.enemyAntHills.remove(a.getPosition());
			}
			
			if(a == null) {
				// Sanitiy check to see if ant spawned on a hill
				boolean sane = false;
				for(Tile hill : MyBot.ants.getMyHills()) {
					if(Util.samePosition(hill, myAnt)) {
						sane = true;
						break;
					}
				}
				
				// If spawn = hill
				if(sane) {
					this.add(spawnAnt(myAnt));
					
				} else {
					//TODO: OMG! Issues... We are spawning outside ant hill
					Util.addToLog("Mad issue! Ant spawned outside the ant hill!");
				} 
			}
		}
	}
	
	public Ant spawnAnt(Tile myAnt) {
		Ant a = new Ant(myAnt.getRow(), myAnt.getCol());
		
		// All ants should kill ant hills
		a.addBehavior(new KillAntHillBehavior(a));
		
		// Make our ants want food... The first ten + 50% of the rest collect food
		if(antCount < 10 || rand.nextInt(10) > 5) {
			a.addBehavior(new CollectClosestFoodBehavior(a));
		}
		
		// Make our ants willing to explore
		a.addBehavior(new ExploreUnseenBehavior(a));
		
		// If all else fails, we just move about randomly
		a.addBehavior(new RandomMovementBehavior(a));
		
		return a;
	}
	
	public Ant getAntAtRowCol(int row, int col) {
		for(Ant a : this) {
			if(a.getPosition().getRow() == row && a.getPosition().getCol() == col) {
				return a;
			}
		}
		
		return null;
	}
}
