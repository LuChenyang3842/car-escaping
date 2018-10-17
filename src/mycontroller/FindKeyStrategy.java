package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

//Strategy to find the exit, the goal is to find the exit
public class FindExitStrategy extends RoutingStrategy {
	
	public FindExitStrategy(Coordinate currentPosition, Direction orientation) {
		super(currentPosition, orientation);
	}

	//check whether the coordinate is an exit
	@Override
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		MapTile mapTile = recordTile.getMapTile();
		if (mapTile.isType(MapTile.Type.FINISH)) {
			return true;
		} else {
			return false;
		}
	}
}