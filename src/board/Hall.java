package board;

import java.awt.Graphics;

import main.Player;



public class Hall extends Tile{


	public Hall(Location l, Player p, String t, DrawContext d) {
		super(l, p, t, d);
	}

	@Override
	public void drawText() {
		System.out.print(dc.getString());
	}

	@Override
	public void drawGraphics(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canMoveTo() {
		if(this.player == null){
			return true;
		}
		System.out.println("Fucking false");
		return false;
	}

}
