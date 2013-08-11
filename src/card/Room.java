package card;
import board.Location;
import board.Door;
import board.TextBoard;
import board.Tile;

import java.util.HashSet;


public class Room implements Cardable {

	private String name;
	private Location location;
	
	private HashSet<Suspect> suspects;
	private HashSet<Weapon> weapons;
	
	private HashSet<Door> doors;
	
	private HashSet<Location> locations;

	public Room(String data, TextBoard board){
		String[] split = data.trim().split(" ");
		this.name = split[0];
		this.location = board.getRoomFromString(name);
		
		suspects = new HashSet<Suspect>();
		weapons = new HashSet<Weapon>();
		doors = new HashSet<Door>();
		locations = new HashSet<Location>();
		
		for(int i = 1; i < split.length;){
			int x = Integer.parseInt(split[i++].trim());
			int y = Integer.parseInt(split[i++].trim());
			
			Tile t = board.getTile(new Location(x, y));
			if(t instanceof Door){
				doors.add((Door)t);
			}else{
				System.out.println("Something went horribly wrong");
			}
		}
	}
	
	public void addLocation(Location l){
		locations.add(l);
	}
	
	public HashSet<Door> getDoors(){
		return doors;
	}
	
	public HashSet<Suspect> getSuspects(){
		return suspects;
	}
	
	public HashSet<Weapon> getWeapons(){
		return weapons;
	}
	
	public void addSuspect(Suspect s){
		suspects.add(s);
	}
	
	public void addDoor(Door d){
		doors.add(d);
	}
	
	public void addWeapon(Weapon w){
		weapons.add(w);
	}
	
	public void removeSuspect(Suspect s){
		suspects.remove(s);
	}
	
	public void removeDoor(Door d){
		doors.remove(d);
	}
	
	public void removeWeapon(Weapon w){
		weapons.remove(w);
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

	public HashSet<Location> getLocations() {
		return locations;
	}

	public void setLocations(HashSet<Location> locations) {
		this.locations = locations;
	}

}
