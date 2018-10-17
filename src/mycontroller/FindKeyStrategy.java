package mycontroller;

import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

//Strategy to find the key, the goal is to get the key
public class FindKeyStrategy extends RoutingStrategy {
	int key;
	public FindKeyStrategy(Coordinate coordinate, Direction currentOrientation, int targetKey) {
		super(coordinate, currentOrientation);
		key = targetKey;
	}

	//check whether the coordinate has a key
	@Override
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		MapTile mapTile = recordTile.getMapTile();
		if (mapTile instanceof LavaTrap) {
			LavaTrap lavaTile = (LavaTrap) mapTile;
			if (lavaTile.getKey() == key) {
				return true;
			}
		} 
		return false;
	}
}