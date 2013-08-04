import java.io.File;
import java.util.ArrayList;

public class TextBoard {
	
	ArrayList<Tile> board;
	
	public TextBoard(){

		File f = new File("Map");
		
		BoardLoader bl = new BoardLoader(f);
		board = bl.getBoard();
		System.out.println("Board size = "+board.size());
		drawBoard();
	}
	
	public void drawBoard(){
		
		for (int i = 0; i < board.size(); i++){
			
			if (i != 0 && i % 30 == 0){
				System.out.println();
			}
			board.get(i).draw(null);
			
		}
	}
	
	public Location getRoomFromString(String room){
		room = room.toLowerCase();
		switch(room){			//Just arbitrary locations for the moment
			case "spa":			//TODO fix this and make the board more sturdy
				return new Location(4, 3);
			case "theatre":
				return new Location(4, 12);  //although these locations are actually in the room
			case "living room":
				return new Location(4, 18);
			case "observatory":
				return new Location(4, 26);
			case "hall":
				return new Location(15, 26);
			case "guest room":
				return new Location(26, 26);
			case "dining room":
				return new Location(26, 14);
			case "kitchen":
				return new Location(26, 3);
			case "patio":
				return new Location(15, 3);
			case "pool":
				return new Location(15, 15);
		}
		
		return null;
	}
	
	public static void main (String[] args){
		
		TextBoard tb = new TextBoard();
	}
	
	

}
