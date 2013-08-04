import java.util.ArrayList;


public class Cluedo {
	private Cardable[] roomDeck = {   //location is the inside of the door
		new Room("SPA", new Location(5, 5)), new Room("Theatre", new Location(11,5)),
				new Room("Conservatory", new Location(25,5)), new Room("Patio",new Location(5,15)),
				new Room("Pool", new Location( 14,15)), new Room("Hall",new Location(25,15)), 
				new Room("Kitchen", new Location(5,26)), new Room("Dining Room", new Location(13,26)),
				new Room("Guest House", new Location(27,26))
	};
	private ArrayList<Character> characterDeck;


	public Cluedo(){
		int numPlayers = 4;

		for(int i = 0; i < 4; i++){

		}
	}

	public static void main(String[] args){
		new Cluedo();
	}
}
