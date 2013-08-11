package board;

/**
 * A class for generating the string to be drawn given an input type or label
 * @author CF
 *
 */
public class ContextGenerator {

	/**
	 * Creates the context
	 * @param type
	 * 		The type of tile that is to be drawn
	 * @param label
	 * 		The label of the room, if any
	 * @return
	 * 		A draw context with the correct way to be drawn
	 */
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
			case "room":
				return new DrawContext("_", null);
			case "intrigue":
				return new DrawContext("?", null);
			case "secret-door":
				return new DrawContext("8", null);
			case "room-label":
				String val = Character.toString(label);
				return new DrawContext(val,null);
		}
		return null;
	}
}
