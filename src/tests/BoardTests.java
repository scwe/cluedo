package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import board.*;
import card.*;
import java.util.*;
import java.io.*;


public class BoardTests {

    @Test
    public void boardLoader_test1() {
        BoardLoader b = new BoardLoader(new File("Map"), 31, 30); 
        b.getBoard();
    }

    @Test
    public void contextGenerator_test1() {
        ContextGenerator cg = new ContextGenerator();
    }
    
    @Test
    public void contextGenerator_test2() {
        assertEquals(new ContextGenerator().createContext("hall", ' ').getString(), " ");
        assertEquals(new ContextGenerator().createContext("wall", ' ').getString(), "#");
        assertEquals(new ContextGenerator().createContext("door-north", ' ').getString(), "^");
        assertEquals(new ContextGenerator().createContext("room-label", 'h').getString(), "h");
    }


    @Test
    public void drawContext_text1(){
    	DrawContext dw  = new DrawContext("hallway", null);
    	DrawContext dw2 = new DrawContext("", null);
    	DrawContext dw3 = new DrawContext(null, null);
    }
    
    @Test
    public void drawContext_text2(){
    	DrawContext dw  = new DrawContext("hallway", null);
    	assertEquals(dw.getString(), "hallway");
    	assertFalse(new DrawContext("something", null).getString().equals("another thing"));
    }
    
    @Test
    public void textBoard_test1(){
    	TextBoard b = new TextBoard();
    }

    @Test
    public void textBoard_test2(){
    	TextBoard b = new TextBoard();
    	
    	assertEquals(b.getStartLocation(0), new Location(7, 1));
    	assertEquals(b.getStartLocation(3), new Location(8, 29));
    	assertEquals(b.getStartLocation(4), new Location(1, 20));
    	
    	assertFalse(b.getStartLocation(2).equals(new Location(50, 30)));
    	assertFalse(b.getStartLocation(5).equals(new Location(50, 30)));
    	assertFalse(b.getStartLocation(1).equals(new Location(50, 30)));
    	
    	assertNull(b.getStartLocation(10));
    	assertNull(b.getStartLocation(6));
    }
    
    @Test
    public void textBoard_test3(){
    	TextBoard b = new TextBoard();
    	
    	assertEquals(b.findLocation(new Location(6, 5), "s"), new Location(6,6));
    	assertEquals(b.findLocation(new Location(6, 5), "n"), new Location(6,4));
    	assertEquals(b.findLocation(new Location(6, 5), "e"), new Location(7,5));
    	assertEquals(b.findLocation(new Location(6, 5), "w"), new Location(5,5));
    	
    	assertFalse(b.findLocation(new Location(6, 5), "s").equals(new Location(50, 30)));
    	assertFalse(b.findLocation(new Location(6, 5), "n").equals(new Location(50, 30)));
    	assertFalse(b.findLocation(new Location(45, 2), "e").equals(new Location(50, 30)));
    }
    
    @Test
    public void textBoard_test4(){
    	TextBoard b = new TextBoard();
    	
    	assertTrue(b.canMoveTo(new Location(7,7)));
    	assertFalse(b.canMoveTo(new Location(-1,-1)));
    	assertFalse(b.canMoveTo(new Location(50,50)));
    	assertFalse(b.canMoveTo(new Location(15, 15)));
    	assertFalse(b.canMoveTo(new Location(0, 0)));
    	
    }
    
    @Test
    public void textBoard_test5(){
    	TextBoard b = new TextBoard();
    	
    	b.getTile(new Location(6,7)).setSuspectOn(new Suspect("Some name", new Location(6, 7)));
    	
    	assertTrue(b.canMoveTo(new Location(6, 6)));
    	b.getTile(new Location(6,7)).setSuspectOn(null);
    	assertFalse(b.canMoveTo(new Location(6, 6)));
    	b.getTile(new Location(6,9)).setSuspectOn(new Suspect("Some name", new Location(6, 9)));
    	
    }
    
    
    @Test
    public void location_test1(){
    	Location l = new Location(109238,98032457);
    	Location l1 = new Location(5,-4);
    	Location l2 = new Location(5, 20);
    	Location l3 = new Location(45,12);
    }
    
    @Test
    public void location_test2(){
    	Location l = new Location(5, 20);
    	assertEquals(l.getX(), 5);
    	assertEquals(l.getY(), 20);
    	l.setX(6);
    	l.setY(70);
    	assertEquals(l.getX(), 6);
    	assertEquals(l.getY(), 70);
    	assertFalse(l.getX() == 70);
    	assertFalse(l.getY() == 40);
    }
    
    @Test
    public void location_test3(){
    	assertEquals(new Location(5,5), new Location(5,5));
    	assertFalse(new Location(5,5).equals("hello"));
    	assertFalse(new Location(5,5).equals(new Location(4,4)));
    	assertEquals(new Location(5,5).toString(),"(5, 5)");
    }
    
    @Test
    public void intrigueTile_test1(){
    	IntrigueTile i = new IntrigueTile(new Location(5,5), null, "intrigue", new DrawContext("?", null));
    	assertTrue(i.canMoveTo());
    }
    
    @Test
    public void hallTile_test1(){
    	Hall i = new Hall(new Location(5,5), null, "hall", new DrawContext(" ", null));
    	assertTrue(i.canMoveTo());
    }
    
    @Test
    public void wall_test1(){
    	Wall i = new Wall(new Location(5,5), null, "wall", new DrawContext("#", null));
    	assertFalse(i.canMoveTo());
    }
}
