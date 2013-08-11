package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import card.*;

import board.*;
import main.Player;
import java.util.*;

public class CardTests {
	TextBoard b;
	
	public CardTests(){
		b = new TextBoard();
	}

	/**
	 * Cardable tests
	 */
	@Test
	public void room_test1(){
		
		Room r = new Room("Patio 6 12 8 13 8 17 6 18", b);
		Room r2 = new Room("Dining_Room 13 20 16 22", b);
	}
	
	@Test
	public void room_test2(){
		
		Room r = new Room("Patio 6 12 8 13 8 17 6 18", b);
		Room r2 = new Room("Dining_Room 13 20 16 22", b);
		Room kitch = new Room("Kitchen 7 23", b);
		
		
		assertTrue(r.getSuspects().isEmpty());
		assertTrue(r.getName().equals("Patio"));
		assertTrue(r.getDoors().size() == 4);
	}
	
	@Test
	public void room_test3(){
		
		Room r = new Room("Patio 6 12 8 13 8 17 6 18", b);
		Room r2 = new Room("Dining_Room 13 20 16 22", b);
		Room kitch = new Room("Kitchen 7 23", b);
		
		
		assertFalse(r.hasSecretDoor());
		assertTrue(kitch.hasSecretDoor());
	}
	
	
	@Test
	public void weapon_test1(){
		
		Room r = new Room("Patio 6 12 8 13 8 17 6 18", b);
		Room r2 = new Room("Dining_Room 13 20 16 22", b);
		Room kitch = new Room("Kitchen 7 23", b);
		
		Weapon w1 = new Weapon("Sword", r);
		Weapon w2 = new Weapon("Knife", r2);
	}
	
	@Test
	public void weapon_test2(){
		
		Room r = new Room("Patio 6 12 8 13 8 17 6 18", b);
		Room r2 = new Room("Dining_Room 13 20 16 22", b);
		Room kitch = new Room("Kitchen 7 23", b);
		
		Weapon w1 = new Weapon("Sword", r);
		Weapon w2 = new Weapon("Knife", r2);
		
		assertEquals(w1, new Weapon("Sword", null));
		assertEquals(w1, new Weapon("Sword", r));
		
		assertEquals(w1.toString(), "Weapon Sword in "+r);
		assertEquals(w1.getName(), "Sword");
	}
	
	@Test
	public void suspect_test1(){
		
		Suspect s = new Suspect("Shit Name", new Location(5,5));
		Suspect s1 = new Suspect("Another name", new Location(5,5));
	}
	
	@Test
	public void suspect_test2(){
		
		Suspect s = new Suspect("Shit Name", new Location(5,5));
		Suspect s1 = new Suspect("Another name", new Location(5,5));
		Suspect s2 = new Suspect("a", null);
		
		assertFalse(s.equals(s1));
		assertTrue(s.equals(new Suspect("Shit Name", new Location(10,10))));
		assertEquals(s.getLocation(), s1.getLocation());
	}
	
	@Test
	public void suspect_test3(){
		
		Suspect s = new Suspect("Shit Name", new Location(5,5));
		Suspect s1 = new Suspect("Another name", new Location(5,5));
		Suspect s2 = new Suspect("a", null);
		
		assertTrue(s2.toString().equals("a who is nowhere"));
		assertTrue(s.toString().equals("Shit Name at (5, 5)"));
	}
	
	@Test
	public void card_test1(){
		Room r = new Room("Patio 6 12 8 13 8 17 6 18", b);
		Room r2 = new Room("Dining_Room 13 20 16 22", b);
		Room kitch = new Room("Kitchen 7 23", b);
		
		Card c = new Card(new Weapon("Sword", r));
		Card c1 = new Card(r);
		Card c2 = new Card(new Suspect("a", new Location(3,4)));
		Card c3 = new Card(new Suspect("a", null));
	}
	
