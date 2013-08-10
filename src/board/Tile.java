package board;

import java.awt.Graphics;

import card.Suspect;
import card.Weapon;


public abstract class Tile {
	
	Location loc;
	Suspect chr;
	Weapon wep;
	String type;
	DrawContext dc;
	public Tile(Location l, Suspect c, String t, DrawContext dc){
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
	
	public Suspect getCharacterOn(){
		return this.chr;
	}
	
	public Weapon getWeaponOn(){
		return this.wep;
	}
	
	public void setWeaponOn(Weapon wep){
		this.wep = wep;
		
	}
	
	public void setCharacterOn(Suspect character){
		this.chr = character;
	}
	
	public String getTileType(){
		return this.type;
	}
	
	public void draw (Graphics g){
		
		if (this.chr != null){
			if (g == null){
				drawSpecial();
			}
			else {
				drawSpecialGraphics(g);
			}
		}
		else{
			if (g == null){
				drawText();
			}
			else{
				drawGraphics(g);
			}
		}
	}
	
	public void drawSpecial(){
		if (this.chr != null){
			System.out.print(Character.toString(chr.getName().charAt(0)).toLowerCase());
		}
		else{
			System.out.print(Character.toString(chr.getName().charAt(0)).toLowerCase());
		}
		
	}
	
	public void drawSpecialGraphics(Graphics g){
		
	}
	
	abstract public void drawText();
	abstract public void drawGraphics(Graphics g);
	abstract public boolean canMoveTo();
	
	public String toString(){
		
		return "Location = "+loc.getX()+" "+loc.getY()+" type = "+type;
	}

}
