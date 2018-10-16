package mycontroller;

import tiles.MapTile;

public class RecordTile {
	private tiles.MapTile.Type tileType;
	private boolean explored;
	public RecordTile(MapTile mapTile){
		this.tileType = mapTile.getType();
		this.explored = true;
	}
	
	public tiles.MapTile.Type getType(){
		return tileType;}
	public boolean getExplored() {
		return explored;}
		
	public Boolean isType(MapTile.Type tileType) {
		return this.tileType == tileType;
	}
}
