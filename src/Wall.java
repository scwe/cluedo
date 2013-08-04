import java.awt.Graphics;


public class Wall extends Tile{

	public Wall(Location l, Character c, String t, DrawContext dc) {
		super(l, c, t, dc);
	}

	@Override
	public void drawText() {
		System.out.println(dc.getString());
		
	}

	@Override
	public void drawGraphics(Graphics g) {
		
	}

}
