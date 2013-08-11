package board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Loads a board from a given file and returns an array of tile objects
 * 
 * @author CF
 *
 */
public class BoardLoader {
	
	private ContextGenerator cg;
	private Tile[][] board;
	
	public BoardLoader(File map, int row_length, int col_length){
		
		cg = new ContextGenerator();
		Scanner scan;
		
		try {
			scan = new Scanner(map);
			board = new Tile[row_length][col_length];
		
			int rowLoc = 0;
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				char[] lineChars = line.toCharArray();
				int colLoc = 0;
				for (char c: lineChars){
					Location loc = new Location(colLoc,rowLoc);
					String type = getType(c);
					Tile t = getTile(type,loc,c);
					board[rowLoc][colLoc] = t;
					colLoc++;
				}
				rowLoc++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Tile[][] getBoard(){
		return this.board;
	}
	
	/**
	* returns a tile based off a given string and a location
	*@param type
	*	The string to base the type of tile off
	*@param l
	* 	The location of the tile
	*@param label
	* 	Only used if the it is a room label
	*@return
	*	The tile generated
	*/
	private Tile getTile(String type, Location l, char label){
		
		DrawContext dc = cg.createContext(type, label);
		switch (type){
			case "wall":
				return new Wall(l, null, type, dc);
			case "hall":
				return new Hall(l, null, type, dc);
			case "door-north":
				return new Door(l, null, type, dc);
			case "door-west":
				return new Door(l, null, type, dc);
			case "door-east":
				return new Door(l, null, type, dc);
			case "door-south":
				return new Door(l, null, type, dc);
			case "border":
				return new Wall(l, null, type, dc);
			case "intrigue":
				return new IntrigueTile(l, null, type, dc);
			case "room-label":
				return new RoomTile(l, null, type, dc);
			case "boundary":
				return new Wall(l,null,type,dc);
			case "room":
				return new RoomTile(l, null, type, dc);
			case "secret-door":
				return new SecretDoor(l, null, type, dc);
		}
		return null;
	}
	
	/**
	 * Turns a character from the file into a full string that describes the tile
	 * better
	 * @param c
	 * 		The character from the file
	 * @return
	 * 		The full string representation
	 */
	private String getType(char c){
		switch (c){
			case '#':
				return "wall";
			case ' ':
				return "hall";
			case '^':
				return "door-north";
			case '>':
				return "door-west";
			case '<':
				return "door-east";
			case 'v':
				return "door-south";
			case '*':
				return "boundary";
			case '?':
				return "intrigue";
			case '_':
				return "room";
			case '8':
				return "secret-door";
			default :
				return "room-label";
		}
	}
}
