package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import controller.CarController;
import tiles.HealthTrap;
import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial.Direction;

public class MyAIController extends CarController{
	private HashMap<Coordinate, Integer> newKeyLocation;
	private RoutingStrategy routingStrategy;
	private int i = 0;
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
		boolean heal = findHealthTrap(currentView);
		//removeDuplicateKeys();
		ExploreMap.getInstance().updateMap(currentView);
		HashMap<Coordinate, RecordTile> myMap = ExploreMap.getInstance().getExploredMap();
		MapTile mapTile = myMap.get(currentPosition).getMapTile();
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
		
		if ((mapTile instanceof HealthTrap && (getHealth() < 100)) || ((getHealth() < 50) && heal)) {
			routingStrategy = new HealStrategy(currentPosition, getOrientation());
			temp  = routingStrategy.AstarPathFinding(); //to be continued....				
			if (temp.size() != 0) {
				drive(currentPosition, temp.get(0));
			}
		}
		
		if (temp.size() == 0) {
			if(!newKeyLocation.isEmpty()) {
				System.out.println(4);
				//routingStrategy = new KeyMixStrategy(currentPosition, getOrientation(), getHealth(), newKeyLocation);			
				routingStrategy = new FindKeyStrategy(currentPosition, getOrientation(), getKeys());
			} else if (getKeys().size() == numKeys()) {
				System.out.println(5);
				routingStrategy = new ExitMixStrategy(currentPosition, getOrientation(), getHealth());
			} else {
				System.out.println(6);
				routingStrategy = new ExploreStrategy(currentPosition, getOrientation());
			}
			temp  = routingStrategy.AstarPathFinding();
			if (temp.size() != 0) {	
				drive(currentPosition, temp.get(0));
			}
		}
		
		
		//routingStrategy = new ExploreStrategy(currentPosition, getOrientation()); //for testing
		if (getSpeed() == 0 && getHealth() == 100) {
			applyForwardAcceleration();
		}
		if (temp.size() == 0) {
			if (getKeys().size() == numKeys()) {
				System.out.println(7);
				routingStrategy = new ExitMixStrategy(currentPosition, getOrientation(), getHealth());
			} else {
				System.out.println(8);
				routingStrategy = new ExploreStrategy(currentPosition, getOrientation());
			}
			temp  = routingStrategy.AstarPathFinding();
			if (temp.size() != 0) {	
				drive(currentPosition, temp.get(0));
			}
		}
		//temp  = routingStrategy.AstarPathFinding(); //to be continued....				
		//drive(currentPosition, temp.get(0));
		
		//System.out.println (temp.get(0));
		System.out.println("i:              "+ i);
		i++;
		
		
		
		
	}
	
	
	private void drive(Coordinate start, Coordinate goal) {
		int startX =  start.x;
		int startY = start.y;
		int goalX = goal.x;
		int goalY = goal.y;
		if (goalX == startX + 1) {
			goEast();
		}
		else if (goalX == startX - 1) {
			goWest();
		}
		else if(goalY == startY+ 1) {
			goNorth();
		}else if (goalY == startY- 1) {
			goSouth();
		}else {
			applyBrake();
		}
		
	}
	
	private void goSouth() {
		Direction currentOrientation = getOrientation();
		switch(currentOrientation){
		case EAST:
			turnRight();
			break;
		case NORTH:
			applyReverseAcceleration();
			break;
		case SOUTH:
			applyForwardAcceleration();
			break;
		case WEST:
			turnLeft();
			break;
		default:
			applyBrake();
			break;
		}
		
		// TODO Auto-generated method stub
		
	}

	private void goNorth() {
		Direction currentOrientation = getOrientation();
		switch(currentOrientation){
		case EAST:
			turnLeft();
			break;
		case NORTH:
			applyForwardAcceleration();
			break;
		case SOUTH:
			applyReverseAcceleration();
			break;
		case WEST:
			turnRight();
			break;
		default:
			applyBrake();
			break;
		}
		
	}

	private void goEast() {
		Direction currentOrientation = getOrientation();
		switch(currentOrientation){
		case EAST:
			applyForwardAcceleration();
			break;
		case NORTH:
			turnRight();
			break;
		case SOUTH:
			turnLeft();
			break;
		case WEST:
			applyReverseAcceleration();
			break;
		default:
			applyBrake();
			break;
		}
		
	}

	private void goWest() {
		Direction currentOrientation = getOrientation();
		switch(currentOrientation){
		case EAST:
			applyReverseAcceleration();
			break;
		case NORTH:
			turnLeft();
			break;
		case SOUTH:
			turnRight();
			break;
		case WEST:
			applyForwardAcceleration();
			break;
		default:
			applyBrake();
			break;
		}
		
	}
	
	//remove the key we already have
	/*private void removeDuplicateKeys() {
		Iterator iter = newKeyLocation.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Coordinate coordinate = (Coordinate) entry.getKey();
			int key = (int) entry.getValue();
			if (getKeys().contains(key)) {
				newKeyLocation.remove(coordinate);
			}
		}
	}*/
	
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
	
	//find new key in currentView(if any) and record its coordinate
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
