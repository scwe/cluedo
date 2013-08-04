import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class Cluedo {
	public static int NUM_CARDS = 24;
	
	public static int NUM_ROOMS = 9;
	public static int NUM_WEAPONS = 9;
	public static int NUM_CHARACTERS = 6;
	
	private ArrayList<Player> players;
	private TextBoard board;


	public Cluedo(){
		int numPlayers = 4;

		for(int i = 0; i < 4; i++){
			
		}
	}
	
	public ArrayList<Card<Cardable>> readDeck(String filename) throws IOException{
		ArrayList<Card<Cardable>> deck = new ArrayList<Card<Cardable>>(NUM_CARDS);
		
		String[] lines = new Scanner(new File(filename)).useDelimiter("\\Z").next().split("\n");   //reads in the whole file and then splits into seperate lines
		
		int count = 0;
		for(String s: lines){
			if(count < NUM_ROOMS){
				deck.add(new Card<Cardable>(new Room(s, new Location(0,0))));  //TODO Location can't be 0,0 we need to change this so the board can give
			}else if(count < NUM_WEAPONS){									   //TODO us the location of a particular room
				deck.add(new Card<Cardable>(new Weapon(s, new Location(0, 0))));
			}else if(count < NUM_CHARACTERS){
				deck.add(new Card<Cardable>(new Character(s, new Location(0,0))));
			}
			count++;
		}
		
		
		
		return null;
	}

	public static void main(String[] args){
		new Cluedo();
	}
}
