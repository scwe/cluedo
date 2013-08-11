package board;

import java.awt.Graphics;

import card.Suspect;

import main.Player;



public class Hall extends Tile{


	public Hall(Location l, Suspect suspect, String t, DrawContext d) {
		super(l, suspect, t, d);
	}

	@Override
	public void drawText() {
		System.out.print(dc.getString());
	}

	@Override
	public void drawGraphics(Graphics g) {
		
	}

	@Override
	public boolean canMoveTo() {
		if(this.suspect == null){
			return true;
		}
		System.out.println("Fucking false");
		return false;
	}

}
