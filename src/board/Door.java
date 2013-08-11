package board;

import java.awt.Graphics;

import card.Suspect;



public class Door extends Tile{


	public Door(Location l, Suspect suspect, String t, DrawContext dc) {
		super(l, suspect, t, dc);
	}

	@Override
	public void drawText() {
		System.out.print(dc.getString());
	}

	@Override
	public void drawGraphics(Graphics g) {
		
	}
	
	public Location getExit(){
		return this.loc;
	}

	@Override
	public boolean canMoveTo() {
		if(this.suspect == null){
			return true;
		}
		return false;
	}


}
