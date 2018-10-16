package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;

public class FindExitStrategy extends RoutingStrategy {
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