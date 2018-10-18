package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;
/**
 * The University of Melbourne
 * SWEN30006 Software Modelling and Design
 * FileName: ExploreStrategy.java
 *
 *
 * This class contains Strategy to find the exit, 
 * the goal is to find the exit
 *
 * @author  Chenyang Lu, Leewei Kuo, Xueting Tan
 * @StudentID 951933, 932975, 948775
 * @Username  chenyangl5, leeweik1, xuetingt
 * 
 * @Date  18/10/2018 
 */
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