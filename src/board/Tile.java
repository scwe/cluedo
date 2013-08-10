package board;

import java.awt.Graphics;

import main.Player;
import card.Weapon;


public abstract class Tile {
	
	Location loc;

	Player player;
	Weapon wep;
	String type;
	DrawContext dc;
	
	public Tile(Location l, Player play, String t, DrawContext dc){

		this.loc = l;
		this.player = play;
		this.type = t;
		this.dc = dc;
	}

	public Location getLocation(){
		return this.loc;
	}
	
	public void setLocation(Location location){
		this.loc = location;
	}
	
	public Player getPlayerOn(){
		return this.player;
	}
	
	public Weapon getWeaponOn(){
		return this.wep;
	}
	
	public void setWeaponOn(Weapon wep){
		this.wep = wep;
		
	}
	
	public void setPlayerOn(Player play){
		this.player = play;
	}
	
	public String getTileType(){
		return this.type;
	}
	
	public void draw (Graphics g){
		
		if (this.player != null){
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

		if (this.player != null){
			System.out.print(Character.toString(player.getCharacter().getName().charAt(0)).toUpperCase());
		}
		else{
			System.out.print(Character.toString(player.getCharacter().getName().charAt(0)).toUpperCase());
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
