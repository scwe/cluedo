import java.awt.Graphics;


public class Hall extends Tile{

	public Hall(Location l, Character c, String t, DrawContext d) {
		super(l, c, t, d);
	}

	@Override
	public void drawText() {
		System.out.println(dc.getString());
		
	}

	@Override
	public void drawGraphics(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
