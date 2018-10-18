package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tiles.HealthTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

public class StrategyFactory {
	private RoutingStrategy routingStrategy;
	MyAIController controller;
	public StrategyFactory(MyAIController controller) {
		this.controller = controller;
	}
	
	public RoutingStrategy getStrategy( HashMap<Coordinate, Integer> newKeyLocation) {
		HashMap<Coordinate, MapTile> currentView = controller.getView();
		Coordinate currentPosition = new Coordinate(controller.getPosition());
		
		boolean heal = findHealthTrap(currentView);
		float health = controller.getHealth();
		ExploreMap.getInstance().updateMap(currentView);
		HashMap<Coordinate, RecordTile> myMap = ExploreMap.getInstance().getExploredMap();
		MapTile mapTile = myMap.get(currentPosition).getMapTile();
		Direction orientation = controller.getOrientation();
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
		if ((mapTile instanceof HealthTrap && (health < 100)) || ((health < 50) && heal)) {
			routingStrategy = new HealStrategy(currentPosition, orientation);
			temp  = routingStrategy.AstarPathFinding(); //to be continued....				
			if (temp.size() != 0) {
				return routingStrategy;
			}
		}
		
	
		if(!newKeyLocation.isEmpty()) {
			routingStrategy = new FindKeyStrategy(currentPosition, orientation, controller.getKeys());
			temp  = routingStrategy.AstarPathFinding();
			if (temp.size() != 0) {	
				return routingStrategy;
			}
		} 
	

		
		if (controller.getKeys().size() == controller.numKeys()) {
			routingStrategy = new ExitMixStrategy(currentPosition, orientation, health);
		} else {

			routingStrategy = new ExploreStrategy(currentPosition, orientation);
		}
		temp = routingStrategy.AstarPathFinding();
		if (temp.size() != 0) {	
			return routingStrategy;
		}
		
		return routingStrategy; //should never happen
	
	}
	
	
	//find health trap in currentView
	private boolean findHealthTrap(HashMap<Coordinate, MapTile> currentView) {
		Iterator iter = currentView.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Coordinate coordinate = (Coordinate) entry.getKey();
			MapTile mapTile = (MapTile) entry.getValue();
			if (mapTile instanceof HealthTrap) {
				return true;
			}
		}
		return false;
	}
}
