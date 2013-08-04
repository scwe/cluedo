import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Loads a board from a given file and returns an array of tile objects
 * 
 * 
 * 
 * @author CF
 *
 */
public class BoardLoader {
	
	ContextGenerator cg;
	ArrayList<Tile> board;
	
	public BoardLoader(File map){
		
		cg = new ContextGenerator();
		Scanner scan;
		
		try {
			scan = new Scanner(map);
			board = new ArrayList<Tile>();
		
			int yloc = 0;
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				char[] lineChars = line.toCharArray();
				int xloc = 0;
				for (char c: lineChars){
					Location loc = new Location(xloc,yloc);
					String type = getType(c);
					Tile t = getTile(type,loc,c);
					xloc++;
					board.add(t);
				}
				yloc++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Tile> getBoard(){
		return this.board;
	}
	
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
			case "room-label":
				return new Hall(l, null, type, dc);
			case "boundary":
				return new Wall(l,null,type,dc);
		}
		return null;
	}
	
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
			default :
				return "room-label";
		}
	}
}
