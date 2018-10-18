package mycontroller;

import java.util.Set;

import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

//Strategy to find the key, the goal is to get the key
public class FindKeyStrategy extends RoutingStrategy {
	Set<Integer> key;
	public FindKeyStrategy(Coordinate coordinate, Direction currentOrientation, Set<Integer> key) {
		super(coordinate, currentOrientation);
		this.key = key;
	}
	
	//check whether the coordinate has a key
		@Override
		public boolean isGoal(Coordinate c) {
			RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
			MapTile mapTile = recordTile.getMapTile();
			if (mapTile instanceof LavaTrap) {
				LavaTrap lavaTile = (LavaTrap) mapTile;
				if (lavaTile.getKey() != 0 && !key.contains(lavaTile.getKey())) {
					return true;
				}
			} 
			return false;
		}
	}