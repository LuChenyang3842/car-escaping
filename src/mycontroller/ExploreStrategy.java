package mycontroller;

import utilities.Coordinate;
import world.WorldSpatial.Direction;
/**
 * The University of Melbourne
 * SWEN30006 Software Modelling and Design
 * FileName: ExploreStrategy.java
 *
 *
 * This class contains strategy to explore the map, 
 * the goal is to find a unexplored tile in ExploreMap
 *
 * @author  Chenyang Lu, Leewei Kuo, Xueting Tan
 * @StudentID 951933, 932975, 948775
 * @Username  chenyangl5, leeweik1, xuetingt
 * 
 * @Date  18/10/2018 
 */
public class ExploreStrategy extends RoutingStrategy {
	
	public ExploreStrategy(Coordinate currentPosition, Direction orientation) {
		super(currentPosition, orientation);
	}

	//check whether the coordinate is unexplored
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		if (!recordTile.getExplored()) {
			return true;
		} else {
			return false;
		}		
	}
}