	@Test
	public void card_test2(){
		Room r = new Room("Patio 6 12 8 13 8 17 6 18", b);
		Room r2 = new Room("Dining_Room 13 20 16 22", b);
		Room kitch = new Room("Kitchen 7 23", b);
		
		Card c = new Card(new Weapon("Sword", r));
		Card c1 = new Card(r);
		Card c2 = new Card(new Suspect("a", new Location(3,4)));
		Card c3 = new Card(new Suspect("a", null));
		
		assertEquals(c, c);
		assertFalse(c.equals(c1));
		assertTrue(c3.equals(new Card(new Suspect("a", null))));
		assertEquals(c1, new Card(r));
	}
	
	@Test
	public void keeper_test1(){
		Keeper[] keepers = new Keeper[8];
		
		for(int i = 0; i < keepers.length; i++){
			keepers[i] = new Keeper(Integer.toString(i));
		}
	}
	
	@Test
	public void clock_test1(){
		Clock c = new Clock(false);
		Clock d = new Clock(true);
		
		assertTrue(d.getLast());
		assertFalse(c.getLast());
		assertEquals(c.toString(), "Clock card, Deadly: false");
	}
	
	@Test
	public void deck_test1(){
		Deck<Card> d = new Deck<Card>();
		ArrayList<Card> a = new ArrayList<Card>();
		a.add(new Card(new Suspect("a", null)));
		a.add(new Card(new Suspect("b", null)));
		a.add(new Card(new Suspect("c", null)));
		
		d.addAll(a);
	}
	
	@Test
	public void deck_test2(){
		Deck<Card> d = new Deck<Card>();
		ArrayList<Card> a = new ArrayList<Card>();
		a.add(new Card(new Suspect("a", null)));
		a.add(new Card(new Suspect("b", null)));
		a.add(new Card(new Suspect("c", null)));
		
		d.addAll(a);
		d.setDeadlyClock();   //shouldn't do anything
		assertFalse(d.isEmpty());
		assertEquals(d.peek(), new Card(new Suspect("c", null)));
		assertEquals(d.pop(), new Card(new Suspect("c", null)));
		Player p = new Player(new Suspect("d", new Location(5,5)), new Hand<Card>());
		Queue<Player> q = new LinkedList<Player>();
		q.offer(p);
		
		d.deal(q);
		
		assertEquals(p.getHand().size(), 2);
		assertTrue(p.getHand().contains(new Card(new Suspect("a", null))));
		assertFalse(p.getHand().contains(new Weapon(null, null)));
		assertTrue(d.isEmpty());
		
	}
	
	@Test
	public void deck_test3(){
		Deck<IntrigueCard> d = new Deck<IntrigueCard>();
		ArrayList<IntrigueCard> a = new ArrayList<IntrigueCard>();
		a.add(new Keeper("1"));
		a.add(new Clock(false));
		a.add(new Clock(false));
		a.add(new Clock(false));
		
		d.addAll(a);
		
		d.setDeadlyClock();
		
		assertFalse(((Clock)(d.pop())).getLast());
		assertFalse(((Clock)(d.pop())).getLast());
		assertTrue(((Clock)(d.pop())).getLast());
	}
	
	@Test
	public void hand_test1(){
		Hand<Card> h = new Hand<Card>();
	}
	
	@Test
	public void hand_test2(){
		Hand<Card> h = new Hand<Card>();
		
		Card c1 = new Card(new Suspect("a", null));
		Card c2 = new Card(new Suspect("b", null));
		
		h.add(c1);
		assertTrue(h.size()==1);
		h.remove(0);
		assertTrue(h.size()==0);
		h.add(c1);
		h.clear();
		assertTrue(h.size()==0);
		h.add(c1);
		h.add(c2);
		h.remove(c1);
		assertTrue(h.contains(c2));
		assertEquals(h.toString().trim(), "Card of b who is nowhere");
	}
}
