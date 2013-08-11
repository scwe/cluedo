package main;

import java.util.*;
import java.io.*;
import logic.MoveRecord;
import logic.Declaration;

import card.*;
import card.Keeper.KeeperFunction;

import board.*;

/**The main class that takes care of all the user input and delegating what is required to the board 
 * and most of the other objects
 * 
 * @author scott & CF
 * 
 *
 */
public class Cluedo {

	private Queue<Player> players;
	private TextBoard board;

	private ArrayList<Suspect> suspects;
	private ArrayList<Room> rooms;
	private ArrayList<Weapon> weapons;

	private Deck<Card> deck;
	private Deck<IntrigueCard> intrigueDeck;
	private Map<Player,Location> playerStartLocMap;
	
	private Declaration solution;

	private Scanner inputScanner;
	private boolean gameFinished;
	private int remainingPlayers;

	public Cluedo() {
		
		board = new TextBoard();
		inputScanner = new Scanner(System.in);
		players = new LinkedList<Player>();
		playerStartLocMap = new HashMap<Player,Location>();
		
		try {
			deck = createDeck();
			intrigueDeck = createIntrigueDeck();
			solution = createSolution(deck);
			if(solution==null){
				System.out.println("SOL = NULL");
				sleep(2000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		board.loadRooms(rooms);
		boolean validInput = false;
		int numPlayers = -1;
		
		doIntro();
		
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
			sleep(500);
			player.getSuspect().setLocation(board.getStartLocation(i));
			player.setPlayerNumber(i+1);
			Location playLoc = board.getStartLocation(player.getPlayerNumber()-1);

			board.getTile(playLoc).setSuspectOn(player.getSuspect());
			playerStartLocMap.put(player, playLoc);
			players.offer(player);
		}
		
		deck.deal(players);
		assignWeapons(weapons);

		for (Player p : players) {
			System.out.println(p);
			sleep(200);
		}

		for (Weapon w : weapons) {
			System.out.println(w);
			sleep(200);
		}
		
		gameFinished = false;
		while (!gameFinished){
			Player curPlayer = players.poll();
			MoveRecord moveRecord = new MoveRecord();
			if(remainingPlayers == 0){
				System.out.println("There are no remaining players. No one wins. Game over!");
				break;
			}
			if(curPlayer.isPlayerOutOfGame()){
				players.offer(curPlayer);
				continue;
			}
			takeTurn(curPlayer, moveRecord);
			
			if (!moveRecord.isDead() && players.size()> 1 && queryIntrigueCard(curPlayer,KeeperFunction.MOVE_START_SPACE,"Your card allows you to move a player back to thier start location")){
				boolean validOption = false;
				while(!validOption){
					ArrayList<Player> playList = new ArrayList<Player>();
					System.out.println("Which player would you like to move?");
					int count = 1;
					for (Player player: players){
						System.out.println(count+"   "+player.getSuspect().getName());
						playList.add(player);
						count++;
					}
					String choice = inputScanner.next();
					int choiceVal = -1;
					try{
						choiceVal = Integer.parseInt(choice)-1;
					}
					catch(Exception e){
						System.out.println("Sorry that choice is invalid");
					}
					if(choiceVal < 0 || choiceVal >= playList.size()){
						System.out.println("Sorry that choice is invalid");
					}
					else{
						Player chosenPlayer = playList.get(choiceVal);
						System.out.println("Moving player "+chosenPlayer.getPlayerNumber()+" ("+chosenPlayer.getSuspect().getName()+") back to the start!");
						board.getTile(chosenPlayer.getSuspect().getLocation()).setSuspectOn(null);
						chosenPlayer.getSuspect().setLocation(playerStartLocMap.get(chosenPlayer));
						board.getTile(playerStartLocMap.get(chosenPlayer)).setSuspectOn(chosenPlayer.getSuspect());
						validOption = true;
					}
					
				}
				removeIntrigueCard(curPlayer, KeeperFunction.MOVE_START_SPACE);
				sleep(800);
			}
			
			if (!moveRecord.isDead() && queryIntrigueCard(curPlayer,KeeperFunction.TAKE_ANOTHER_TURN,"Your card allows you to take another turn now.")){
				System.out.println("Your next turn starts now!");
				removeIntrigueCard(curPlayer, KeeperFunction.TAKE_ANOTHER_TURN);
				sleep(800);
				takeTurn(curPlayer,moveRecord);
			}
			
			for(IntrigueCard inCard : curPlayer.getIntrigueHand()){
				if (inCard instanceof Keeper){
					Keeper keepCard = (Keeper)inCard;
					if(keepCard.getType() == KeeperFunction.RIGHT_SHOW_CARD){
						System.out.println("Your 'right show' keeper card is now being played.");
						Player last = null;
						for (Player play: players){
							last = play;
						}
						System.out.println(last.getPlayerNumber()+" will now show you a card");
						sleep(800);
						Card chosen = last.getHand().get(0);
						System.out.println("The card shown is "+chosen);
						sleep(800);
						removeIntrigueCard(curPlayer, KeeperFunction.RIGHT_SHOW_CARD);
						for (Player play: players){
							if (play != curPlayer && queryIntrigueCard(play,KeeperFunction.CARD_SNIPE,"Your card allows you to see the card just shown")){
								System.out.println("The card just shown was "+chosen);
								removeIntrigueCard(play, KeeperFunction.CARD_SNIPE);
							}
						}
					}
				}
			}

			players.offer(curPlayer);
		}

	}
	
	/**
	 * The main method to control the turn taking process, checks what the user wants to do,
	 * then processes accordingly
	 * @param player
	 * 		The player whose turn it is
	 */
	public void takeTurn(Player player, MoveRecord moveRecord){
		
		
		System.out.println();
		System.out.println("------------New Turn------------");
		System.out.println("Player "+player.getPlayerNumber()+": "+player.getSuspect().getName()+
				" ("+player.getSuspect().getShortName()+")");

		Scanner optionScan = new Scanner(System.in);
		boolean decisionMade = false;
		boolean validOption = false;
		
		
		if (queryIntrigueCard(player,KeeperFunction.MOVE_ANYWHERE,"Your card allows you to move anywhere.")){
			boolean validMoveAnywhere = false;
			while(!validMoveAnywhere){
				System.out.println("Enter the location you would like to move to in the form x,y");
				System.out.println("The board is 30x29 and you cannot move into walls or other players");
				String choice = optionScan.next();
				int x = -1;
				int y = -1;
				try{
					String[] spltCoords = choice.split(",");
					x = Integer.parseInt(spltCoords[0]);
					y = Integer.parseInt(spltCoords[1]);
				}
				catch (Exception e){
					System.out.println("Sorry, your location was invalid, please try again");
					continue;
				}
				if((x >= 0 && x < 30) && ( y >= 0 && y < 20)){
					Location testLoc = new Location(x,y);
					for (Room room : rooms){
						for (Location loc : room.getLocations()){
							if (loc.equals(testLoc) && board.getTile(testLoc).getSuspectOn()==null){
								System.out.println("Now moving into a room!");
								moveToRoom(player, room);
								if (room.getName().equalsIgnoreCase("Pool")){
									moveRecord.setCanAccuse(true);
									endTurn(player, moveRecord);
									return;
								}
								else{
									moveRecord.setRm(room);
									endTurn(player,moveRecord);
									return;
								}
							}
								
						}
					}
					if(board.canMoveTo(testLoc)){
						System.out.println("Now moving to "+testLoc);
						board.getTile(player.getSuspect().getLocation()).setSuspectOn(null);
						player.getSuspect().setLocation(testLoc);
						board.getTile(testLoc).setSuspectOn(player.getSuspect());
						return;
					}
				}
				System.out.println("Sorry, you can not move to that location, please try again");
			}
			removeIntrigueCard(player, KeeperFunction.MOVE_ANYWHERE);
		}
		
		while(!decisionMade){
			
			int playerChoice = 0;
			
			while(!validOption){

				System.out.println("Options: (enter corresponding number)");
				System.out.println("1	Roll Dice");
				System.out.println("2	View Hand");
				System.out.println("3	End Turn");
				int okRange = 3;
				
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
				rollMove(player,moveRecord);
				decisionMade = true;
				
			}else if (playerChoice == 2){
				printHand(player);
				validOption = false;
			}else if (playerChoice == 3){
				return;
			}

		}
		endTurn(player, moveRecord);

	}

	/**
	 * Checks to see if the player is in a room and then sorts out getting them out
	 * of the room
	 * @param player
	 * 		The player whose turn it is
	 * @param moveRecord
	 * 		The record of the players current turn and moves
	 */
	private void rollMove(Player player, MoveRecord moveRecord){
		
		int playerChoice = 0;
		Scanner optionScan = new Scanner(System.in);
		
		int roll = rollDice(player);
		moveRecord.setHasRolled(true);
		
		if (queryIntrigueCard(player,KeeperFunction.MOVE_EXTRA_SIX,"Your card allows you to add 6 to your roll immediately")){
			roll = roll+6;
			System.out.println("Another 6 has been added to your roll");
			System.out.println("Your roll is now worth "+roll);
			removeIntrigueCard(player, KeeperFunction.MOVE_EXTRA_SIX);
			sleep(800);
		}
		
		if(findRoomOfPlayer(player) != null){

			if (queryIntrigueCard(player,KeeperFunction.STAY_ROOM,"Your card allows you to stay in the room and make an announcement")){
				System.out.println("You have opted to stay in the room and make an announcement");
				removeIntrigueCard(player, KeeperFunction.STAY_ROOM);
				sleep(800);
				Declaration dec = makeDeclaration(player,"Announce");
				performAnnouncement(dec,player);
				return;
			}
			
			Room playerRoom = findRoomOfPlayer(player);
			System.out.println("You are currently in the "+playerRoom);
			boolean validOption = false;
			while(!validOption){
				System.out.println("Please enter the number of the door you wish to exit by");
				System.out.println("Type 'board' to see the board");
				for(int i = 0; i < playerRoom.getDoors().size(); i++){
					System.out.println((i+1)+"	Door at "+playerRoom.getDoors().get(i));
				}
				String decision = optionScan.next();
				if (decision.equalsIgnoreCase("board")){
					board.drawBoard();
					continue;
				}
				try{
					playerChoice = Integer.parseInt(decision)-1;
				}catch (Exception e){
					System.out.println("Sorry, that option was not valid");
					continue;
				}
				if (playerChoice >= 0 && playerChoice < playerRoom.getDoors().size()){
					Door toDoor = playerRoom.getDoors().get(playerChoice);
					if (toDoor instanceof SecretDoor){
						Room toRoom = (playerRoom.getName().equals("Kitchen"))?findRoom("Conservatory"):findRoom("Kitchen");
						System.out.println("Moving through the Secret Door to "+toRoom.getName());
						sleep(1000);
						moveToRoom(player, toRoom);
					}
					else{
						System.out.println("You move to the door at "+toDoor.getLocation());
						sleep(1000);
						board.getTile(player.getSuspect().getLocation()).setSuspectOn(null);
						player.getSuspect().setLocation(toDoor.getLocation());
						board.getTile(toDoor.getLocation()).setSuspectOn(player.getSuspect());
					}
					board.drawBoard();
					validOption = true;
				}
				else{
					System.out.println("Sorry that option was not valid");
				}
			}
		}
		moveSuspect(roll,player,moveRecord);
	}
	
	/**
	 * The method that takes care of the final part of the players turn, making announcements etc
	 * @param player
	 * 		The player whose turn it is
	 * @param moveRecord
	 * 		The record of the players current turn and moves
	 */
	private void endTurn(Player player, MoveRecord moveRecord){
		
		Scanner optionScan = new Scanner(System.in);
		boolean decisionMade = false;
		boolean validOption = false;
		if(moveRecord.isDead())return;
		//If the player has entered a room, we need to deal with this before their turn ends
		if(moveRecord.getRm()!=null){
			while(!decisionMade){
				System.out.println("You have entered the "+moveRecord.getRm().getName());
				System.out.println("Would you like to make an announcement? (Y/N)");
				System.out.println("(Type 'hand' to view your hand)");
				validOption = false;
				while(!validOption){
					String decision = optionScan.next();
					if(decision.equalsIgnoreCase("Y")){
						Declaration dec = makeDeclaration(player,"Announce");
						performAnnouncement(dec,player);
						validOption = true;
						decisionMade = true;
					}
					else if (decision.equalsIgnoreCase("N")){
						validOption = true;
						decisionMade = true;
					}
					else if(decision.equalsIgnoreCase("hand")){
						printHand(player); break;
					}
					else{
						System.out.println("That option was not valid.");
						System.out.println("Would you like to make an announcement? (Y/N)");
						System.out.println("Type Y, N, or enter 'hand' to view your hand.");
					}
				}
			}
		}
		if(moveRecord.canAccuse()){
			System.out.println("You are in the pool room. Would you like to make an Accusation? (Y/N)");
			String decision = optionScan.next();
			validOption = false;
			while(!validOption){
				if(decision.equalsIgnoreCase("Y")){
					Declaration dec = makeDeclaration(player,"Accuse");
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
	
	/**
	 * A helper method for performing and accusation
	 * @param dec
	 * 		The Declaration made
	 * @param p
	 * 		The player who made it
	 * @return
	 * 		whether that player is the winner (i.e found a indisputable declaration)
	 */
	private boolean performAccusation(Declaration dec, Player p){
		
		System.out.println("You have decided to make an accusation!");
		sleep(1500);
		System.out.println("If you get this right, you win. If you get this wrong, you're out");
		System.out.println("You look at the solution...");
		sleep(1500);
		boolean winner = true;
		System.out.println("Room: You said "+dec.getAccusedRoom().getName()+". The solution is "+solution.getAccusedRoom().getName());
		if(!dec.getAccusedRoom().equals(solution.getAccusedRoom())){
			winner = false;
		}
		System.out.println("Weapon: You said "+dec.getWeapon().getName()+". The solution is "+solution.getWeapon().getName());
		if(!dec.getWeapon().equals(solution.getSuspect())){
			winner = false;
		}
		System.out.println("Suspect: You said "+dec.getSuspect().getName()+". The solution is "+solution.getSuspect().getName());
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
	
	/**
	 * Draws an intrigue card from the deck
	 * @param p
	 * 		The player who landed on the intrigue tile
	 * @param deck
	 * 		The deck to draw the card from
	 */
	public void drawIntrigue(Player p, Deck<IntrigueCard> deck){
		if (!deck.isEmpty()){
			p.addCard(deck.pop());
		}
	}
	

	/**
	 * Creates a declaration object which can be used as an Announcement,
	 * Accusation, or solution.
	 * @param curPlayer
	 * 		The curplayer, who's turn is it
	 * @param moveRecord
	 * 		The moves made so far in this turn
	 */
	public Declaration makeDeclaration(Player curPlayer, String type){
		Suspect announcedSuspect = null;
		Weapon announcedWeapon = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("OK! Let's begin!");
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
	
	/**check if the accused character is associated to a given player. If so, move the 
	*character into the accused room with the accuser.
	*@param ann
	*		The declaration made
	*@param curPlayer
	*		The player whose turn it is at the moment
	*/
	private void performAnnouncement(Declaration ann, Player curPlayer){
		Suspect announcedSuspect = ann.getSuspect();
		Weapon announcedWeapon = ann.getWeapon();
		Room announcedRoom = ann.getRoom();
		Player foundPlayer = null;
		
		for (Player player : players){
			if (player.getSuspect().equals(announcedSuspect)){
				foundPlayer = player;
			}
		}
		
		if (foundPlayer != null && !foundPlayer.equals(curPlayer)){
			System.out.println("Moving "+announcedSuspect.getName()+" to announced room "+announcedRoom);
			sleep(1000);
			moveToRoom(foundPlayer,announcedRoom);
		}
		System.out.println("Moving "+announcedWeapon+" to announced room "+announcedRoom);
		announcedWeapon.setRoom(announcedRoom);
		sleep(1000);
		System.out.println("Can any player disprove the suggestion?");
		int disproveCount = 0;
		
		Player cyclePlayer = null;
		players.offer(curPlayer);
		while((cyclePlayer = players.poll()) != curPlayer){
			
			if(cyclePlayer.getHand().contains(new Card(announcedSuspect))){
				disproveCount += printRevelation(cyclePlayer,announcedSuspect);
			}
			else if(cyclePlayer.getHand().contains(new Card(announcedWeapon))){
				disproveCount += printRevelation(cyclePlayer,announcedWeapon);
			}
			else if(cyclePlayer.getHand().contains(new Card(announcedRoom))){
				disproveCount += printRevelation(cyclePlayer,announcedRoom);
				
			}
			players.offer(cyclePlayer);
		}
		if(disproveCount == 0){
			System.out.println("No players disputed the suggestion!");
		}
	}
	
	/**
	 * Helper method, prints the reveal card once it has been found
	 * @param player
	 * 		The player required to reveal the card
	 * @param card
	 * 		The card they are revealing
	 * @return
	 * 		1 if it gets disputed, 0 if it doesn't
	 */
	private int printRevelation(Player player, Cardable card){
		

		if (queryIntrigueCard(player,KeeperFunction.RUMOR_UNANSWERED, "Your card enables you to refuse to dispute the announcement")){
			System.out.println("Player "+player.getPlayerNumber()+" has refused to disupte any announcement");
			removeIntrigueCard(player,KeeperFunction.RUMOR_UNANSWERED);
			sleep(800);
			return 0;
		}
		
		System.out.println("Player "+player.getPlayerNumber()+ " ("+player.getSuspect().getName()+") "+
				"revealed the "+card.getName()+" card to you in secret, disproving the " +
				"card as a suspect");
		for (Player play: players){
			if (play != player && queryIntrigueCard(player,KeeperFunction.CARD_SNIPE,"Your card allows you to see the card just shown")){
				System.out.println("The card just shown was "+card.getName());
				removeIntrigueCard(play, KeeperFunction.CARD_SNIPE);
			}
		}
		sleep(800);
		return 1;
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


		HashSet<Tile> visited = new HashSet<Tile>();
		while(steps > 0 && !finishedTurn){

			System.out.println("Player "+curPlayer.getPlayerNumber()+": "+curPlayer.getSuspect().getName()+
					" ("+curPlayer.getSuspect().getShortName()+")");
			System.out.println("Please enter the letter of the direction you wish to take type: n, s, w, or e");
			System.out.println("(Enter 'board' to show the board)");
			System.out.println("You have "+steps+" moves remaining ");
			
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
			if (visited.contains(board.getTile(testLoc))){
				System.out.println("Sorry, you have already visited that tile this turn. Please try again");
				continue;
			}
			if(!board.canMoveTo(testLoc)){
				System.out.println("current player location = "+curPlayer.getSuspect().getLocation());
				System.out.println("You can not move in that direction, please try again");
				continue;
			}
			steps--;
			System.out.println("Now moving: "+buildpath);
			sleep(500);
			applyPath(curLoc,testLoc,curPlayer);
			if (board.getTile(testLoc) instanceof IntrigueTile){
				if (!intrigueDeck.isEmpty()){
					System.out.println("You picked up an intrigue card!");
					sleep(1000);
					IntrigueCard ic = intrigueDeck.pop();
					curPlayer.addCard(ic);
					moveRecord.setIc(ic);
					System.out.println("Your new intrigue card reads as follows:");
					System.out.println(ic);
					sleep(2000);
					if (ic instanceof Clock){
						Clock curClock = (Clock)ic;
						if (curClock.isLast()){
							System.out.println("The clock was deadly! You died!");
							System.out.println("You will need to stay at the table to dispute announcements.");
							curPlayer.setPlayerOutOfGame(true);
							moveRecord.setIsDead(true);
							return false;
						}
					}
				}
			}
			else if (board.getTile(testLoc) instanceof Door){
				Room rm = findRoom((Door)board.getTile(testLoc));
				System.out.println("Moving to room "+rm);
				moveToRoom(curPlayer,rm);
				if (rm.getName().equalsIgnoreCase("Pool")){
					System.out.println("You are in the pool room");
					moveRecord.setCanAccuse(true);
				}
				else{
					moveRecord.setRm(rm);
				}
				finishedTurn = true;
			}
			visited.add(board.getTile(testLoc));
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
		int val = gen.nextInt(600)+1;
		System.out.println("You rolled a "+val);
		System.out.println();
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
		Room room = null;
		Suspect suspect = null;
		Weapon weapon = null;
		Card rm = null;
		Card wep = null;
		Card sus = null;

		for (Card c : deck) {
			if (c.getCard() instanceof Suspect) {
				suspect = (Suspect)c.getCard();
				sus = c;
			} else if (c.getCard() instanceof Room) {
				room = (Room)c.getCard();
				rm = c;
			} else if (c.getCard() instanceof Weapon) {
				weapon = (Weapon)c.getCard();
				wep = c;
			}
		}
		Declaration dec = new Declaration(suspect,weapon,null);
		dec.setAccusedRoom(room);
		deck.remove(rm);
		deck.remove(wep);
		deck.remove(sus);

		return dec;
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
	
	/**
	 * Finds the room of a given player
	 * @param player
	 * 		The player to find
	 * @return
	 * 		The room found
	 */
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
	
	/**
	 * prints the hand of the given player
	 * @param player
	 * 		The player whose hand should be printed
	 */
	private void printHand(Player player){
		System.out.println("You hold the following cards:");
		for (Card c: player.getHand()){
			System.out.println("	"+c);
			sleep(400);
		}
		System.out.println();
	}
	
	/**
	 * Prints the intro sequence
	 */
	private void doIntro(){
		
		System.out.println("Welcome");
		sleep(800);
		System.out.println("\tTo");
		sleep(800);
		System.out.print("\t   ");
		for (int i = 0; i < 4; i++){
			System.out.print(".");
			sleep(300);
		}
		sleep(300);
		System.out.println();
		System.out.println("-------------------------------------------");
		System.out.println("\t\tCLUEDO!");
		System.out.println("-------------------------------------------");
		sleep(900);
	}
	
	/**
	 * Queries the user as to whether they want to use a given Keeper card
	 * @param player
	 * 		The player whose possiblity it is to use the Keeper Card
	 * @param cardType
	 * 		The type of the Keeper Card
	 * @param message
	 * 		the message on the Keeper Card, its function
	 * @return
	 * 		Whether the Intrigue card was used
	 */
	private boolean queryIntrigueCard(Player player, Keeper.KeeperFunction cardType, String message){
		
		Scanner yesNo = new Scanner(System.in);
		for(IntrigueCard inCard : player.getIntrigueHand()){
			if (inCard instanceof Keeper){
				Keeper keepCard = (Keeper)inCard;
				if(keepCard.getType() == cardType){
					System.out.println();
					System.out.println(player.getPlayerNumber()+" ("+player.getSuspect().getName()+") has an intrigue card to play:");
					sleep(800);
					System.out.println(keepCard);
					System.out.println(message);
					System.out.println("Would Player "+player.getPlayerNumber()+" like to play that now? Y/N");
					boolean validOption = false;
					while(!validOption){
						String decision = yesNo.next();
						if (decision.equalsIgnoreCase("y")){
							return true;
						}
						else if (decision.equalsIgnoreCase("n")){
							return false;
						}
						else{
							System.out.println("That choice was not valid. Please enter Y or N");
						}
					}
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * Removes a specificed Keeper card from a specificed players hand
	 * @param player
	 * 		The player to remove the card from
	 * @param keepFunc
	 * 		The card to remove
	 */
	private void removeIntrigueCard(Player player, KeeperFunction keepFunc){
		
		Keeper toRemove = null;
		for (IntrigueCard inCard: player.getIntrigueHand()){
			
			if(inCard instanceof Keeper){
				Keeper keepCard = (Keeper)inCard;
				if(keepCard.getType() == keepFunc){
					toRemove = keepCard;
				}
			}
		}
		if (toRemove != null){
			player.getIntrigueHand().remove(toRemove);
		}
		
	}
	
	/**
	 * Finds the room given a String of the name
	 * @param roomName
	 * 		The name of the room to find
	 * @return
	 * 		The room found
	 */
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
