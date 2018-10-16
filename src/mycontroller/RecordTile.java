package mycontroller;

import tiles.MapTile;

public class RecordTile {

	private MapTile mapTile;
	private boolean explored;
	public RecordTile(MapTile mapTile){
		this.mapTile = mapTile;
		this.explored = true;
	}
	
	public MapTile getMapTile(){
		return mapTile;}
	public boolean getExplored() {
		return explored;}
    
    public void setExplored(boolean b) {
        this.explored = b;
    }
}