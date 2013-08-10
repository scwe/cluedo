package board;

import java.awt.Graphics;

import main.Player;

import card.Character;



public class Door extends Tile{

	public Door(Location l, Player p, String t, DrawContext dc) {
		super(l, p, t, dc);
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
		if(this.player == null){
			return true;
		}
		return false;
	}


}
