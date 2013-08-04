
public class ContextGenerator {

	public ContextGenerator(String type){
		createContext(type);
	}
	
	public DrawContext createContext (String type){
		
		switch (type){
			case "wall":
				return new DrawContext("#",null);
			case "hall":
				return new DrawContext(" ",null);
			case "door-north":
				return new DrawContext("^",null);
			case "door-west":
				return new DrawContext(">",null);
			case "door-east":
				return new DrawContext("<",null);
			case "door-south":
				return new DrawContext("v",null);
			case "border":
				return new DrawContext("*",null);
			case "kitch-k":
				return new DrawContext("k",null);
			case "kitch-i":
				return new DrawContext("i",null);
			case "din-d":
				return new DrawContext("d",null);
			case "din-r":
				return new DrawContext("r",null);
			case "guest-g":
				return new DrawContext("g",null);
			case "guest-h":
				return new DrawContext("h",null);
			case "patio-p":
				return new DrawContext("p",null);
			case "patio-a":
				return new DrawContext("a",null);
			case "pool-p":
				return new DrawContext("p",null);
			case "pool-o":
				return new DrawContext("o",null);
			case "hall-h":
				return new DrawContext("h",null);
			case "hall-a":
				return new DrawContext("a",null);
			case "spa-s":
				return new DrawContext("s",null);
			case "theatre-t":
				return new DrawContext("t",null);
			case "living-l":
				return new DrawContext("l",null);
			case "living-r":
				return new DrawContext("r",null);
			case "cons-c":
				return new DrawContext("c",null);
		}
		return null;
	}
}
