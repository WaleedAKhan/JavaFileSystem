package test;

import static org.junit.Assert.*;
import driver.*;

import org.junit.Before;
import org.junit.Test;

public class outPrintStreamTest {

  @Before
  public void setup() {
    outPrintStream.clearContent();
    outPrintStream.enablePrinting();
  }
  
  
  @Test
  public void getContentTest() {
    outPrintStream.disablePrinting();
    outPrintStream.println("Hello");
    outPrintStream.println("World");
    System.out.println(outPrintStream.getContent());
    assertEquals(outPrintStream.getContent(), "\nHello\nWorld");
  }

  @Test
  public void enablePrintingTest() {
    outPrintStream.enablePrinting();
    assertEquals(outPrintStream.isCanPrint(), true);
  }
  
  @Test
  public void disablePrintingTest() {
    outPrintStream.disablePrinting();
    assertEquals(outPrintStream.isCanPrint(), false);
  }

  @Test
  public void clearContentTest(){
    outPrintStream.disablePrinting();
    outPrintStream.println("Hello");
    outPrintStream.println("World");
    System.out.println(outPrintStream.getContent());
    assertEquals(outPrintStream.getContent(), "\nHello\nWorld");
    
    outPrintStream.clearContent();
    assertEquals(outPrintStream.getContent(), "");
  }
}
