import java.awt.Graphics;


public class Wall extends Tile{

	public Wall(Location l, Character c, String t, String image) {
		super(l, c, t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawText() {
		System.out.println("#");
		
	}

	@Override
	public void drawGraphics(Graphics g) {
		
	}

}
