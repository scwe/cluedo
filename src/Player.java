import java.util.ArrayList;

public class Player {

	private ArrayList<Card> hand;
	private ArrayList<IntrigueCard> intrigueHand;
	private Character c;
	private int playerNumber;

	public Player(Character c, ArrayList<Card> hand, int playNum){
		this.c = c;
		this.hand = hand;
		this.playerNumber = playNum;
		intrigueHand = new ArrayList<IntrigueCard>();
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
	
	@Override
	public String toString(){
		StringBuilder h = new StringBuilder();
		h.append("Character: "+c.toString()+" with hand: \n");
		for(Card c: hand){
			h.append("\t"+c.getCard().getName());
			h.append(",\n");
		}
		
		h.setCharAt(h.length() - 2, ' ');
		
		return h.toString();
	}
	
	public void printHand(){
		printHand(hand);
	}
	
	public void printIntrigueHand(){
		printHand(intrigueHand);
	}
	
	private <E extends Object>void printHand(ArrayList<E> hand){
		StringBuilder h = new StringBuilder();
		for(E e: hand){
			h.append(e.toString());
			h.append(", ");
		}
		
		h.setCharAt(h.length() - 2, ' ');  //set the last comma to nothing
		
		System.out.println(h.toString());
	}
	
	public boolean hasIntrigueCards(){
		return !intrigueHand.isEmpty();
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	public void addIntrigueCard(IntrigueCard i){
		intrigueHand.add(i);
	}
	
	public ArrayList<IntrigueCard> getIntrigueHand(){
		return intrigueHand;
	}

	
}
