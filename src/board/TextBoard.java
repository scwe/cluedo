package board;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

import card.Room;

public class TextBoard {

	public final int ROW_LENGTH = 31;
	public final int COL_LENGTH = 30;
	private Tile[][] board = new Tile[ROW_LENGTH][COL_LENGTH];

	public TextBoard() {

		File f = new File("Map");

		BoardLoader bl = new BoardLoader(f, ROW_LENGTH, COL_LENGTH);
		board = bl.getBoard();
		drawBoard();
	}

	
	public void drawBoard(){
		
		for (int a = 0; a < ROW_LENGTH; a++){
			for(int b = 0; b < COL_LENGTH; b++){
				board[a][b].draw(null);
			}
			System.out.println(); // Just to make sure there is a blank line between printing
		}

	}


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
	
	public Tile getTile(Location loc){
		return board[loc.getY()][loc.getX()];
	}
	
	
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
	
	public boolean canMoveTo(Location testLoc){
		if(getTile(testLoc) instanceof Door){
			Door d = (Door)getTile(testLoc);
			
			System.out.println(d.getDrawContext().getString());
			
			switch(d.getDrawContext().getString()){
			case "^":
				if(getTile(new Location(testLoc.getX(), testLoc.getY()-1)).getSuspectOn() == null){
					return false;
				}
				break;
			case "v":
				if(getTile(new Location(testLoc.getX(), testLoc.getY()+1)).getSuspectOn() == null){
					return false;
				}
				break;
			case "<":
				if(getTile(new Location(testLoc.getX()-1, testLoc.getY())).getSuspectOn() == null){
					return false;
				}
				break;
			case ">":
				if(getTile(new Location(testLoc.getX()+1, testLoc.getY())).getSuspectOn() == null){
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
	
	public HashMap<Location, Room> loadRooms(ArrayList<Room> rooms){
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
		
		return map;
	}
	
	/**
	 * A method which takes a map from locations to room
	 * @param roomLocations
	 */
	public void distributeLocations(HashMap<Location, Room> roomLocations){
		for(Map.Entry<Location, Room> e: roomLocations.entrySet()){
			e.getValue().addLocation(e.getKey());
		}
	}
	
	private Room getRoom(String name, ArrayList<Room> rooms){
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


}
