
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
	
	@Override
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(! (o instanceof Character)){
			return false;
		}
		
		Character c = (Character)o;
		
		return c.name.equals(this.name) && c.location.equals(this.location);
	}

}
