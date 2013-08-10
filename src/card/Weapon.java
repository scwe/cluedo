package card;

/**
 * @author westonscot
 *
 * A class to represent the weapon within the game, has a name (Baseball Bat, Pistol, Poison, etc)
 * and an x, y Location
 */
public class Weapon implements Cardable {

	private String name;
	private Room room;

	public Weapon(String name, Room room){
		this.name = name;
		this.room = room;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public Room getRoom(){
		return room;
	}
	
	public void setRoom(Room room){
		this.room = room;
	}
	
	@Override
	public String toString(){
		return "Weapon "+name+" in "+room;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weapon other = (Weapon) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
