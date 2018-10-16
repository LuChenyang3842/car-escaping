package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.Direction;

public abstract class RoutingStrategy {
	private Node startNode;
	private final static int ACTION_COST = 1;

	public RoutingStrategy(Coordinate coordinate, Direction currentOrientation) {
		startNode = new Node(coordinate, currentOrientation);
	}

	/*
	 * use A star algorithm to find path
	 * would return an arrayList of coordinate to get to goal state
	 * if goal state is not reachable, return an empty arrayList<Coordinate>
	 * if startNode is goal, return arrayList that only contain start coordinate
	 */
	public ArrayList<Coordinate> AstarPathFinding() {
		PriorityQueue<Node> openList = new PriorityQueue<>();
		ArrayList<Coordinate> closedList = new ArrayList<Coordinate>();
		openList.add(startNode);
		if (isGoal(startNode.getCoordinate())) {
			ArrayList<Coordinate>  stop = new ArrayList<Coordinate>();
			stop.add(startNode.getCoordinate());
			return stop;
		}
		
		while (!openList.isEmpty()) {
			Node currentNode = openList.remove();
			int currentCost = currentNode.getG();
			ArrayList<Coordinate> currentPathToTheNode = currentNode.getpathTotheNode();
			if (closedList.indexOf(currentNode.getCoordinate()) == -1) {
				closedList.add(currentNode.getCoordinate());
				if (isGoal(currentNode.getCoordinate())) {
					return currentPathToTheNode;
				}
				ArrayList<Node> successors = getSuccessor(currentNode);
				for (Node successorNode : successors) {
					int g = currentNode.getG() + ACTION_COST;
					int h = heuristic(successorNode.getCoordinate());
					if (closedList.indexOf(successorNode.getCoordinate()) == -1) {
						currentPathToTheNode = currentNode.getpathTotheNode(); // get an new deep copy of array
						currentPathToTheNode.add(successorNode.getCoordinate());
						int f = g + h ;
						successorNode.update(g,f,currentPathToTheNode);
						openList.add(successorNode);
					}
				}

			}

		}
		return new ArrayList<Coordinate>();
	}

	public ArrayList<Node> getSuccessor(Node node) {
		Coordinate currentCoordinate = node.coordinate;
		int x = currentCoordinate.x;
		int y = currentCoordinate.y;
		Direction currentOrientation = node.currentOrientation;
		MapTile currentTile = ExploreMap.getInstance().getExploredMap().get(currentCoordinate).getMapTile();
		ArrayList<Node> successor = new ArrayList<Node>();
		ArrayList<Direction> legalActions = getLegalDirections(node);

		for (Direction direction : legalActions) {
			Coordinate successorCoordinate;
			switch (direction) {
			case NORTH:
				successorCoordinate = new Coordinate(x, y + 1);
				break;
			case SOUTH:
				successorCoordinate = new Coordinate(x, y - 1);
				break;
			case EAST:
				successorCoordinate = new Coordinate(x + 1, y);
				break;
			case WEST:
				successorCoordinate = new Coordinate(x - 1, y);
				break;
			default:
				successorCoordinate = new Coordinate(x, y); // should never happen
			}
			MapTile successorTile = ExploreMap.getInstance().getExploredMap().get(successorCoordinate).getMapTile();
			if (!successorTile.isType(MapTile.Type.WALL)) {
				if (successorTile.isType(MapTile.Type.TRAP) && ((TrapTile) currentTile).getTrap().equals("mud")) {
					continue; // if MapTile type is mud, then it can not be added into successor
				}
				Node successorNode = new Node(successorCoordinate, direction);
				successor.add(successorNode);
			}

		}

		return successor;

	}

	public ArrayList<Direction> getLegalDirections(Node node) {
		ArrayList<Direction> legalActions = new ArrayList<Direction>();
		Coordinate currentCoordinate = node.coordinate;
		Direction currentOrientation = node.currentOrientation;
		MapTile currentTile = ExploreMap.getInstance().getExploredMap().get(currentCoordinate).getMapTile();
		// if the MapTile type is grass, then the legal action would only be current
		// action/ reverse action
		if (currentTile.isType(MapTile.Type.TRAP) && ((TrapTile) currentTile).getTrap().equals("grass")) {
			legalActions.add(currentOrientation);
			legalActions.add(WorldSpatial.reverseDirection(currentOrientation));
			return legalActions;

		}
		legalActions.add(Direction.SOUTH);
		legalActions.add(Direction.NORTH);
		legalActions.add(Direction.EAST);
		legalActions.add(Direction.WEST);
		return legalActions;
	}

	public int heuristic(Coordinate coordinate) {
		MapTile currentTile = ExploreMap.getInstance().getExploredMap().get(coordinate).getMapTile();
		if (currentTile.isType(MapTile.Type.TRAP) && ((TrapTile) currentTile).getTrap().equals("lava"))
			return 100; // if that's Lava
		return 0;
	}

	public abstract boolean isGoal(Coordinate c);

	class Node implements Comparable<Node> {
		Coordinate coordinate;
		Direction currentOrientation;
		ArrayList<Coordinate> pathTotheNode = new ArrayList<Coordinate>();
		int g = 0;
		int f = 0;

		Node(Coordinate coordinate, Direction currentOrientation) {
			this.coordinate = coordinate;
			this.currentOrientation = currentOrientation;
		}

		Node(Coordinate coordinate, Direction currentOrientation, int g, int f) {
			this.coordinate = coordinate;
			this.currentOrientation = currentOrientation;
			this.g = g;
			this.f = f;
		}

		// return a deep copy of arrayList
		ArrayList<Coordinate> getpathTotheNode() {
			ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
			if (pathTotheNode.size() > 0) {
				for (Coordinate coordinate : pathTotheNode) {
					temp.add(coordinate);
				}
			}
			return temp;
		}
		
		void update(int g, int f, ArrayList<Coordinate> pathTotheNode) {
			this.g= g;
			this.f = f;
			this.pathTotheNode = pathTotheNode;
			
		}

		int getF() {
			return f;
		}

		int getG() {
			return g;
		}

		Coordinate getCoordinate() {
			return coordinate;
		}

		Direction getCurrentDirection() {
			return currentOrientation;
		}

		@Override
		public int compareTo(Node node) {
			if (this.getF() > node.getF()) {
				return 1;
			} else if (this.getF() < node.getF()) {
				return -1;
			} else {
				return 0;
			}
		}

	}

}
