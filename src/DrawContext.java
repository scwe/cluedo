import java.awt.Image;


public class DrawContext {

	Image im;
	String st;
	
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
