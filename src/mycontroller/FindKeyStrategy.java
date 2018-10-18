package mycontroller;

import java.util.Set;

import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;
/**
 * The University of Melbourne
 * SWEN30006 Software Modelling and Design
 * FileName: ExploreStrategy.java
 *
 *
 * This class contains Strategy to find the key, 
 * the goal is to find a key
 *
 * @author  Chenyang Lu, Leewei Kuo, Xueting Tan
 * @StudentID 951933, 932975, 948775
 * @Username  chenyangl5, leeweik1, xuetingt
 * 
 * @Date  18/10/2018 
 */
public class FindKeyStrategy extends RoutingStrategy {
	
	//the key we already have
	private Set<Integer> holdKey;
	
	public FindKeyStrategy(Coordinate coordinate, Direction currentOrientation, Set<Integer> key) {
		super(coordinate, currentOrientation);
		this.holdKey = key;
	}
		
	//check whether the coordinate has an unseen key
	@Override
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		MapTile mapTile = recordTile.getMapTile();
		if (mapTile instanceof LavaTrap) {
			LavaTrap lavaTile = (LavaTrap) mapTile;
			if (lavaTile.getKey() != 0 && !holdKey.contains(lavaTile.getKey())) {
				return true;
			}
		} 
		return false;
	}
}