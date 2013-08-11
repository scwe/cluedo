package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import logic.*;
import card.*
;
import board.*;

public class LogicTest {

	@Test
	public void declaration_test1() {
		TextBoard b = new TextBoard();
		
		Room r = new Room("Guest_House 22 23 23 22", b);
		Suspect s = new Suspect("a", null);
		Weapon w = new Weapon("Sword", null);
		
		Declaration d = new Declaration(s, w, r);
	}
	
	@Test
	public void declaration_test2() {
		TextBoard b = new TextBoard();
		
		Room r = new Room("Guest_House 22 23 23 22", b);
		Suspect s = new Suspect("a", null);
		Weapon w = new Weapon("Sword", null);
		
		Declaration d = new Declaration(s, w, r);
		
		d.setAccusedRoom(r);
		d.setRoom(r);
		d.setSuspect(s);
		d.setWeapon(w);
		
		assertEquals(d.getAccusedRoom(), r);
		assertEquals(d.getRoom(), r);
		assertEquals(d.getSuspect(), s);
		assertEquals(d.getWeapon(), w);
	}

	@Test
	public void moveRecord_test1(){
		MoveRecord m = new MoveRecord();
		Room r = new Room("Patio 5 16 7 20", new TextBoard());
		IntrigueCard i = new Clock(false);
		
		m.setIc(i);
		m.setRm(r);
		m.setCanAccuse(true);
		
		assertTrue(m.canAccuse());
		assertEquals(m.getRm(), r);
		assertEquals(m.getIc(), i);
	}
	
}
