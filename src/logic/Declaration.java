package logic;
import card.Suspect;
import card.Room;
import card.Weapon;

/**
 * A class to represent a declaration in the game,
 * that is an accusation or the solution or if in the pool
 * and you think you have an ansewer.
 * Essentially a tuple of Weapon, Suspect and Room
 * @author CF
 *
 */
public class Declaration {
	
	private Suspect suspect;
	private Weapon weapon;
	private Room room;
	private Room accusedRoom;
	
	public Declaration(Suspect suspect, Weapon weapon, Room room){
		this.weapon = weapon;
		this.suspect = suspect;
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Suspect getSuspect() {
		return suspect;
	}

	public void setSuspect(Suspect suspect) {
		this.suspect = suspect;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public void setAccusedRoom(Room rm){
		this.accusedRoom = rm;
	}
	
	public Room getAccusedRoom(){
		return this.accusedRoom;
	}

}
