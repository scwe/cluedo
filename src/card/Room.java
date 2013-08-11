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

	public Room(String data, TextBoard board){
		String[] split = data.trim().split(" ");
		this.name = split[0];
		this.location = board.getRoomFromString(name);
		System.out.println("------"+name+"-----------");
		
		suspects = new HashSet<Suspect>();
		weapons = new HashSet<Weapon>();
		doors = new HashSet<Door>();
		
		for(int i = 1; i < split.length;){
			int x = Integer.parseInt(split[i++].trim());
			int y = Integer.parseInt(split[i++].trim());
			System.out.println(x+" "+y);
			
			Tile t = board.getTile(new Location(x, y));
			System.out.println(t.getLocation());
			if(t instanceof Door){
				doors.add((Door)t);
			}else{
				System.out.println(t.getLocation());
				System.out.println("Something went horribly wrong");
			}
		}
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
