package mycontroller;

import utilities.Coordinate;

public class ExploreStrategy extends RoutingStrategy {
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		if (!recordTile.getExplored()) {
			return true;
		} else {
			return false;
		}		
	}
}