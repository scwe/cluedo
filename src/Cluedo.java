import java.util.ArrayList;


public class Cluedo {
	private Cardable[] deck = {   //location is the inside of the door
		new Room("SPA", new Location(5, 5))
	};
	private ArrayList<Player> players;


	public Cluedo(){
		int numPlayers = 4;

		for(int i = 0; i < 4; i++){

		}
	}

	public static void main(String[] args){
		new Cluedo();
	}
}
