
public class ContextGenerator {
	
	
	
	public ContextGenerator(char c){
		
		createContext(c);
	
	}
	
	private DrawContext createContext (char c){
		
		switch (c){
		
		case '#':
			return new DrawContext("#",null);
		case ' ':
			return new DrawContext(" ",null);
		case '^':
			return new DrawContext("^",null);
		
		
	
	
	
		
	}

}
