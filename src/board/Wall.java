package board;

import java.awt.Graphics;

import card.Suspect;


/**
 * The tile to represent the 
 * @author CF
 *
 */
public class Wall extends Tile{


	public Wall(Location l, Suspect suspect, String t, DrawContext dc) {
		super(l, suspect, t, dc);
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
