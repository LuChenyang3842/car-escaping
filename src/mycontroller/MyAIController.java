package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import controller.CarController;
import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial.Direction;

/**
 * The University of Melbourne
 * SWEN30006 Software Modelling and Design
 * FileName: MyAIController.java
 *
 *
 * This class is used handle the control of car
 * 
 * @author  Chenyang Lu, Leewei Kuo, Xueting Tan
 * @StudentID 951933, 932975, 948775
 * @Username  chenyangl5, leeweik1, xuetingt
 * 
 * @Date  18/10/2018 
 */
public class MyAIController extends CarController{
	private HashMap<Coordinate, Integer> keyLocation;
	private RoutingStrategy routingStrategy;
	private StrategyFactory factory;
	private final int FIRST_STEP = 0;
	public MyAIController(Car car) {
		super(car);
		keyLocation = new HashMap<Coordinate, Integer>();
		factory = new StrategyFactory(this);
	}

	@Override
	public void update() {
		
		//update keyLocation with currentView
		findKeys(getView());
		
		//update ExploreMap with currentView
		ExploreMap.getInstance().updateMap(getView());
		
		//find strategy with StrategyFactory
		routingStrategy = factory.getStrategy(keyLocation);
		
		//find the path using the chosen Strategy
		ArrayList<Coordinate> path = routingStrategy.AstarPathFinding();
		
		//drive 1 step
		drive(new Coordinate(getPosition()), path.get(FIRST_STEP));
	}
	
	//drive from start coordinate to goal coordinate
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
					keyLocation.put(coordinate, key);
				}
			}
		}
	}	
}