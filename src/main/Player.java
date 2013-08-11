package main;
import java.util.ArrayList;

import card.Card;
import card.Suspect;
import card.Hand;
import card.Holdable;
import card.IntrigueCard;

public class Player {

	

	private Hand<Card> hand;
	private Hand<IntrigueCard> intrigueHand;
	private Suspect c;
	private int playerNumber;
	private boolean playerOutOfGame;


	public Player(Suspect c, Hand<Card> hand){
		this.c = c;
		this.hand = hand;
		intrigueHand = new Hand<IntrigueCard>();
		this.playerOutOfGame = false;
	}

	public void addCard(Holdable h){
		if(h instanceof Card){
			hand.add((Card)h);
		}else if(h instanceof IntrigueCard){
			intrigueHand.add((IntrigueCard)h);
		}
	}

	public void removeCard(Holdable h){
		if(h instanceof Card){
			hand.remove((Card)h);
		}else if(h instanceof IntrigueCard){
			intrigueHand.remove((IntrigueCard)h);
		}
	}

	public Card removeCard(int index){
		return hand.remove(index);
	}

	public Suspect getSuspect(){
		return c;
	}

	public void setSuspect(Suspect c){
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
	
	public Hand<IntrigueCard> getIntrigueHand(){
		return intrigueHand;
	}
	
	public char getShortName(){
		return getSuspect().getName().toUpperCase().charAt(0);
	}
	
	public boolean isPlayerOutOfGame() {
		return playerOutOfGame;
	}

	public void setPlayerOutOfGame(boolean playerOutOfGame) {
		this.playerOutOfGame = playerOutOfGame;
	}

	
}
