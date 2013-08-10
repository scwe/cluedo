package main;
import java.util.ArrayList;

import card.Card;
import card.Character;
import card.Hand;
import card.IntrigueCard;

public class Player {

	private Hand<Card> hand;
	private Hand<IntrigueCard> intrigueHand;
	private Character c;
	private int playerNumber;


	public Player(Character c, Hand<Card> hand){
		this.c = c;
		this.hand = hand;
		intrigueHand = new Hand<IntrigueCard>();
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

	public Hand<Card> getHand(){
		return hand;
	}

	public void setHand(Hand<Card> hand){
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
		System.out.println(hand);
	}
	
	public void printIntrigueHand(){
		System.out.println(intrigueHand);
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
	
	public Hand<IntrigueCard> getIntrigueHand(){
		return intrigueHand;
	}
	
	public char getShortName(){
		return getCharacter().getName().toUpperCase().charAt(0);
	}

	
}
