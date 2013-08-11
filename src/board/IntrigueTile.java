package board;

import java.awt.Graphics;

import card.Suspect;

import main.Player;


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
