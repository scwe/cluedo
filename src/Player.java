import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand;
	private Character c;

	public Player(Character c, ArrayList<Card> hand){
		this.c = c;
		this.hand = hand;
	}

	public void addCard(Card c){
		hand.add(c);
	}

	public void removeCard(Card c){
		hand.remove(c);
	}

	public Card removeCard(int index){
		return hand.remove(index);
	}

	public Character getCharacter(){
		return c;
	}

	public void setCharacter(Character c){
		this.c = c;
	}

	public ArrayList<Card> getHand(){
		return hand;
	}

	public void setHand(ArrayList<Card> hand){
		this.hand = hand;
	}
	
}
