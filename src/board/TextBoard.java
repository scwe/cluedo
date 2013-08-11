package board;

import java.io.*;
import java.util.*;

import card.Room;

/**A class to represent the board, passes the drawing off to each its tiles
 * has some nice helper methods for finding start location etc
 * @author CF & scott
 *
 */
public class TextBoard {

	public final int ROW_LENGTH = 31;
	public final int COL_LENGTH = 30;
	private Tile[][] board = new Tile[ROW_LENGTH][COL_LENGTH];

	public TextBoard() {

		File f = new File("Map");

		BoardLoader bl = new BoardLoader(f, ROW_LENGTH, COL_LENGTH);
		board = bl.getBoard();

	}

	/**
	 * Draws the board
	 */
	public void drawBoard(){
		
		System.out.println("-----------THE BOARD-----------");
		for (int a = 0; a < ROW_LENGTH; a++){
			for(int b = 0; b < COL_LENGTH; b++){
				board[a][b].draw(null);
			}
			System.out.println(); // Just to make sure there is a blank line between printing
		}

	}


	/**
	 * Returns the start location given a player number
	 * @param i
	 * 		The player number to get the start location for
	 * @return
	 * 		The location that player should start at
	 */
	public Location getStartLocation(int i) {
		switch (i) {
		case 0:
			return new Location(7, 1);
		case 1:
			return new Location(21, 1);
		case 2:
			return new Location(19, 29);
		case 3:
			return new Location(8, 29);
		case 4:
			return new Location(1, 20);
		case 5:
			return new Location(1, 10);
		}

		return null;
	}
	
	/**
	 * Gets the tile at a specificied location
	 * @param loc
	 * 		The location to get the tile for
	 * @return
	 * 		The Tile found
	 */
	public Tile getTile(Location loc){
		return board[loc.getY()][loc.getX()];
	}
	
	/**
	 * Finds the location given a start location and a string to move by
	 * @param start
	 * 		The start fo where the move occured
	 * @param move
	 * 		The string representation of the move
	 * @return
	 * 		The location that that move will end up on
	 */
	public Location findLocation(Location start, String move){
		
		Location testLoc  = new Location(start.getX(), start.getY());
		switch(move){
			case "n":
				testLoc.setY(testLoc.getY()-1);
				return testLoc;
			case "e":
				testLoc.setX(testLoc.getX()+1);
				return testLoc;
			case "s":
				testLoc.setY(testLoc.getY()+1);
				return testLoc;
			case "w":
				testLoc.setX(testLoc.getX()-1);
				return testLoc;
			default:
				return null;
		}
		
	}
	
	/**
	 * Returns whether it is possible to move to a given location
	 * @param testLoc
	 * 		the location we are going to move to
	 * @return
	 * 		Whether it is a legal move
	 */
	public boolean canMoveTo(Location testLoc){
		if(testLoc.getX() < 0 || testLoc.getX() >= COL_LENGTH || testLoc.getY() < 0 || testLoc.getY() >= ROW_LENGTH){
			return false;
		}
		if(getTile(testLoc) instanceof Door){
			Door d = (Door)getTile(testLoc);
			
			System.out.println(d.getDrawContext().getString());
			
			switch(d.getDrawContext().getString()){
			case "^":
				if(getTile(new Location(testLoc.getX(), testLoc.getY()-1)).getSuspectOn() == null){
					printDoorMessage();
					return false;
				}
				break;
			case "v":
				if(getTile(new Location(testLoc.getX(), testLoc.getY()+1)).getSuspectOn() == null){
					printDoorMessage();
					return false;
				}
				break;
			case "<":
				if(getTile(new Location(testLoc.getX()-1, testLoc.getY())).getSuspectOn() == null){
					printDoorMessage();
					return false;
				}
				break;
			case ">":
				if(getTile(new Location(testLoc.getX()+1, testLoc.getY())).getSuspectOn() == null){
					printDoorMessage();
					return false;
				}
				break;
			}
		}
		if(getTile(testLoc).canMoveTo()){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * From a separate file, generates all the rooms with their locations and puts
	 * them into a map from location to room
	 * @param rooms
	 * 		The rooms that are to be loaded into
	 * @return
	 * 		The map from location to room
	 */
	public void loadRooms(ArrayList<Room> rooms){
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
						Room r = getRoom(name, rooms);
						map.put(new Location(colCount, rowCount), r);
					}
					colCount++;
				}
				rowCount++;
			}
			
			fileScanner.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		for(Map.Entry<Location, Room> e: map.entrySet()){
			e.getValue().addLocation(e.getKey());
		}
	}
	
	/**
	 * gets the room based on a name
	 * @param name
	 * 		The name of the room
	 * @param rooms
	 * 		The rooms to look through
	 * @return
	 * 		The room found, or null if there isn't one
	 */
	private Room getRoom(String name, ArrayList<Room> rooms){
		for(Room r : rooms){
			if(r.getName().equals(name)){
				return r;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the name of the room based on the roomLoc.txt file
	 * @param room
	 * 		The character representation of the room
	 * @return
	 * 		The actual name of the room
	 */
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

	
	private void printDoorMessage(){
		
		System.out.println("\nNOTE: You can only enter doors from the direction they point");
		
		
	}

}
