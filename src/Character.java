
public class Character implements Cardable{

	private String name;
	private Location location;

	public Character(String name, Location location){
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

	public void setLocation(Location location){
		this.location = location;
	}

}
