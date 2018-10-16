package mycontroller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import world.World;
import tiles.MapTile;
import utilities.Coordinate;

public class ExploreMap {
	private HashMap<Coordinate, RecordTile> newMap;
	public static ExploreMap instance;
	
	public ExploreMap() {
		getInitialMap();
	}
	
	public static synchronized ExploreMap getInstance() {
		if(instance == null) {
			instance = new ExploreMap();
			return instance;
		}else {
			return instance;
		}
	}
	
	public synchronized void updateMap(HashMap<Coordinate, MapTile> currentView) {
		Iterator iter = currentView.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Coordinate key = (Coordinate) entry.getKey();
			MapTile val = (MapTile) entry.getValue();
			if(!newMap.containsKey(key)) {
				RecordTile rt = new RecordTile(val);
				newMap.put(key, rt);
			}
		}
	}
	
	private synchronized void getInitialMap(){
		HashMap<Coordinate, MapTile> map = World.getMap();
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Coordinate key = (Coordinate) entry.getKey();
			MapTile val = (MapTile) entry.getValue();
			RecordTile rt = new RecordTile(val);
			newMap.put(key, rt);
		}
	}
	
	public synchronized HashMap<Coordinate, RecordTile> getExploredMap(){
		return newMap;
	}
	
}
