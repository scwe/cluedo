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
    }

    @Test
    public void contextGenerator_test1() {
        ContextGenerator cg = new ContextGenerator();
    }

    @Test
    public void contextGenerator_test2() {
        ContextGenerator cg = new ContextGenerator();
        DrawContext dw = cg.createContext("wall");
        assertEquals(dw.getString(), "#");
        dw = cg.createContext("door-south");
        assertEquals(dw.getString(), "v");
        dw = cg.createContext("intrigue");
        assertEquals(dw.getString(), "?");
        dw = cg.createContext("hall");
        assertEquals(dw.getString(), " ");
    }

    @Test
    public void contextGenerator_test3() {
        ContextGenerator cg = new ContextGenerator();
        DrawContext dw = cg.createContext("wal");
        assertEquals(dw.getString(), "#");
        dw = cg.createContext("door-north-east");
        assertEquals(dw.getString(), null);
        dw = cg.createContext("fail");
        assertEquals(dw.getString(), "?");
        dw = cg.createContext("somethign that doesn't exists");
        assertEquals(dw.getString(), " ");
    }

    @Testpublic void drawContext_text1(){
    	DrawContext dw  = new DrawContext("hallway", null);
    	DrawContext dw2 = new DrawContext("", null);
    }

}
