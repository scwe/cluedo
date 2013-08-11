package main;

import java.util.*;
import java.io.*;

import logic.Announcement;

import card.*;

import board.*;


public class Cluedo {

	private Queue<Player> players;
	private TextBoard board;

	private ArrayList<Suspect> suspects;
	private ArrayList<Room> rooms;
	private ArrayList<Weapon> weapons;

	private Deck<Card> deck;
	private Deck<IntrigueCard> intrigueDeck;
	
	private HashMap<Location, Room> roomLocations;

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
		
		roomLocations = loadRooms();
		distributeLocations(roomLocations);

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
			player.getSuspect().setLocation(board.getStartLocation(i));
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
	
	public void takeTurn(Player player){
		
		MoveRecord moveRecord = new MoveRecord();
		
		System.out.println("Player "+player.getPlayerNumber()+": "+player.getSuspect().getName()+
				" ("+player.getShortName()+")");

		boolean validOption = false;
		int playerChoice = 0;
		
		Scanner optionScan = new Scanner(System.in);
		while(!validOption){
			System.out.println("Options: ");
			System.out.println("(1)	Roll dice");
			if (player.hasIntrigueCards()){
				System.out.println("(3)  Play intrigue card");
			}
			int okRange = (player.hasIntrigueCards())?3:2;
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
			selectDirection(rollDice(player),player,moveRecord);
		}
		if(moveRecord.getRm()!=null){
			System.out.println("Would you like to make an accusation? (Y/N)");
			String decision = optionScan.next();
			validOption = false;
			while(!validOption){
				if(decision.equalsIgnoreCase("Y")){
					makeAnnouncement(player,moveRecord);
				}
				else if (decision.equalsIgnoreCase("N")){
					validOption = true;
				}
				System.out.println("That option was not valid.");
				System.out.println("Would you like to make an accusation? (Y/N)");
			}
		}
			
		else if (playerChoice == 3){}
	}
	
	public void drawIntrigue(Player p, Deck<IntrigueCard> deck){
		p.addCard(deck.pop());
	}
	
	public void makeAnnouncement(Player p, MoveRecord moveRecord){
		Scanner input = new Scanner(System.in);
		
	}
	
	public Room findRoom(Door d){
		for(Room r : rooms){
			if(r.getDoors().contains(d)){
				return r;
			}
		}
		return null;
	}
	
	public void moveRoom(Player player, Room room){
		for(Room r : rooms){
			if(r.getSuspects().contains(player.getSuspect())){
				r.removeSuspect(player.getSuspect());
			}
		}
		//Location roomLoc = room.addSuspect(player.getSuspect());
		board.getTile(player.getSuspect().getLocation()).setSuspectOn(null);
		//player.getSuspect().setLocation(roomLoc);
	}
	
	public boolean selectDirection(int steps, Player curPlayer,MoveRecord moveRecord){
		
		boolean validPath = false;
		Path p = null;
		
		Scanner scan = new Scanner(System.in);
		
		while(!validPath){
			HashSet<Location> visited = new HashSet<Location>();
			while(steps > 0){
				
				System.out.println("Player "+curPlayer.getPlayerNumber()+": "+curPlayer.getSuspect().getName()+
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
				Location curLoc = curPlayer.getSuspect().getLocation();
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
					System.out.println("current player location = "+curPlayer.getSuspect().getLocation());
					System.out.println("You can not move in that direction, please try again");
					continue;
				}
				steps--;
				System.out.println("Now moving: "+buildpath);
				sleep(1000);
				applyPath(curLoc,testLoc,curPlayer);
				if (board.getTile(testLoc) instanceof IntrigueTile){
					System.out.println("You picked up an intrigue card!");
					sleep(1000);
					IntrigueCard ic = intrigueDeck.pop();
					curPlayer.addCard(ic);
					moveRecord.setIc(ic);
					System.out.println("Your new intrigue card reads as follows:");
					System.out.println(ic);
				}
				else if (board.getTile(testLoc) instanceof Door){
					Room rm = findRoom((Door)board.getTile(testLoc));
					moveRoom(curPlayer,rm);
					moveRecord.setRm(rm);
				}
				visited.add(testLoc);
				board.drawBoard();
			}
			validPath = true;
		}
		return true;
		
	}
	
	public void applyPath(Location cur , Location loc, Player player){
		board.getTile(cur).setSuspectOn(null);
		board.getTile(loc).setSuspectOn(player.getSuspect());
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
				if (p.getSuspect().equals(c)) {
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
	
	public HashMap<Location, Room> loadRooms(){
		HashMap<Location, Room> map = new HashMap<Location, Room>();
		
		try{
			Scanner fileScanner = new Scanner(new File("roomLocs.txt"));
			
			int rowCount = 0;
			while(fileScanner.hasNextLine()){
				String line = fileScanner.nextLine();
				
				char[] characters = line.toCharArray();
				
				int colCount = 0;
				for(char c: characters){
					String name = getName(c);
					
					if(name != null){
						Room r = getRoom(name);
						map.put(new Location(colCount, rowCount), r);
					}
					colCount++;
				}
				rowCount++;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return map;
	}
	
	private Room getRoom(String name){
		for(Room r : rooms){
			if(r.getName().equals(name)){
				return r;
			}
		}
		
		return null;
	}
	
	private String getName(char room){
		switch(room){
		case 'S':
			return "Spa";
		case 'T':
			return "Theatre";
		case 'L':
			return "Living_Room";
		case 'C':
			return "Conservatory";
		case 'P':
			return "Patio";
		case 'p':
			return "Pool";
		case 'H':
			return "Hall";
		case 'K':
			return "Kitchen";
		case 'D':
			return "Dining_Room";
		case 'G':
			return "Guest_House";
		}
		
		return null;
	}
	
	private void distributeLocations(HashMap<Location, Room> roomLocations){
		for(Map.Entry<Location, Room> e: roomLocations.entrySet()){
			e.getValue().addLocation(e.getKey());
		}
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
