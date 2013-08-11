package board;

import java.awt.Graphics;

import main.Player;


public class RoomTile extends Tile{

	public RoomTile(Location l, Player play, String t, DrawContext dc) {
		super(l, play, t, dc);
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
