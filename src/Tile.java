import java.awt.Graphics;


public abstract class Tile {
	
	Location loc;
	Character chr;
	String type;
	DrawContext dc;
	public Tile(Location l, Character c, String t, DrawContext dc){
		this.loc = l;
		this.chr = c;
		this.type = t;
		this.dc = dc;
	}

	public Location getLocation(){
		return this.loc;
	}
	
	public void setLocation(Location location){
		this.loc = location;
	}
	
	public Character getCharacterOn(){
		return this.chr;
	}
	
	public void setCharacterOn(Character character){
		this.chr = character;
	}
	
	public String getTileType(){
		return this.type;
	}
	
	public void draw (Graphics g){
		
		if (g == null){
			drawText();
		}
		else{
			drawGraphics(g);
		}
	}
	
	abstract public void drawText();
	abstract public void drawGraphics(Graphics g);
	abstract public boolean canMoveTo();
	
	public String toString(){
		
		return "Location = "+loc.getX()+" "+loc.getY()+" type = "+type;
	}

}
