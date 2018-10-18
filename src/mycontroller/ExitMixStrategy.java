package mycontroller;

import tiles.HealthTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

/**
 * The University of Melbourne
 * SWEN30006 Software Modelling and Design
 * FileName: ExitMixStrategy.java
 *
 *
 * This class contains a mix strategy to find the exit 
 * 
 *
 * @author  Chenyang Lu, Leewei Kuo, Xueting Tan
 * @StudentID 951933, 932975, 948775
 * @Username  chenyangl5, leeweik1, xuetingt
 * 
 * @Date  18/10/2018 
 */
public class ExitMixStrategy extends RoutingStrategy{
	private HealStrategy heal;
	private FindExitStrategy findExit;
	private float health;
	private MapTile currentMapTile;
	
	public ExitMixStrategy(Coordinate coordinate, Direction currentOrientation, float health) {
		super(coordinate, currentOrientation);
		heal = new HealStrategy(coordinate, currentOrientation);
		findExit = new FindExitStrategy(coordinate, currentOrientation);
		this.health = health;
		this.currentMapTile = ExploreMap.getInstance().getExploredMap().get(coordinate).getMapTile();
	}

	/*
	 * if the health is low, find the health trap
	 * otherwise find the exit
	 */
	@Override
	public boolean isGoal(Coordinate c) {
		int healDistance = heal.AstarPathFinding().size();
		
		if ((currentMapTile instanceof HealthTrap && (health < 100)) || 
				((health < 50) && (healDistance != 0) && (healDistance < findExit.AstarPathFinding().size()))) {
			return heal.isGoal(c);
		} else {
			return findExit.isGoal(c);
		}
	}
}
