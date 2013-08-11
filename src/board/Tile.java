package board;

import java.awt.Graphics;

import main.Player;
import card.Suspect;
import card.Weapon;


public abstract class Tile {
	
	Location loc;

	Suspect suspect;
	Weapon wep;
	String type;
	DrawContext dc;
	
	public Tile(Location l, Suspect suspects, String t, DrawContext dc){

		this.loc = l;
		this.suspect = suspect;
		this.type = t;
		this.dc = dc;
	}

	public Location getLocation(){
		return this.loc;
	}
	
	public void setLocation(Location location){
		this.loc = location;
	}
	
	public Suspect getSuspectOn(){
		return this.suspect;
	}
	
	public Weapon getWeaponOn(){
		return this.wep;
	}
	
	public void setWeaponOn(Weapon wep){
		this.wep = wep;
		
	}
	
	public void setSuspectOn(Suspect suspect){
		this.suspect = suspect;
	}
	
	public String getTileType(){
		return this.type;
	}
	
	public void draw (Graphics g){
		
		if (this.suspect != null){
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

		if (this.suspect != null){
			System.out.print(Character.toString(suspect.getName().charAt(0)).toUpperCase());
		}
		else{
			System.out.print(Character.toString(suspect.getName().charAt(0)).toUpperCase());
		}
		
	}
	
	public void drawSpecialGraphics(Graphics g){
		
	}
	
	abstract public void drawText();
	abstract public void drawGraphics(Graphics g);
	abstract public boolean canMoveTo();
	
	@Override
	public String toString(){
		return "Location = "+loc.getX()+" "+loc.getY()+" type = "+type;
	}

}
