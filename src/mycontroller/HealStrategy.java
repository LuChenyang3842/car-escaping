package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial.Direction;
import tiles.HealthTrap;
import tiles.MapTile;

//Strategy to heal, the goal is to find the HealthTrap tile in ExploreMap
public class HealStrategy extends RoutingStrategy{
	
	public HealStrategy(Coordinate currentPosition, Direction orientation) {
		super(currentPosition, orientation);
	}

	//check whether the coordinate is a HealthTrap
	@Override
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		MapTile mapTile = recordTile.getMapTile();
		if(mapTile instanceof HealthTrap) {
			return true;
		} else {
			return false;
		}
		/*if (mapTile.isType(MapTile.Type.TRAP)) {
			TrapTile trapTile = (TrapTile)mapTile;
			if(trapTile.getTrap().equals("health")) {
				return true;
			}
		} 
		return false;*/
		
	}
}