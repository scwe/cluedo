package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import main.*;
import board.*;
import card.*;

public class MainTests {
	TextBoard t = new TextBoard();
	Suspect s = new Suspect("a", null);

	@Test
	public void player_test1() {
		Player p = new Player(s, new Hand<Card>());
	}
	
	@Test
	public void player_test2() {
		Player p = new Player(s, new Hand<Card>());
		
		assertEquals(p.getSuspect(), s);
		assertTrue(p.getHand().isEmpty());
		assertTrue(p.getShortName()=='A');
		assertEquals(p.getPlayerNumber(), 0);
		assertTrue(p.getIntrigueHand().isEmpty());
	}
	
	@Test
	public void player_test3() {
		Player p = new Player(s, new Hand<Card>());
		
		p.addCard(new Card(s));
		p.addCard(new Clock(false));
		
		assertTrue(p.getHand().size()==1);
		assertTrue(p.getIntrigueHand().size()==1);
		assertTrue(p.hasIntrigueCards());
	}
	
	@Test
	public void player_test4() {
		Player p = new Player(s, new Hand<Card>());
		
		p.addCard(new Card(s));
		p.addCard(new Clock(false));
		
		assertFalse(p.isPlayerOutOfGame());
		p.setPlayerOutOfGame(true);
		assertTrue(p.isPlayerOutOfGame());
	}
	
	@Test
	public void player_test5() {
		Player p = new Player(s, new Hand<Card>());
		
		p.addCard(new Card(s));
		p.addCard(new Clock(false));
		
		p.setSuspect(new Suspect("b", null));
		assertFalse(p.getSuspect().equals(s));
		
	}
	
	@Test
	public void player_test6() {
		Player p = new Player(s, new Hand<Card>());
		
		p.addCard(new Card(s));
		p.addCard(new Clock(false));
		
		assertTrue(p.getPlayerNumber()==0);
		p.setPlayerNumber(3);
		assertTrue(p.getPlayerNumber()==3);
		
	}
	
	@Test
	public void player_test7() {
		Player p = new Player(s, new Hand<Card>());
		
		p.addCard(new Card(s));
		p.addCard(new Clock(false));
		
		assertTrue(p.hasIntrigueCards());
		
		p.removeCard(new Card(s));
		
		assertTrue(p.getHand().size()==0);
		assertTrue(p.getIntrigueHand().size()==1);
		
	}
	
	@Test
	public void player_test8() {
		Player p = new Player(s, new Hand<Card>());
		
		p.addCard(new Card(s));
		p.addCard(new Clock(false));
		
		assertEquals(p.toString().trim(), "Character: a who is nowhere with hand: \n\ta");
		
	}

}
