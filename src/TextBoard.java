import java.io.File;
import java.util.ArrayList;

public class TextBoard {
	public static int ROW_LENGTH = 30;
	private ArrayList<Tile> board;
	
	public TextBoard(){

		File f = new File("Map");
		
		BoardLoader bl = new BoardLoader(f);
		board = bl.getBoard();
		drawBoard();
	}
	
	public void drawBoard(){
		
		for (int i = 0; i < board.size(); i++){
			
			if (i != 0 && i % ROW_LENGTH == 0){
				System.out.println();
			}
			board.get(i).draw(null);
			
		}
		System.out.println(); //Just to make sure there is a blank line between printing
	}

	
	public Location getRoomFromString(String room){
		room = room.toLowerCase().trim();
		switch(room){
			case "spa":	
				return new Location(5, 5);	//STURDY
			case "theatre":
				return new Location(11, 5);  //although these locations are actually in the room
			case "living room":
				return new Location(17, 5);
			case "conservatory":
				return new Location(25, 5); //STURDY
			case "patio":
				return new Location(5, 15);	//YOLOSTURD
			case "pool":
				return new Location(14, 15); //SOSTURD720
			case "hall":
				return new Location(25, 15);
			case "kitchen":
				return new Location(5, 26);
			case "dining room":
				return new Location(13, 26);
			case "guest house":
				return new Location(27, 26);

		}
		
		return null;
	}
	
	public Location getStartLocation(int i){
		if(i == 0){
			return new Location(7, 1);
		}else if(i == 1){
			return new Location(21, 1);
		}else if(i == 2){
			return new Location(29, 19);
		}else if(i == 3){
			return new Location(29, 8);
		}else if(i == 4){
			return new Location(20, 1);
		}else if(i == 5){
			return new Location(10, 1);
		}
		
		return null;
	}
	
	public Tile getTile(Location l){
		return board.get(ROW_LENGTH*l.getX()+l.getY());
	}
	
	public static void main (String[] args){
		
		TextBoard tb = new TextBoard();
	}
	
	

}
