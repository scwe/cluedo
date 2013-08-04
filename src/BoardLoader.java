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
	
	public BoardLoader(File map){
		
	
		Scanner scan;
		try {
			scan = new Scanner(map);
			ArrayList<Tile> board = new Board<Tile>();
			while(scan.hasNextLine()){
				
				String line = scan.nextLine();
				char[] lineChars = line.toCharArray();
				for (char c: lineChars){
					
					ArrayList<Tile> 
					
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
