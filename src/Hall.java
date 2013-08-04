import java.awt.Graphics;


public class Hall extends Tile{

	public Hall(Location l, Character c, String t, DrawContext d) {
		super(l, c, t, d);
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
		if(this.chr == null){
			return true;
		}
		return false;
	}

}
