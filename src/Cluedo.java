import java.util.*;
import java.io.*;


public class Cluedo {
	public static int NUM_CARDS = 24;
	
	public static int NUM_ROOMS = 9;
	public static int NUM_WEAPONS = 9;
	public static int NUM_CHARACTERS = 6;
	
	private Queue<Player> players;   //tis a queue so we can sort out turns by polling
	private TextBoard board;
	
	private ArrayList<Character> characters;
	private ArrayList<Room> rooms;
	private ArrayList<Weapon> weapons;
	
	private Scanner inputScanner;


	public Cluedo(){
		board = new TextBoard();
		inputScanner = new Scanner(System.in);
		players = new LinkedList<Player>();
		
		
		System.out.println("Enter the number of players playing ");
		int numPlayers = inputScanner.nextInt();

		for(int i = 0; i < numPlayers; i++){
			Player player = null;
			while(player == null){
				player = readPlayer(inputScanner);
			}
			players.offer(player);
		}
		
		
	}
	

	public void createDeck() throws IOException{
		characters = new ArrayList<Character>();
		rooms = new ArrayList<Room>();
		weapons = new ArrayList<Weapon>();
		
		String[] lines = new Scanner(new File("characters.txt")).useDelimiter("\\Z").next().split("\n");
		
		for(String s : lines){
			characters.add(new Character(s, new Location(0,0))); //TODO change this to use that characters start location
		}
		
		lines = new Scanner(new File("rooms.txt")).useDelimiter("\\Z").next().split("\n");
		
		for(String s : lines){
			rooms.add(new Room(s, board.getRoomFromString(s)));
		}
		
		lines = new Scanner(new File("weapons.txt")).useDelimiter("\\Z").next().split("\n");
		
		for(String s : lines){
			weapons.add(new Weapon(s, new Location(0,0)));   //TODO fix this so it uses a proper location
		}
	}
	
	public Player readPlayer(Scanner input){
		
		System.out.println("Please select a character to play, your options are: ");
		
		for(Character c : characters){
			boolean used = false;
			for(Player p : players){
				if(p.getCharacter().equals(c)){
					used = true;
				}
			}
			if(!used){
				System.out.println("\t"+c.getName());
			}
		}
		
		String name = input.nextLine();  //read in the character they are going to use
		
		Player p = null;
		
		for(Character c : characters){
			if(c.getName().equals(name)){
				p = new Player(c, new ArrayList<Card>());
			}
		}
		return p;
	}
	
	public void shuffleAndDeal(){
		
	}

	public static void main(String[] args){
		new Cluedo();
	}
}
