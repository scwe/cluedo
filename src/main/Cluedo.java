package main;

import java.util.*;
import java.io.*;
import logic.MoveRecord;
import logic.Declaration;

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

	private Declaration solution;

	private Scanner inputScanner;
	private boolean gameFinished;
	private int remainingPlayers;

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
		
		roomLocations = board.loadRooms(rooms);
		boolean validInput = false;
		int numPlayers = -1;
		while(!validInput){
			System.out.println("Enter the number of players playing (1-6)");
			
			try{
				numPlayers = inputScanner.nextInt();
			}
			catch (Exception e){
				System.out.println("That number was not valid. Try again.");
				continue;
			}
			if (numPlayers > 0 && numPlayers <= 6){
				remainingPlayers = numPlayers;
				validInput = true;
			}
			else{
				System.out.println("That number was not valid. Try again.");
			}
		}
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

			board.getTile(playLoc).setSuspectOn(player.getSuspect());
			players.offer(player);
		}
		
		deck.deal(players);
		assignWeapons(weapons);

		for (Player p : players) {
			System.out.println(p);
			
		}

		for (Weapon w : weapons) {
			System.out.println(w);
			sleep(200);
		}
		
		gameFinished = false;
		
		while (!gameFinished){
			Player curPlayer = players.poll();
			
			if(remainingPlayers == 0){
				System.out.println("There are no remaining players. No one wins. Game over!");
				break;
			}
			if(curPlayer.isPlayerOutOfGame()){
				players.offer(curPlayer);
				continue;
			}
			takeTurn(curPlayer);
				
			players.offer(curPlayer);
		}

	}
	
	/**
	 * The main method to control the turn taking process, checks what the user wants to do,
	 * then processes accordingly
	 * @param player
	 * 		The player whose turn it is
	 */
	public void takeTurn(Player player){
		
		MoveRecord moveRecord = new MoveRecord();
		
		System.out.println("Player "+player.getPlayerNumber()+": "+player.getSuspect().getName()+
				" ("+player.getShortName()+")");

		boolean validOption = false;
		int playerChoice = 0;
		
		Scanner optionScan = new Scanner(System.in);
		while(!validOption){
			System.out.println("Options: (type corresponding number)");
			System.out.println("1	Roll dice");
			if (player.hasIntrigueCards()){
				System.out.println("(2)  Play intrigue card");
			}
			int okRange = (player.hasIntrigueCards())?2:1;
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
			if(findRoomOfPlayer(player) != null){
				Room playerRoom = findRoomOfPlayer(player);
				System.out.println("You are currently in the "+playerRoom);
				validOption = false;
				while(!validOption){
					System.out.println("Please enter the number of the door you wish to exit by");
					for(int i = 0; i > playerRoom.getDoors().size(); i++){
						System.out.println((i+1)+" "+playerRoom.getDoors().get(i));
					}
					String decision = optionScan.next();
					try{
						playerChoice = Integer.parseInt(decision);
					}catch (Exception e){
						System.out.println("Sorry, that option was not valid");
					}
					if (playerChoice > 0 && playerChoice <= playerRoom.getDoors().size()){
						Door toDoor = playerRoom.getDoors().get(playerChoice);
						if (toDoor instanceof SecretDoor){
							Room toRoom = (playerRoom.getName().equals("Kitchen"))?findRoom("Conservatory"):findRoom("Kitchen");
							moveToRoom(player, toRoom);
						}
						else{
							board.getTile(player.getSuspect().getLocation()).setSuspectOn(null);
							player.getSuspect().setLocation(toDoor.getLocation());
							board.getTile(toDoor.getLocation()).setSuspectOn(player.getSuspect());
						}
						validOption = true;
					}
					else{
						System.out.println("Sorry that option was not valid");
					}
				}
			}
			moveSuspect(rollDice(player),player,moveRecord);
			
			
		}else if (playerChoice == 2){
			//TODO the player wants to play an intrigue card
		}
		if(moveRecord.getRm()!=null){
			System.out.println("Would you like to make an accusation? (Y/N)");
			String decision = optionScan.next();
			validOption = false;
			while(!validOption){
				if(decision.equalsIgnoreCase("Y")){
					Declaration dec = makeDeclaration(player,moveRecord,"Announce");
					validOption = true;
				}
				else if (decision.equalsIgnoreCase("N")){
					validOption = true;
				}
				else{
					System.out.println("That option was not valid. Please enter Y or N");
					System.out.println("Would you like to make an announcement? (Y/N)");
				}
			}
		}
		if(moveRecord.canAccuse() == true){
			System.out.println("You are in the pool room. Would you like to make an Accusation? (Y/N)");
			String decision = optionScan.next();
			validOption = false;
			while(!validOption){
				if(decision.equalsIgnoreCase("Y")){
					Declaration dec = makeDeclaration(player,moveRecord,"Accuse");
					gameFinished = performAccusation(dec,player);
					validOption = true;
				}
				else if (decision.equalsIgnoreCase("N")){
					validOption = true;
				}
				else{
					System.out.println("That option was not valid. Please enter Y or N");
					System.out.println("Would you like to make an Accusation? (Y/N)");
				}
			}
		}
	}
	
	private boolean performAccusation(Declaration dec, Player p){
		
		System.out.println("You have decided to make an accusation!");
		sleep(1500);
		System.out.println("If you get this right, you win. If you get this wrong, you're out");
		System.out.println("You look at the solution...");
		sleep(1500);
		boolean winner = true;
		System.out.println("Room: You said "+dec.getAccusedRoom()+". The solution is "+solution.getAccusedRoom());
		if(!dec.getAccusedRoom().equals(solution.getAccusedRoom())){
			winner = false;
		}
		System.out.println("Weapon: You said "+dec.getWeapon()+". The solution is "+solution.getWeapon());
		if(!dec.getWeapon().equals(solution.getSuspect())){
			winner = false;
		}
		System.out.println("Suspect: You said "+dec.getSuspect()+". The solution is "+solution.getSuspect());
		if(!dec.getSuspect().equals(solution.getSuspect())){
			winner = false;
		}
		if(winner){
			System.out.println("Congratulations, you just won!");
		}
		else{
			System.out.println("I'm sorry to say, you just lost!");
			System.out.println("You will need to stay at the table to dispute announcements.");
			p.setPlayerOutOfGame(true);
			remainingPlayers--;
		}
		
		return winner;
		
	}
	
	public void drawIntrigue(Player p, Deck<IntrigueCard> deck){
		p.addCard(deck.pop());
	}
	

	/**
	 * Makes an announcement to the board of what the player thinks is the solution
	 * then test to see if anyone can refute it, working clockwise
	 * @param curPlayer
	 * 		The curplayer, who's turn is it
	 * @param moveRecord
	 * 		The moves made so far in this turn
	 */
	public Declaration makeDeclaration(Player curPlayer, MoveRecord moveRecord, String type){
		Suspect announcedSuspect = null;
		Weapon announcedWeapon = null;
		Room announcedRoom = findRoomOfPlayer(curPlayer);
		Scanner scan = new Scanner(System.in);
		System.out.println("OK! Let's begin your announcement!");
		boolean validSuspect = false;
		while (!validSuspect){
			System.out.println("Please enter the number of the suspect you wish to "+type);
			for (int i = 0; i < suspects.size(); i++){
				System.out.println((i+1)+" "+suspects.get(i).getName());
			}
			String suspectChoice = scan.next();
			int choiceValue = -1;
			try{
				choiceValue = Integer.parseInt(suspectChoice)-1;
			}catch (Exception e){
				System.out.println("Sorry that choice was not valid.");
				continue;
			}
			if (choiceValue < 0 || choiceValue >=  suspects.size())
				System.out.println("Sorry that choice was not valid.");
			else{
				announcedSuspect = suspects.get(choiceValue);
				validSuspect = true;
			}
		}
		boolean validWeapon = false;
		while (!validWeapon){
			System.out.println("Please enter the number of the weapon you wish to "+type);
			for (int i = 0; i < weapons.size(); i++){
				System.out.println((i+1)+" "+weapons.get(i).getName());
			}
			String weaponChoice = scan.next();
			int choiceValue = -1;
			try{
				choiceValue = Integer.parseInt(weaponChoice)-1;
			}catch (Exception e){
				System.out.println("Sorry that choice was not valid.");
				continue;
			}
			if (choiceValue < 0 || choiceValue >=  weapons.size())
				System.out.println("Sorry that choice was not valid.");
			else{
				announcedWeapon = weapons.get(choiceValue);
				validWeapon = true;
			}
		}
		Declaration dec = new Declaration(announcedSuspect,announcedWeapon,findRoomOfPlayer(curPlayer));
		if (type.equalsIgnoreCase("Accuse")){
			boolean validRoom = false;
			while (!validRoom){
				System.out.println("Please enter the number of the room you wish to "+type);
				for (int i = 0; i < rooms.size()-1; i++){
					System.out.println((i+1)+" "+rooms.get(i).getName());
				}
				String roomChoice = scan.next();
				int choiceValue = -1;
				try{
					choiceValue = Integer.parseInt(roomChoice)-1;
				}catch (Exception e){
					System.out.println("Sorry that choice was not valid.");
					continue;
				}
				if (choiceValue < 0 || choiceValue >=  rooms.size()-1)
					System.out.println("Sorry that choice was not valid.");
				else{
					Room accusedRoom = rooms.get(choiceValue);
					dec.setAccusedRoom(accusedRoom);
					validRoom = true;
				}
				
			}
		}
		return dec;
	}
	
	//check if the accused character is associated to a given player. If so, move the 
	//character into the accused room with the accuser.
	private void performAnnouncement(Declaration ann, Player curPlayer){
		Suspect announcedSuspect = ann.getSuspect();
		Weapon announcedWeapon = ann.getWeapon();
		Room announcedRoom = ann.getRoom();
		Player movePlayer = null;
		Player foundPlayer = null;
		players.offer(curPlayer);
		while(movePlayer != curPlayer){
			movePlayer = players.poll();
			if (movePlayer.getSuspect().equals(announcedSuspect)){
				foundPlayer = movePlayer;
			}
			players.offer(movePlayer);
		}
		
		if (foundPlayer != null){
			System.out.println("Moving "+announcedSuspect.getName()+" to announced room "+announcedRoom);
			sleep(1000);
			moveToRoom(foundPlayer,announcedRoom);
		}
		System.out.println("Moving "+announcedWeapon+" to announced room "+announcedRoom);
		announcedWeapon.setRoom(announcedRoom);
		sleep(1000);
		System.out.println("Can any player disprove the suggestion?");
		int disproveCount = 0;
		for(Player player: players){
			if (player != curPlayer){
				if(player.getHand().contains(announcedSuspect)){
					printRevelation(curPlayer,announcedSuspect);
					disproveCount++;
				}
				else if(player.getHand().contains(announcedWeapon)){
					printRevelation(curPlayer,announcedWeapon);
					disproveCount++;
				}
				else if(player.getHand().contains(announcedRoom)){
					printRevelation(curPlayer,announcedRoom);
					disproveCount++;
				}
			}
		}
		if(disproveCount == 0){
			System.out.println("No players could dispute the suggestion!");
		}
	}
	
	private void printRevelation(Player player, Cardable card){
		System.out.println(player.getPlayerNumber()+ "("+player.getSuspect()+")"+
				"revealed the "+card+" card to you in secret, disproving the " +
				"suspect");
	}
	
	/**
	 * Takes a door and finds the corresponding room attached to it
	 * @param d
	 * 		The door to find the room for
	 * @return
	 * 		The room found, null if there is none (shouldn't be possible)
	 */
	public Room findRoom(Door d){
		for(Room r : rooms){
			if(r.getDoors().contains(d)){
				return r;
			}
		}
		return null;
	}
	
	/**
	 * Moves a given player into a given room, making sure to remove that player
	 * from their original room if any
	 * @param player
	 * 		The player to move
	 * @param room
	 * 		The room to move the player to
	 */
	public void moveToRoom(Player player, Room room){
		for(Room r : rooms){
			if(r.getSuspects().contains(player.getSuspect())){
				r.removeSuspect(player.getSuspect());
			}
		}
		Location roomLoc = room.addSuspect(player.getSuspect());
		board.getTile(player.getSuspect().getLocation()).setSuspectOn(null);
		player.getSuspect().setLocation(roomLoc);
		board.getTile(roomLoc).setSuspectOn(player.getSuspect());
	}
	
	/**
	 * Takes care of the user inputing text and then moves and tests whether that 
	 * is a valid move until the user has no more moves left. If they move somewhere invalid
	 * it will repeat until they choose something valid.
	 * @param steps
	 * 		The steps to be taken
	 * @param curPlayer
	 * 		The player that is moving
	 * @param moveRecord
	 * 		where they have moved so far
	 * @return
	 * 		whether the move was successful or not
	 */
	public boolean moveSuspect(int steps, Player curPlayer,MoveRecord moveRecord){
		
		boolean finishedTurn = false;
		
		Scanner scan = new Scanner(System.in);


		HashSet<Location> visited = new HashSet<Location>();
		while(steps > 0 && !finishedTurn){

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
				System.out.println("Moving to room "+rm);
				moveToRoom(curPlayer,rm);
				if (rm.getName().equalsIgnoreCase("Pool")){
					moveRecord.setCanAccuse(true);
				}
				else{
					moveRecord.setRm(rm);
				}
				finishedTurn = true;
			}
			visited.add(testLoc);
			board.drawBoard();
		}

		return true;
		
	}
	
	/**
	 * Applies the path to the board
	 * @param cur
	 * 		The suspects current position
	 * @param loc
	 * 		The position the suspect is moving to
	 * @param player
	 * 		The player object that wraps a suspect
	 */
	public void applyPath(Location cur , Location loc, Player player){
		board.getTile(cur).setSuspectOn(null);
		board.getTile(loc).setSuspectOn(player.getSuspect());
		player.getSuspect().setLocation(loc);
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
		int val = gen.nextInt(6000)+1;
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

	/**
	 * Assigns the given list of weapons to the rooms
	 * @param weapons
	 * 		The weapons to distribute
	 * @param rooms
	 * 		The rooms to distribute the weapons to
	 */
	public void assignWeapons(ArrayList<Weapon> weapons) {
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
	private Declaration createSolution(Deck<Card> deck) {
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

		return null;
	}

	/**
	 * A helper method so we can sleep the thread to make the outpput nicer
	 * @param val
	 * 		The time in milliseconds to sleep
	 */
	private void sleep(int val) {
		try {
			Thread.sleep(val);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	private Room findRoomOfPlayer(Player player){
		Suspect searchSus = player.getSuspect();
		for(Room room : rooms){
			for(Suspect sus : room.getSuspects()){
				if (sus.equals(searchSus)){
					return room;
				}
			}
		}
		return null;
	}
	
	private Room findRoom(String roomName){
		for (Room room : rooms){
			if (room.getName().equalsIgnoreCase(roomName)){
				return room;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		new Cluedo();
	}

}
