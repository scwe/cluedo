import java.util.*;

public class Path {
	public enum Direction{
		NORTH,
		EAST,
		SOUTH,
		WEST;
		
		
		public static Direction fromString(String s){
			if(s.equalsIgnoreCase("north")){
				return NORTH;
			}else if(s.equalsIgnoreCase("east")){
				return EAST;
			}else if(s.equalsIgnoreCase("south")){
				return SOUTH;
			}else if(s.equalsIgnoreCase("west")){
				return WEST;
			}else{
				return null;
			}
		}
	}
	
	private ArrayList<Direction> path;
	
	public Path(String input){
		path = new ArrayList<Direction>();
		String[] directions = input.split(", ");
		
		for(String s : directions){
			path.add(Direction.fromString(s.trim()));
		}
	}
	
	public Path(String[] input){
		path = new ArrayList<Direction>();
		
		for(String s : input){
			path.add(Direction.fromString(s.trim()));
		}
	}
	
	public Path(ArrayList<Direction> path){
		this.path = path;
	}
	
	public int pathLength(){
		return path.size();
	}
}
