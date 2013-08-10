package main;

import java.util.*;
import java.io.*;

import logic.Announcement;

import card.*;
import card.Character;

import board.Path;
import board.TextBoard;


public class Cluedo {

	private Queue<Player> players; // tis a queue so we can sort out turns by polling
	private TextBoard board;

	private ArrayList<Character> characters;
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
			player.setPlayerNumber(i);
			players.offer(player);
		}

		solution = createSolution(deck);
		deck.deal(players);
		shuffleWeapons(weapons);

		for (Player p : players) {
			System.out.println(p);
			
		}

		for (Weapon w : weapons) {
			System.out.println(w);
			sleep(1000);
		}
		
		System.out.println(solution);
		
		for(IntrigueCard i : intrigueDeck){
			System.out.println(i);
		}
		
		boolean gameFinished = false;
		
		/**
		 * Game loop, this should probably be moved into a different method
		 */
		while (!gameFinished){
			Player curPlayer = players.poll();
			Scanner scan = new Scanner(System.in);
			System.out.println("Player "+curPlayer.getPlayerNumber()+"'s turn");

			boolean validOption = false;
			int playerChoice = 0;
			while(!validOption){
				System.out.println("Options: ");
				System.out.println("(1)	Roll dice");
				System.out.println("(2)  Make Accusation");
				if (curPlayer.hasIntrigueCards()){
					System.out.println("(3)  Play intrigue card");
				}
				int okRange = (curPlayer.hasIntrigueCards())?3:2;
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
				selectDirection(rollDice(curPlayer),curPlayer);
			}
			else if (playerChoice == 2 ){
				selectDirection(rollDice(curPlayer),curPlayer);
			}
				
			else if (playerChoice == 3){}
				
			players.offer(curPlayer);
		}

	}
	
	public void selectDirection(int steps, Player curPlayer){
		
		boolean validPath = false;
		Path p = null;
		
		Scanner scan = new Scanner(System.in);
		
		while(!validPath){
			System.out.println("Please enter the path you wish to take");
			System.out.println("(Enter 'board' to show the board)");
			String buildpath = scan.next();
			if (buildpath.equalsIgnoreCase("board")){
				board.drawBoard(); continue;
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
				System.out.println("That path was not valid, please try again");
			}
			validPath = true;
		}
		
	}
	
	/**
	 * Rolls the dice on a player's turn
	 * 
	 * @param p
	 * 		   the player rolling the die
	 */
	public int rollDice (Player p){
		
		System.out.println("Now rolling dice for player "+p.getPlayerNumber());
		sleep(2000);
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
		characters = new ArrayList<Character>();
		rooms = new ArrayList<Room>();
		weapons = new ArrayList<Weapon>();

		Deck<Card> deck = new Deck<Card>();

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

		int count = 0;
		for (IntrigueCard i : deck) {
			if (i instanceof Clock) {
				if (count == 7) {
					((Clock) i).setLast(true);
					break;
				}
				count++;
			}
		}
		
		return deck;
	}

	public void shuffleWeapons(ArrayList<Weapon> weapons) {
		Collections.shuffle(weapons); // put the weapons in a random order
		int count = 0;
		for (Weapon w : weapons) { // put each one in a room
			w.setRoom(rooms.get(count));
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
			if (c.getCard() instanceof Character) {
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
