
public class Room implements Cardable {

	private String name;
	private Location location;

	public Room(String name, Location location){
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
