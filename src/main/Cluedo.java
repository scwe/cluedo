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
		Scanner scan = new Scanner(System.in);
		System.out.println("Player "+p.getPlayerNumber()+"'s turn");

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
			String decision = scan.next();
			try{
				playerChoice = Integer.parseInt(decision);

			}catch (Exception e){
				validOption = false;
			}
			if (playerChoice > 0 && playerChoice <= okRange){
				validOption = true;
			}
		}
		if (playerChoice == 1){
			if(selectDirection(rollDice(p),p)){
				board.drawBoard();
			}
		}
		else if (playerChoice == 2 ){
			selectDirection(rollDice(p),p);
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
			System.out.println("Player "+curPlayer.getPlayerNumber()+": "+curPlayer.getCharacter().getName()+
					" ("+curPlayer.getShortName()+")");
			System.out.println("Please enter the path you wish to take");
			System.out.println("Your path can consist of "+steps+" moves ");
			System.out.println("Enter the path as a combination of n,s,w,e (e.g nsswees)");
			System.out.println("(Enter 'board' to show the board)");
			String buildpath = scan.next();
			if (buildpath.equalsIgnoreCase("board")){
				board.drawBoard(); 
				continue;
			}
			if (buildpath.length() > steps){
				System.out.println("Invalid path, please try again");
				continue;
			}
			if (buildpath.length() < steps){
				while(buildpath.length() < steps){
					System.out.println("You have "+(steps-buildpath.length())+" steps remaining, please enter these now");
					String addpath = scan.next();
					buildpath = buildpath.concat(addpath);
				}
			}
			p = new Path(curPlayer.getCharacter().getLocation(),buildpath);
			if(!p.isValid(board)){
				System.out.println("That path was not valid, please try again-------------------");
				System.out.println(p.getStartLocation()+" "+p.getEndLocation());
				continue;
			}
			System.out.println("The outcome of that path is as follows");
			applyPath(board,p,curPlayer);
			board.drawBoard();
			System.out.println("Are you happy with this move? (Y/N)");
			if (!scan.next().equalsIgnoreCase("Y")){
				String reverseString = "";
				for (int i = buildpath.length(); i < buildpath.length(); i--){
					reverseString = reverseString.concat(java.lang.Character.toString(buildpath.charAt(i)));
				}
				System.out.println(reverseString);
				Path newPath = new Path(curPlayer.getCharacter().getLocation(),reverseString);
				applyPath(board,newPath,curPlayer);
				continue;
			}
			
			validPath = true;
		}
		scan.close();
		return true;
		
	}
	
	public void applyPath(TextBoard b , Path p, Player player){
		b.getTile(p.getStartLocation()).setPlayerOn(null);
		b.getTile(p.getEndLocation()).setPlayerOn(player);

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
