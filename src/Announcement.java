import java.util.ArrayList;

public class Announcement {
	
	private ArrayList<Cardable> cards;
	
	public Announcement(Cardable room, Cardable character, Cardable weapon){
		this.cards = new ArrayList<Cardable>();
	}
	
	public boolean isValid(){
		return false;
	}
}
