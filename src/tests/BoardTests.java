package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import board.*;
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
    
}
