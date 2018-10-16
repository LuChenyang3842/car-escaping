package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.Coordinate;
import world.WorldSpatial.Direction;

public abstract class RoutingStrategy {
	private Node currentNode;
	
	public RoutingStrategy(Coordinate coordinate, Direction currentOrientation) {
		currentNode = new Node(coordinate,currentOrientation);
	}
	
	
	public void getSuccessor(Node node){
		Coordinate currentCoordinate = node.coordinate;
		Direction currentOrientation = node.currentOrientation;
		RecordTile currentTile = ExploreMap.getInstance().getExploredMap().get(currentCoordinate);
		
		
		
		
	}
	
	
	public void findPath() {
		
		
	}
	
	public void heuristic() {
		
	}
	public abstract boolean isGoal(Coordinate c);
	
	
	
	class Node {
		Coordinate coordinate;
		Direction currentOrientation;
		
		Node(Coordinate coordinate, Direction currentOrientation){
			this.coordinate = coordinate;
			this.currentOrientation =currentOrientation;
		}
		
		
		
		
		
	}
	
	

}
