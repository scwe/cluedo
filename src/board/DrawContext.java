package board;
import java.awt.Image;

/**
 * A draw context object that can either have a string or an image and will supply
 * that based on how you which the object that it represents to be drawn
 * @author CF
 *
 */
public class DrawContext {

	private Image im;
 	private String st;
	
	public DrawContext(String str, Image image){
		im = image;
		st = str;
	}
	
	public Image getImage(){
		return this.im;
	}
	
	public String getString(){
		return this.st;
	}
	
}
