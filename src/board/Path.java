package board;

import java.util.*;


public class Path {
	private enum Direction{   //this can be made public if we need it, but I don't think we will
		NORTH,
		EAST,
		SOUTH,
		WEST;
		
		
		public static Direction fromChar(char d){
			switch (d){
				case'n':
					return NORTH;
				case 'e':
					return EAST;
				case 's':
					return SOUTH;
				case 'w':
					return WEST;
			}	
			return null;
		}
	}
	
	private ArrayList<Direction> path;
	private Location startLocation;
	private Location endLocation;
	
	public Path(Location startLocation, String input){
		this.startLocation = startLocation;
		path = new ArrayList<Direction>();
		char[] directions = input.toCharArray();
		
		for(char dir : directions){
			path.add(Direction.fromChar(dir));
		}
		
		findEndLocation();
	}
	
	public boolean createPath(Location startLocation, String input){
		
		this.startLocation = startLocation;
		path = new ArrayList<Direction>();
		char[] directions = input.toCharArray();
		
		for(char dir : directions){
			path.add(Direction.fromChar(dir));
		}
		
		try{
			findEndLocation();
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	public Path(Location startLocation, ArrayList<Direction> path){
		this.startLocation = startLocation;
		this.path = path;
		this.endLocation = findEndLocation();
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
	
	private Location findEndLocation(){
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
		
		return new Location(startLocation.getX() + xDiff, startLocation.getY() + yDiff);
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
