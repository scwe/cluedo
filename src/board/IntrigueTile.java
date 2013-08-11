package board;

import java.awt.Graphics;

import card.Suspect;

/**
 * The tile to represent the ? on the board, the intrigue tile
 * @author scott
 *
 */
public class IntrigueTile extends Tile{

	public IntrigueTile(Location l, Suspect suspect, String t, DrawContext dc) {
		super(l,suspect , t, dc);
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
		return true;
	}

}
