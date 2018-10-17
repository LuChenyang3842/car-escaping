package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial.Direction;

//Strategy to explore the map, the goal is to find a unexplored tile in ExploreMap
public class ExploreStrategy extends RoutingStrategy {
	
	public ExploreStrategy(Coordinate currentPosition, Direction orientation) {
		super(currentPosition, orientation);
	}

	//check whether the coordinate is unexplored
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		if (!recordTile.getExplored()) {
			return true;
		} else {
			return false;
		}		
	}
}