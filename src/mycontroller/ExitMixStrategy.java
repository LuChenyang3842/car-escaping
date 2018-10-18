package mycontroller;

import tiles.HealthTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

//a mix strategy to find the exit
public class ExitMixStrategy extends RoutingStrategy{
	HealStrategy heal;
	FindExitStrategy findExit;
	float health;
	Coordinate currentCoordinate;
	MapTile currentMapTile;
	
	public ExitMixStrategy(Coordinate coordinate, Direction currentOrientation, float health) {
		super(coordinate, currentOrientation);
		heal = new HealStrategy(coordinate, currentOrientation);
		findExit = new FindExitStrategy(coordinate, currentOrientation);
		this.health = health;
		this.currentCoordinate = coordinate;
		this.currentMapTile = ExploreMap.getInstance().getExploredMap().get(coordinate).getMapTile();
	}

	/*
	 * if the health is low, find the health trap
	 * otherwise find the exit
	 */
	@Override
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		//MapTile mapTile = recordTile.getMapTile();
		int healDistance = heal.AstarPathFinding().size();
		//heal when standing on HealthTrap or close to HealthTrap(compare to exit)
		if ((currentMapTile instanceof HealthTrap && (health < 100)) || 
				((health < 50) && (healDistance != 0) && (healDistance < findExit.AstarPathFinding().size()))) {
			return heal.isGoal(c);
		} else {
			return findExit.isGoal(c);
		}
	}
}
