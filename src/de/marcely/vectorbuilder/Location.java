package de.marcely.vectorbuilder;

public class Location implements Cloneable {
	
	public float x, y;
	
	public Location(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float distanceX(Location loc){
		return x > loc.x ? x - loc.x : loc.x - x;
	}
	
	public float distanceY(Location loc){
		return y > loc.y ? y - loc.y : loc.y - y;
	}
	
	public float distance(Location loc){
		return distanceX(loc) + distanceY(loc);
	}
	
	public Location add(float x, float y){
		Location loc = clone();
		
		loc.x += x;
		loc.y += y;
		
		return loc;
	}
	
	@Override
	public Location clone(){
		try{
			return (Location) super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		
		return null;
	}
}