import java.util.*;
import java.io.*;

public class Cluedo {

	private Queue<Player> players; // tis a queue so we can sort out turns by polling
	private TextBoard board;

	private ArrayList<Character> characters;
	private ArrayList<Room> rooms;
	private ArrayList<Weapon> weapons;
	
	private Stack<Card> deck;
	
	private Announcement solution;

	private Scanner inputScanner;

	public Cluedo() {
		board = new TextBoard();
		inputScanner = new Scanner(System.in);
		players = new LinkedList<Player>();
		try {
			deck = createDeck();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Enter the number of players playing ");
		int numPlayers = inputScanner.nextInt();
		inputScanner.nextLine();
		
		System.out.println("Preparing your game...");
		sleep(1000);
		
		
		//inputScanner.next();   //read off any white space
		for (int i = 0; i < numPlayers; i++) {
			Player player = null;
			System.out.println("Time for player "+(i+1)+" to choose who they will be");
			boolean firstTime = true;
			while (player == null) {
				if(!firstTime){
					System.out.println("That entry was invalid. Please try again.");
				}
				player = readPlayer(inputScanner);
				firstTime = false;
			}
			System.out.println("Great choice!\n");
			sleep(500);
			player.getCharacter().setLocation(board.getStartLocation(i));
			players.offer(player);
		}
		
		solution = createSolution(deck);
		dealDeck(deck, players);
		shuffleWeapons(weapons);
		
		for(Player p : players){
			System.out.println(p);
		}
		
		for(Weapon w : weapons){
			System.out.println(w);
		}
		
		System.out.println(solution);
	}
	
	/**
	 * Gets input from a specified scanner and creates a player object based on
	 * what the user decides to choose
	 * 
	 * @param input
	 *            The scanner to use
	 * @return
	 *         returns the newly created player
	 */
	public Player readPlayer(Scanner input) {

		System.out.println("Please enter the number of the character you wish to play.\nYour options are: ");

		ArrayList<Character> numList = new ArrayList<Character>();
		int count = 1;
		for (Character c : characters) { // print out the possible character
			boolean used = false;
			for (Player p : players) {
				if (p.getCharacter().equals(c)) {
					used = true;
				}
			}
			if (!used) {
				System.out.println(count+"\t" + c.getName());
				numList.add(c);
				count++;
			}
		}

		String name = input.nextLine(); // read in the character they are going to use
		Player p = null;
		int val = -1;
		try{
			val = Integer.parseInt(name.trim())-1;
		}catch(Exception e){
			return p;
		}
		
		if (val < 0 || val >= numList.size()) return p;

		p = new Player(numList.get(val), new ArrayList<Card>());
		return p;
	}

	/**
	 * Reads in data from 3 files and generates a deck based on them
	 * 
	 * @throws IOException
	 */
	public Stack<Card> createDeck() throws IOException {
		characters = new ArrayList<Character>();
		rooms = new ArrayList<Room>();
		weapons = new ArrayList<Weapon>();
		
		Stack<Card> deck = new Stack<Card>();

		String[] lines = new Scanner(new File("characters.txt")).useDelimiter("\\Z").next().split("\n");

		for (String s : lines) {
			Character c = new Character(s.trim(), null);
			characters.add(c);
			deck.push(new Card(c));
		}

		lines = new Scanner(new File("rooms.txt")).useDelimiter("\\Z").next().split("\n");

		for (String s : lines) {
			Room r = new Room(s.trim(), board.getRoomFromString(s));
			rooms.add(r);
			deck.push(new Card(r));
		}
		
		lines = new Scanner(new File("weapons.txt")).useDelimiter("\\Z").next().split("\n");

		for (String s : lines) {
			Weapon w = new Weapon(s.trim(), null);
			weapons.add(w); 
			deck.push(new Card(w));
		}
		
		Collections.shuffle(deck);   //shuffle the cards
		return deck;
	}

	

	/**
	 * Shuffles the deck given and then deals it out to the players give
	 * @param deck
	 * 		The deck to deal
	 * @param players
	 * 		The Players to deal to 
	 */
	public void dealDeck(Stack<Card> deck, Queue<Player> players){
		
		Player startPlayer = players.peek();

		while(!deck.isEmpty()){		//while there are still cards, take a player off the turn queue 
			Player p = players.poll();
			
			p.addCard(deck.pop());		//and add a card to its hand then put it on the back
			
			players.offer(p);
		}
		
		while(players.peek() != startPlayer){   //to make sure the queue order remains the same
			players.offer(players.poll());   //take that person off and put them at the back of the queue
		}
	}
	
	public void shuffleWeapons(ArrayList<Weapon> weapons){
		Collections.shuffle(weapons);   //put the weapons in a random order
		int count = 0;
		for(Weapon w : weapons){   //put each one in a room
			w.setRoom(rooms.get(count));
			count++;
		}
	}
	
	/**
	 * Creates the solution to this game,
	 * @param deck
	 * 		The deck of cards to use to find a solution from
	 * @return
	 * 		The solution to the game, represented in an announcement object
	 */
	private Announcement createSolution(Stack<Card> deck){
		Card room = null;
		Card player = null;
		Card weapon = null;
		
		for(Card c: deck){
			if(c.getCard() instanceof Character){
				player = c;
			}else if(c.getCard() instanceof Room){
				room = c;
			}else if(c.getCard() instanceof Weapon){
				weapon = c;
			}
		}
		
		deck.remove(room);
		deck.remove(weapon);
		deck.remove(player);
		
		return new Announcement(room, player, weapon);
	}

	private void sleep (int val){
		try {
		    Thread.sleep(val);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	public static void main(String[] args) {
		new Cluedo();
	}
	
	
}
