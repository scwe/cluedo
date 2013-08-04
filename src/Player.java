import java.util.ArrayList;

public class Player {
	private ArrayList<Cardable> hand;
	private Character c;

	public Player(Character c, ArrayList<Cardable> hand){
		this.c = c;
		this.hand = hand;
	}

	public void addCard(Cardable c){
		hand.add(c);
	}

	public void removeCard(Cardable c){
		hand.remove(c);
	}

	public Cardable removeCard(int index){
		return hand.remove(index);
	}

	public Character getCharacter(){
		return c;
	}

	public void setCharacter(Character c){
		this.c = c;
	}

	public ArrayList<Cardable> getHand(){
		return hand;
	}

	public void setHand(ArrayList<Cardable> hand){
		this.hand = hand;
	}
	
}
