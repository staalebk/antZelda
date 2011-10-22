import java.util.ArrayList;
import java.util.Set;


@SuppressWarnings("serial")
public class AntPopulation extends ArrayList<Ant> {
	
	// Called each round to move data from myAnts to AntPopulation
	public void updateAntPopulation() {
		Set<Tile> myAnts = MyBot.ants.getMyAnts();
		
		createNewSpawnedAnts(myAnts);
	}
	
	public void createNewSpawnedAnts(Set<Tile> myAnts) {
		for(Tile myAnt : myAnts) {
			Ant a = getAntAtRowCol(myAnt.getRow(), myAnt.getCol());
			
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
					a = new Ant(myAnt.getRow(), myAnt.getCol(), new FoodGathererBehavior());
					this.add(a);
				} else {
					//TODO: OMG! Issues... We are spawning outside ant hill
				} 
			}
		}
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
