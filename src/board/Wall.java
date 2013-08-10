package board;

import java.awt.Graphics;

import main.Player;



public class Wall extends Tile{


	public Wall(Location l, Player p, String t, DrawContext dc) {
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
		return false;
	}

}
