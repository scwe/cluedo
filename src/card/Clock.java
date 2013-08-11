package card;


/**
 * A class representing the clock IntrigueCard
 * @author scott
 *
 */
public class Clock implements IntrigueCard{
	
	private Boolean last;
	
	public Clock(Boolean last){
		this.last = last;
	}

	public Boolean getLast() {
		return last;
	}
	/**
	 * Whether this is the last Clock in the deck of intrigue Cards
	 */
	public void setLast(Boolean last) {
		this.last = last;
	}
	
	public String toString(){
		return "Clock card, Deadly: "+last;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clock other = (Clock) obj;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		return true;
	}
}
