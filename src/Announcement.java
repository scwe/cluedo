
public class Announcement {
	
	private Player p;
	private Card room;
	private Card character;
	private Card weapon;
	
	public Announcement(Player p, Card room, Card character, Card weapon){
		this.p = p;
		this.room = room;
		this.character = character;
		this.weapon = weapon;
	}
	
	public boolean isValid(){
		return room.getCard() instanceof Room && character.getCard() instanceof Character && weapon.getCard() instanceof Weapon;
	}
	
	public Card getRoom() {
		return room;
	}
	
	public void setRoom(Card room) {
		this.room = room;
	}

	public Card getCharacter() {
		return character;
	}

	public void setCharacter(Card character) {
		this.character = character;
	}

	public Card getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
}
