package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import driver.*;

public class CommandEchoTest {
  FileSystem fs;
  
  @Before
  public void setUp() throws Exception {
    fs = new FileSystem();
  }


  @Test
  public void echoFileInPWD() {
    FileSystem fs = new FileSystem();
    String command = "echo \"Hello World\" > file";
    String [] arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
    File file = fs.getFileObjFromName("file");
    assertFalse(null == file);
    String expected = "Hello World";
    assertEquals(file.toString(), expected);
  }
  
  @Test
  public void echoFileInRelativeDir() {
    FileSystem fs = new FileSystem();
    String [] args = {"a1"};
    CommandMkdir.mkdir(fs, args);
    
    String command = "echo \"Hello World\" > a1/file";
    String [] arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
    File file = fs.getFileFromPath("a1/file");
    assertFalse(null == file);
    String expected = "Hello World";
    assertEquals(file.toString(), expected);
  }
  
  @Test
  public void echoFileInAbsoluteDir() {
    FileSystem fs = new FileSystem();
    String [] args = {"a1"};
    CommandMkdir.mkdir(fs, args);
    
    String command = "echo \"Hello World\" > /a1/file";
    String [] arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
    File file = fs.getFileFromPath("a1/file");
    assertFalse(null == file);
    String expected = "Hello World";
    assertEquals(file.toString(), expected);
  }
  
  @Test
  public void echoAppendToFile() {
    FileSystem fs = new FileSystem();
    String command = "echo \"Hello World\" > file";
    String [] arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
    command = "echo \"Bye World\" >> file";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
    File file = fs.getFileObjFromName("file");
    assertFalse(null == file);
    String expected = "Hello World\nBye World";
    assertEquals(file.toString(), expected);
  }  
  
  @Test
  public void echoOverWriteFilesContent() {
    FileSystem fs = new FileSystem();
    String command = "echo \"Hello World\" > file";
    String [] arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
    command = "echo \"Bye World\" > file";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
    File file = fs.getFileObjFromName("file");
    assertFalse(null == file);
    String expected = "Bye World";
    assertEquals(file.toString(), expected);
  }
}
