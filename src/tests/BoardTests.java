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
    public void drawContext_text1(){
    	DrawContext dw  = new DrawContext("hallway", null);
    	DrawContext dw2 = new DrawContext("", null);
    }

}
