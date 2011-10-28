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
			
			// We are standing one an enemy anthill... remove it from the collection and move on.
			if(a != null && MyBot.enemyAntHills.contains(a.getPosition())) {
				MyBot.enemyAntHills.remove(a.getPosition());
			}
			
			if(a == null) {
				this.add(spawnAnt(myAnt));
			}
		}
	}
	
	public Ant spawnAnt(Tile myAnt) {
		Ant a = new Ant(myAnt.getRow(), myAnt.getCol());
		// 10% should guard our ant hill(s).
		if(rand.nextInt(10) > 8){
			a.addBehavior(new ProtectAntHillBehaviour(a));
		}
		
		// 90% ants should kill ant hills
		if(rand.nextInt(10) > 1){
			a.addBehavior(new KillAntHillBehavior(a));
		}
		
		// Make our ants want food... The first ten + 50% of the rest collect food
		if(antCount < 15 || rand.nextInt(10) > 4) {
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
