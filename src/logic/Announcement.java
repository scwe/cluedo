package logic;
import card.Card;
import card.Suspect;
import card.Room;
import card.Weapon;


public class Announcement {
	
	

	private Suspect suspect;
	private Weapon weapon;
	private Room room;
	
	public Announcement(Suspect suspect, Weapon weapon, Room room){
		this.room = room;
		this.weapon = weapon;
		this.suspect = suspect;
		
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


	public Room getRoom() {
		return room;
	}


	public void setRoom(Room room) {
		this.room = room;
	}


	
}
