package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tiles.HealthTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

/**
 * The University of Melbourne
 * SWEN30006 Software Modelling and Design
 * FileName: StrategyFactory.java
 *
 *
 * This class is used to handle the complex logic of strategy, 
 * the getStrategy method will return a suitable strategy according
 * to the car's current location and the map information we have 
 * observed so far.
 *
 * @author  Chenyang Lu, Leewei Kuo, Xueting Tan
 * @StudentID 951933, 932975, 948775
 * @Username  chenyangl5, leeweik1, xuetingt
 * 
 * @Date  18/10/2018 
 */
public class StrategyFactory {
	private RoutingStrategy routingStrategy;
	private MyAIController controller;
	public StrategyFactory(MyAIController controller) {
		this.controller = controller;
	}
	
	/**
	 * 
	 * @param newKeyLocation the key location we have seen
	 * @return a strategy suitable for current circumstances
	 */
	public RoutingStrategy getStrategy( HashMap<Coordinate, Integer> keyLocation) {
		HashMap<Coordinate, MapTile> currentView = controller.getView();
		Coordinate currentPosition = new Coordinate(controller.getPosition());
		
		//is true when health trap in currentView
		boolean heal = findHealthTrap(currentView);
		float health = controller.getHealth();

		ExploreMap.getInstance().updateMap(currentView);
		HashMap<Coordinate, RecordTile> myMap = ExploreMap.getInstance().getExploredMap();
		MapTile mapTile = myMap.get(currentPosition).getMapTile();
		Direction orientation = controller.getOrientation();
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
		
		//heal when standing on health trap (until full health), or
		//if health < 50 and a health trap in view
		if ((mapTile instanceof HealthTrap && (health < 100)) || ((health < 50) && heal)) {
			routingStrategy = new HealStrategy(currentPosition, orientation);
			temp  = routingStrategy.AstarPathFinding(); 
			
			//check if a path exit
			if (temp.size() != 0) {
				return routingStrategy;
			}
		}
		
		//find key if keyLocation is not empty
		if(!keyLocation.isEmpty()) {
			routingStrategy = new FindKeyStrategy(currentPosition, orientation, controller.getKeys());
			temp  = routingStrategy.AstarPathFinding();
			
			//check if a path exit
			if (temp.size() != 0) {	
				return routingStrategy;
			}
		} 
	
		//find exit if we have all the keys, otherwise explore the map
		if (controller.getKeys().size() == controller.numKeys()) {
			routingStrategy = new ExitMixStrategy(currentPosition, orientation, health);
		} else {
			routingStrategy = new ExploreStrategy(currentPosition, orientation);
		}
		return routingStrategy; 
	
	}
		
	//find health trap in currentView, return true if there is one
	private boolean findHealthTrap(HashMap<Coordinate, MapTile> currentView) {
		Iterator iter = currentView.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			MapTile mapTile = (MapTile) entry.getValue();
			if (mapTile instanceof HealthTrap) {
				return true;
			}
		}
		return false;
	}
}
