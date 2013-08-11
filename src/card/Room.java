package card;
import board.Location;
import board.Door;
import board.TextBoard;
import board.Tile;

import java.util.*;

/**
 * A class to represent a room object,
 * keeps track of all the weapons, suspects currently in it,
 * also keeps track of all the locations in it and it's doors
 * @author scott
 *
 */
public class Room implements Cardable {

	private String name;
	
	private HashSet<Suspect> suspects;
	private HashSet<Weapon> weapons;
	private TextBoard board;
	private ArrayList<Door> doors;
	
	private HashSet<Location> locations;

	public Room(String data, TextBoard board){
		this.board = board;
		String[] split = data.trim().split(" ");
		this.name = split[0];
		
		suspects = new HashSet<Suspect>();
		weapons = new HashSet<Weapon>();
		doors = new ArrayList<Door>();
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
	
	public ArrayList<Door> getDoors(){
		return doors;
	}
	
	public HashSet<Suspect> getSuspects(){
		return suspects;
	}
	
	public HashSet<Weapon> getWeapons(){
		return weapons;
	}
	
	public Location addSuspect(Suspect s){
		
		for (Location loc : locations){
			if (board.getTile(loc).getSuspectOn() == null){
				suspects.add(s);
				return loc;
			}
		}
		return null;
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
	public String toString(){
		return name;
	}

	public HashSet<Location> getLocations() {
		return locations;
	}


}
