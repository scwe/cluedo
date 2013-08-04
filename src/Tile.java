
public abstract class Tile {
	
	Location loc;
	Character chr;
	
	public Location getLocation(){
		return this.loc;
	}
	
	public void setLocation(Location location){
		this.loc = location;
	}
	
	public Character getCharacterOn(){
		return this.chr;
	}
	
	abstract public String getTileType();
	
	
}
