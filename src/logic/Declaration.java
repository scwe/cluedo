package logic;
import card.Card;
import card.Suspect;
import card.Room;
import card.Weapon;


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
