package card;
import board.Location;

/**
 * The Suspect class represents a character on the board, and can also be
 * put into a card and held in some hand or deck
 * @author scott
 *
 */
public class Suspect implements Cardable{

	private String name;
	private Location location;

	public Suspect(String name, Location location){
		this.name = name;
		this.location = location;
	}

	@Override
	public String getName() {
		return name;
	}

	public Location getLocation(){
		return location;
	}

	public void setLocation(Location location){
		this.location = location;
	}
	
	@Override
	public String toString(){
		if(location == null){
			return name+" who is nowhere";
		}else{
			return name+" at "+location.toString();
		}
		
	}
	public char getShortName(){
		if (getName().equals("Jack Mustard")){
			return 'M';
		}
		return getName().toUpperCase().charAt(0);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Suspect other = (Suspect) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
