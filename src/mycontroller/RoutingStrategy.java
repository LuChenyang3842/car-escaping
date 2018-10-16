package mycontroller;

import java.util.ArrayList;
import utilities.Coordinate;

public abstract class RoutingStrategy {
    private Coordinate currentLocation;
	private HashMap<Coordinate, RecordTile> currentMap;
	
	public void getSuccessor(){
		
	}
	
	
	public void findPath() {
		
	}
	
	public void heuristic() {
		
	}
	public abstract boolean isGoal(Coordinate c);
	
	

}
