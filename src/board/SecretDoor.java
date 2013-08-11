package board;

import card.Suspect;

/**
 * The class that represents the Secret door that is from the kitchen to
 * the conservatory
 * @author scott
 *
 */
public class SecretDoor extends Door{

	public SecretDoor(Location l, Suspect suspect, String t, DrawContext dc) {
		super(l, suspect, t, dc);
	}
	
	@Override
	public String toString(){
		return "Secret door at "+loc;
	}


}
