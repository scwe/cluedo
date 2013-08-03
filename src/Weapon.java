/**
 * @author westonscot
 *
 * A class to represent the weapon within the game, has a name (Baseball Bat, Pistol, Poison, etc)
 * and an x, y Location
 */
public class Weapon implements Cardable {

	private String name;
	private Location location;

	public Weapon(String name, Location location){
		this.name = name;
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

	public Location getLocation(){
		return location;
	}

	@Override
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(!(o instanceof Weapon)){
			return false;
		}

		Weapon w = (Weapon)o;

		return w.name.equals(this.name) && w.location.equals(this.location);
	}

}
