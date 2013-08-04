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
	
	public static void main (String[] args){
		
		TextBoard tb = new TextBoard();
	}
	
	

}
