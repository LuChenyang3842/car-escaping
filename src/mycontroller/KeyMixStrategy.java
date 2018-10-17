package mycontroller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tiles.HealthTrap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

//a mix strategy to find the key
public class KeyMixStrategy extends RoutingStrategy{
	FindKeyStrategy findKey;
	ExploreStrategy explore;
	HealStrategy heal;
	float health;
	HashMap<Coordinate, Integer> newKeyLocation;
	
	public KeyMixStrategy(Coordinate currentPosition, Direction orientation, float health,
			HashMap<Coordinate, Integer> newKeyLocation) {
		super(currentPosition, orientation);
		this.health = health;
		this.newKeyLocation = newKeyLocation;
		findTargetKey(currentPosition, orientation);
		explore = new ExploreStrategy(currentPosition, orientation);
		heal = new HealStrategy(currentPosition, orientation);
	}
	
	/*
	 * if the health is low, find the health trap
	 * find key if the key is reachable
	 * otherwise explore new section of map
	 */
	@Override
	public boolean isGoal(Coordinate c) {
		RecordTile recordTile = ExploreMap.getInstance().getExploredMap().get(c);
		MapTile mapTile = recordTile.getMapTile();
		int healDistance = heal.AstarPathFinding().size();
		if ((mapTile instanceof HealthTrap && (health < 100)) || 
				((health < 30) && (findKey != null) && (healDistance != 0) && (healDistance < findKey.AstarPathFinding().size()))) {
			return heal.isGoal(c);
		} else if (findKey != null){
			return findKey.isGoal(c);
		} else {
			return explore.isGoal(c);
		}
	}
	
	public void findTargetKey(Coordinate currentPosition, Direction orientation) {
		Iterator iter = newKeyLocation.entrySet().iterator();
		FindKeyStrategy best = null;
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Coordinate coordinate = (Coordinate) entry.getKey();
			int key = (int) entry.getValue();
			FindKeyStrategy temp = new FindKeyStrategy(currentPosition, orientation, key);
			
			//unreachable
			if (temp.AstarPathFinding().size() == 0) {
				newKeyLocation.remove(coordinate);
			} else if (best == null) {
				best = temp;
				
			//need get cost function!!!
			} else if (temp.AstarPathFinding().size() < best.AstarPathFinding().size()) {
				best = temp;
			}
		}
		
		if (best != null) {
			findKey = best;
		}
	}

}
