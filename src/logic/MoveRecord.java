package logic;
import card.IntrigueCard;
import card.Room;


/**
 * A class the represents a Move Record, that is
 * whether they ahve picked up an IntrigueCard, and whether they have moved into a room
 * and whether they can accuse
 * @author scott
 *
 */
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
