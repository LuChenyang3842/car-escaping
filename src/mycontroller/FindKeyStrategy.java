package mycontroller;

import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;

public class FindKeyStrategy extends RoutingStrategy {

	@Override
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		MapTile mapTile = recordTile.getMapTile();
		if (mapTile instanceof LavaTrap) {
			LavaTrap lavaTile = (LavaTrap) mapTile;
			
			//what if two keys are beside each other?
			if (lavaTile.getKey() != 0) {
				return true;
			}
		} 
		return false;
	}
}