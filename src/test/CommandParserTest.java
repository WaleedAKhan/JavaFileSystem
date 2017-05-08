package test;

import static org.junit.Assert.*;
import driver.*;

import org.junit.Before;
import org.junit.Test;

public class CommandParserTest {
  FileSystem fs;
  @Before
  public void setup(){
    fs = new FileSystem();
  }
  
  
  @Test
  public void ComParserValidParamTestForMv() {    
    String command = "mv a1 a2";
    assertTrue(CommandParser.validCommand(command));
  }
  
  @Test
  public void ComParserValidParamTestForEcho() {    
    String command = "echo \"Hello World\"";
    assertTrue(CommandParser.validCommand(command));
  }
  
  @Test
  public void ComParserValidParamCountForEcho() {
    String command = "echo \"Hello World\"";
    String[] args = CommandParser.getArgs(command);
    String cmd = CommandParser.getCommand(command);
    assertTrue(CommandParser.validParamCount(cmd, args));
  }
  
  @Test
  public void ComParserGetArgsTestForEcho() {
    String command = "echo \"Hello World\"";
    String cmd = CommandParser.getCommand(command);  
    assertTrue(cmd.equals("echo"));
  }  

}
