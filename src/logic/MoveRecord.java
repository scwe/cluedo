package logic;
import card.IntrigueCard;
import card.Room;

public class MoveRecord {
	
	
	private IntrigueCard ic;
	private Room rm;
	private boolean canAccuse;
	
	public IntrigueCard getIc() {
		return ic;
	}
	public void setIc(IntrigueCard ic) {
		this.ic = ic;
	}
	public Room getRm() {
		return rm;
	}
	public void setRm(Room rm) {
		this.rm = rm;
	}
	public boolean canAccuse() {
		return canAccuse;
	}
	public void setCanAccuse(boolean canAccuse) {
		this.canAccuse = canAccuse;
	}
}
