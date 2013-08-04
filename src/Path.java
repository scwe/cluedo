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
	private Location startLocation;
	private Location endLocation;
	
	public Path(Location startLocation, String input){
		this.startLocation = startLocation;
		path = new ArrayList<Direction>();
		String[] directions = input.split(", ");
		
		for(String s : directions){
			path.add(Direction.fromString(s.trim()));
		}
		
		findEndLocation();
	}
	
	public Path(Location startLocation, String[] input){
		this.startLocation = startLocation;
		path = new ArrayList<Direction>();
		
		for(String s : input){
			path.add(Direction.fromString(s.trim()));
		}
		
		findEndLocation();
	}
	
	public Path(Location startLocation, ArrayList<Direction> path){
		this.startLocation = startLocation;
		this.path = path;
		findEndLocation();
	}
	
	public boolean isValid(TextBoard board){
		Location l = new Location(startLocation.getX(), startLocation.getY());
		for(Direction d : path){
			switch(d){
				case NORTH:
					l.setY(l.getY()-1);
					if(!board.getTile(l).canMoveTo()) return false;
				case EAST:
					l.setX(l.getX()+1);
					if(!board.getTile(l).canMoveTo()) return false;
				case SOUTH:
					l.setY(l.getY()+1);
					if(!board.getTile(l).canMoveTo()) return false;
				case WEST:
					l.setX(l.getX()-1);
					if(!board.getTile(l).canMoveTo()) return false;
			}
		}
		return true;
	}
	
	private void findEndLocation(){
		int xDiff = 0;
		int yDiff = 0;
		
		for(Direction d: path){
			switch(d){
				case NORTH:
					yDiff--;
				case EAST:
					xDiff++;
				case SOUTH:
					yDiff++;
				case WEST:
					xDiff--;
			}
		}
		
		endLocation = new Location(startLocation.getX() + xDiff, startLocation.getY() + yDiff);
	}
	
	public Location getStartLocation(){
		return startLocation;
	}
	
	public Location getEndLocation(){
		return endLocation;
	}
	
	public int pathLength(){
		return path.size();
	}
}
