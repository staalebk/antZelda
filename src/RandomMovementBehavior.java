import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RandomMovementBehavior implements Behavior {

	@Override
	public List<Aim> move() {
		// Move a random direction
		List<Aim> pDir = Arrays.asList(Aim.values());
		Collections.shuffle(pDir);
		
		return pDir;
	}
}
