package card;
import board.Location;
import board.Door;

import java.util.HashSet;


public class Room implements Cardable {

	private String name;
	private Location location;
	
	private HashSet<Suspect> suspects;
	private HashSet<Weapon> weapons;
	
	private HashSet<Door> doors;

	public Room(String name, Location location){
		this.name = name;
		this.location = location;
		
		suspects = new HashSet<Suspect>();
		weapons = new HashSet<Weapon>();
		doors = new HashSet<Door>();
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

}
