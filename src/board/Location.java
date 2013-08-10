package board;
/**
 *
 * @author westonscot
 *
 * Represents and x, y location on the board,
 */
public class Location {

	private int x;
	private int y;

	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(!(o instanceof Location)){
			return false;
		}

		Location l = (Location)o;

		return l.x == this.x && l.y == this.y;
	}
	
	@Override
	public String toString(){
		return "("+x+", "+y+")";
	}
}
