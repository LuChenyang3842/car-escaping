package mycontroller;

import utilities.Coordinate;
import tiles.HealthTrap;
import tiles.MapTile;

public class HealStrategy extends RoutingStrategy{
	@Override
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		MapTile mapTile = recordTile.getMapTile();
		if(mapTile instanceof HealthTrap) {
			return true;
		} else {
			return false;
		}
	}
}