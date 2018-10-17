package mycontroller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import controller.CarController;
import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;

public class MyAIController extends CarController{
	private HashMap<Coordinate, Integer> newKeyLocation;
	private RoutingStrategy routingStrategy;
	public MyAIController(Car car) {
		super(car);
		newKeyLocation = new HashMap<Coordinate, Integer>();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		HashMap<Coordinate, MapTile> currentView = getView();
		Coordinate currentPosition = new Coordinate(getPosition());
		findKeys(currentView);
		removeDuplicateKeys();
		ExploreMap.getInstance().updateMap(currentView);
		HashMap<Coordinate, RecordTile> myMap = ExploreMap.getInstance().getExploredMap();
		
		//not done
		/*if(!newKeyLocation.isEmpty()) {
			routingStrategy = new KeyMixStrategy(currentPosition, getOrientation(), getHealth(), newKeyLocation);
		} else if (getKeys().size() == numKeys()) {
			routingStrategy = new ExitMixStrategy(currentPosition, getOrientation(), getHealth());
		} else {
			routingStrategy = new ExploreStrategy(currentPosition, getOrientation());
		}*/
		
		routingStrategy = new ExploreStrategy(currentPosition, getOrientation()); //for testing
		
		routingStrategy.AstarPathFinding(); //to be continued....
		
		
		
	}
	
	//remove the key we already have
	private void removeDuplicateKeys() {
		Iterator iter = newKeyLocation.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Coordinate coordinate = (Coordinate) entry.getKey();
			int key = (int) entry.getValue();
			if (getKeys().contains(key)) {
				newKeyLocation.remove(coordinate);
			}
		}
	}
	
	//find new key in currentView(if any) and record its coordinate
	private void findKeys(HashMap<Coordinate, MapTile> currentView) {
		Iterator iter = currentView.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Coordinate coordinate = (Coordinate) entry.getKey();
			MapTile mapTile = (MapTile) entry.getValue();
			if (mapTile instanceof LavaTrap) {
				LavaTrap lavaTrap = (LavaTrap) mapTile;
				int key = lavaTrap.getKey();
				if((key != 0) && (!getKeys().contains(key))) {
					newKeyLocation.put(coordinate, key);
				}
			}
		}
	}

}
