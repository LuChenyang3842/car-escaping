package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial.Direction;
import tiles.HealthTrap;
import tiles.MapTile;
/**
 * The University of Melbourne
 * SWEN30006 Software Modelling and Design
 * FileName: HealStrategy.java
 *
 *
 * This class contains the Strategy to find health trap, 
 * the goal is to find the HealthTrap tile in ExploreMap
 *
 * @author  Chenyang Lu, Leewei Kuo, Xueting Tan
 * @StudentID 951933, 932975, 948775
 * @Username  chenyangl5, leeweik1, xuetingt
 * 
 * @Date  18/10/2018 
 */
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
	}
}