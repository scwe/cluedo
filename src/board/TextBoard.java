package board;

import java.io.File;

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

	public Location getRoomFromString(String room) {
		room = room.toLowerCase().trim();
		switch (room) {
		case "spa":
			return new Location(5, 5); // STURDY
		case "theatre":
			return new Location(11, 5); // although these locations are actually in the room
		case "living room":
			return new Location(17, 5);
		case "conservatory":
			return new Location(25, 5); // STURDY
		case "patio":
			return new Location(5, 15); // YOLOSTURD
		case "pool":
			return new Location(14, 15); // SOSTURD720
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
					System.out.println("The suspect there y-1 "+testLoc+" is null");
					return false;
				}
				break;
			case "v":
				if(getTile(new Location(testLoc.getX(), testLoc.getY()+1)).getSuspectOn() == null){
					System.out.println("The suspect there y+1 "+testLoc+" is null");
					return false;
				}
				break;
			case "<":
				if(getTile(new Location(testLoc.getX()-1, testLoc.getY())).getSuspectOn() == null){
					System.out.println("The suspect there x-1 "+testLoc+" is null");
					return false;
				}
				break;
			case ">":
				if(getTile(new Location(testLoc.getX()+1, testLoc.getY())).getSuspectOn() == null){
					System.out.println("The suspect there x+1 "+testLoc+" is null");
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


}
