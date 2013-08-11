package main;

import java.util.*;
import java.io.*;

import logic.Announcement;

import card.*;

import board.Location;
import board.Path;
import board.TextBoard;


public class Cluedo {

	private Queue<Player> players;
	private TextBoard board;

	private ArrayList<Suspect> suspects;
	private ArrayList<Room> rooms;
	private ArrayList<Weapon> weapons;

	private Deck<Card> deck;
	private Deck<IntrigueCard> intrigueDeck;

	private Announcement solution;

	private Scanner inputScanner;

	public Cluedo() {
		
		board = new TextBoard();
		inputScanner = new Scanner(System.in);
		players = new LinkedList<Player>();
		try {
			deck = createDeck();
			intrigueDeck = createIntrigueDeck();
			solution = createSolution(deck);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Enter the number of players playing");
		int numPlayers = inputScanner.nextInt();
		inputScanner.nextLine();

		System.out.println("Preparing your game...");
		sleep(1000);
		for (int i = 0; i < numPlayers; i++) {
			Player player = null;
			System.out.println("Time for player " + (i + 1) + " to choose who they will be");
			boolean firstTime = true;
			while (player == null) {
				if (!firstTime) {
					System.out.println("That entry was invalid. Please try again.");
				}
				player = readPlayer(inputScanner);
				firstTime = false;
			}
			System.out.println("Great choice!\n");
			sleep(500);
			player.getCharacter().setLocation(board.getStartLocation(i));
			player.setPlayerNumber(i+1);
			Location playLoc = board.getStartLocation(player.getPlayerNumber()-1);

			board.getTile(playLoc).setPlayerOn(player);
			players.offer(player);
		}

		
		deck.deal(players);
		shuffleWeapons(weapons);

		for (Player p : players) {
			System.out.println(p);
			
		}

		for (Weapon w : weapons) {
			System.out.println(w);
			sleep(200);
		}
		
		boolean gameFinished = false;
		
		while (!gameFinished){
			Player curPlayer = players.poll();
			
			takeTurn(curPlayer);
				
			players.offer(curPlayer);
		}

	}
	
	public void takeTurn(Player p){
		
		System.out.println("Player "+p.getPlayerNumber()+": "+p.getCharacter().getName()+
				" ("+p.getShortName()+")");

		boolean validOption = false;
		int playerChoice = 0;
		
		while(!validOption){
			System.out.println("Options: ");
			System.out.println("(1)	Roll dice");
			System.out.println("(2)  Make Accusation");
			if (p.hasIntrigueCards()){
				System.out.println("(3)  Play intrigue card");
			}
			int okRange = (p.hasIntrigueCards())?3:2;
			Scanner optionScan = new Scanner(System.in);
			String decision = optionScan.next();
			try{
				playerChoice = Integer.parseInt(decision);

			}catch (Exception e){
				System.out.println("Sorry, that option was not valid");
				validOption = false;
			}
			if (playerChoice > 0 && playerChoice <= okRange){
				validOption = true;
			}
		}
		if (playerChoice == 1){
			selectDirection(rollDice(p),p);
		}
		else if (playerChoice == 2 ){
			
		}
			
		else if (playerChoice == 3){}
	}
	
	public void drawIntrigue(Player p, Deck<IntrigueCard> deck){
		p.addCard(deck.pop());
	}
	
	public void makeAnnouncement(Player p){
		Scanner input = new Scanner(System.in);
		
		
	}
	
	public boolean selectDirection(int steps, Player curPlayer){
		
		boolean validPath = false;
		Path p = null;
		
		Scanner scan = new Scanner(System.in);
		
		while(!validPath){
			HashSet<Location> visited = new HashSet<Location>();
			while(steps > 0){
				
				System.out.println("Player "+curPlayer.getPlayerNumber()+": "+curPlayer.getCharacter().getName()+
						" ("+curPlayer.getShortName()+")");
				System.out.println("Please enter the path you wish to take type n, s, w, or e");
				System.out.println("You have "+steps+" moves remaining ");
				System.out.println("(Enter 'board' to show the board)");
				String buildpath = scan.next();
				if (buildpath.equalsIgnoreCase("board")){
					board.drawBoard(); 
					continue;
				}
				if (buildpath.length() > 1){
					System.out.println("Invalid path, please try again");
					continue;
				}
				Location curLoc = curPlayer.getCharacter().getLocation();
				Location testLoc = board.findLocation(curLoc,buildpath);
				if (testLoc == null){
					System.out.println("Invalid path, please try again");
					continue;
				}
				if (visited.contains(testLoc)){
					System.out.println("Sorry, you have already visited that tile. Please try again");
					continue;
				}
				if(!board.canMoveTo(testLoc)){
					System.out.println("current player location = "+curPlayer.getCharacter().getLocation());
					System.out.println("You can not move in that direction, please try again");
					continue;
				}
				steps--;
				System.out.println("Now moving: "+buildpath);
				sleep(1000);
				applyPath(curLoc,testLoc,curPlayer);
				if (board.getTile(testLoc) instanceof IntrigueCard){
					
					
					
					
				}
				visited.add(testLoc);
				board.drawBoard();
			}
			validPath = true;
		}
		return true;
		
	}
	
	public void applyPath(Location cur , Location loc, Player player){
		board.getTile(cur).setPlayerOn(null);
		board.getTile(loc).setPlayerOn(player);
		player.getCharacter().setLocation(loc);
	}
	
	/**
	 * Rolls the dice on a player's turn
	 * @param p
	 * 		   the player rolling the die
	 */
	public int rollDice (Player p){
		System.out.println("Now rolling dice for player "+p.getPlayerNumber());
		sleep(1000);
		Random gen = new Random();
		int val = gen.nextInt(6)+1;
		System.out.println("You rolled a "+val);
		return val;
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

		ArrayList<Suspect> numList = new ArrayList<Suspect>();
		int count = 1;
		for (Suspect c : suspects) { // print out the possible character
			boolean used = false;
			for (Player p : players) {
				if (p.getCharacter().equals(c)) {
					used = true;
				}
			}
			if (!used) {
				System.out.println(count + "\t" + c.getName());
				numList.add(c);
				count++;
			}
		}

		String name = input.nextLine(); // read in the character they are going to use
		Player p = null;
		int val = -1;
		try {
			val = Integer.parseInt(name.trim()) - 1;
		} catch (Exception e) {
			return p;
		}

		if (val < 0 || val >= numList.size())
			return p;
		
		p = new Player(numList.get(val), new Hand<Card>());
		return p;
	}

	/**
	 * Reads in data from 3 files and generates a deck based on them
	 * 
	 * @throws IOException
	 */
	public Deck<Card> createDeck() throws IOException {
		suspects = new ArrayList<Suspect>();
		rooms = new ArrayList<Room>();
		weapons = new ArrayList<Weapon>();

		Deck<Card> deck = new Deck<Card>();

		String[] lines = new Scanner(new File("suspects.txt")).useDelimiter("\\Z").next().split("\n");

		for (String s : lines) {
			Suspect c = new Suspect(s.trim(), null);
			suspects.add(c);
			deck.push(new Card(c));
		}

		lines = new Scanner(new File("rooms.txt")).useDelimiter("\\Z").next().split("\n");

		for (String s : lines) {
			Room r = new Room(s.trim(), board);
			rooms.add(r);
			if(!s.startsWith("Pool"))   //don't add the pool to the deck, it cannot be the place of a murder
				deck.push(new Card(r));
		}

		lines = new Scanner(new File("weapons.txt")).useDelimiter("\\Z").next().split("\n");

		for (String s : lines) {
			Weapon w = new Weapon(s.trim(), null);
			weapons.add(w);
			deck.push(new Card(w));
		}

		deck.shuffle(); // shuffle the cards
		return deck;
	}

	/**
	 * A method to build the intrigue deck, takes the deck to put everything into
	 * @param deck
	 * 		The deck to put everything into
	 */
	public Deck<IntrigueCard> createIntrigueDeck() throws IOException {
		Deck<IntrigueCard> deck = new Deck<IntrigueCard>();
		String[] lines = {};
		
		lines = new Scanner(new File("keepers.txt")).useDelimiter("\\Z").next().split("\n");
		

		for (String s : lines) {
			deck.push(new Keeper(s));
		}

		for (int i = 0; i < 8; i++) {
			deck.push(new Clock(false));
		}
		
		deck.shuffle();

		deck.setDeadlyClock();
		
		return deck;
	}

	public void shuffleWeapons(ArrayList<Weapon> weapons) {
		Collections.shuffle(weapons); // put the weapons in a random order
		int count = 0;
		for (Weapon w : weapons) { // put each one in a room
			w.setRoom(rooms.get(count));
			rooms.get(count).addWeapon(w);
			count++;
		}
	}

	/**
	 * Creates the solution to this game,
	 * 
	 * @param deck
	 *            The deck of cards to use to find a solution from
	 * @return
	 *         The solution to the game, represented in an announcement object
	 */
	private Announcement createSolution(Deck<Card> deck) {
		Card room = null;
		Card player = null;
		Card weapon = null;

		for (Card c : deck) {
			if (c.getCard() instanceof Suspect) {
				player = c;
			} else if (c.getCard() instanceof Room) {
				room = c;
			} else if (c.getCard() instanceof Weapon) {
				weapon = c;
			}
		}

		deck.remove(room);
		deck.remove(weapon);
		deck.remove(player);

		return new Announcement(room, player, weapon);
	}

	private void sleep(int val) {
		try {
			Thread.sleep(val);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) {
		new Cluedo();
	}

}
