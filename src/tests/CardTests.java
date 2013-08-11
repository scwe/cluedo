package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import card.*;

import board.*;

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
}
