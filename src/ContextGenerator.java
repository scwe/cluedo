public class ContextGenerator {


	public DrawContext createContext (String type, char label){
		
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
			case "boundary":
				return new DrawContext("*",null);
			case "room-label":
				String val = java.lang.Character.toString(label);
				return new DrawContext(val,null);
		}
		return null;
	}
}
