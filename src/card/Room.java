package card;
import board.Location;


public class Room implements Cardable {

	private String name;
	private Location location;

	public Room(String name, Location location){   //TODO should this have a set of players and Weapons perhaps?
		this.name = name;						   //Otherwise it is going to be hard find what is where
		this.location = location;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public String toString(){
		return name + " at "+location;
	}


}
