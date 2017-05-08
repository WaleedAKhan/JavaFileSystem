package test;



import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import driver.*;

public class CommandCatTest {
  FileSystem fs;
  String command;
  String[] arguments;
 
  @Before
  public void setUp() throws Exception {
    fs = new FileSystem();

  }

  @Test
  public void catFileInPWDTest() {
	    command = "echo \"Hello World\" > f1";
	    arguments = CommandParser.getArgs(command);
	    CommandEcho.echo(fs,arguments);
	    assertEquals("Hello World", CommandCat.CommandCat("f1", fs));
  }
  @Test
  public void catFileRelDirectory(){
	  command = "mkdir folder1";
	  arguments = CommandParser.getArgs(command);
	  CommandMkdir.mkdir(fs,arguments);
	  
	  
	  command = "echo \"Hello World\" > folder1/f1";
	  arguments = CommandParser.getArgs(command);
	  CommandEcho.echo(fs,arguments);
	  String fileContent = CommandCat.CommandCat("folder1/f1", fs);
	  assertEquals("Hello World",fileContent);  
   }
  
  @Test
  public void catToFolderError(){
	  command = "mkdir folder1";
	  arguments = CommandParser.getArgs(command);
	  CommandMkdir.mkdir(fs,arguments);
	  //"@@DONOTPRINT@@" indicates error
	  assertEquals("@@DONOTPRINT@@", 
	      CommandCat.CommandCat("folder1", fs));  
	  
  }
  
  @Test
  public void catToAbsolutePath(){
	  command = "mkdir folder1";
	  arguments = CommandParser.getArgs(command);
	  CommandMkdir.mkdir(fs,arguments);
	  
	  
	  command = "echo \"Hello World\" > f1";
	  arguments = CommandParser.getArgs(command);
	  CommandEcho.echo(fs,arguments);
	  

	  command = "cd folder1";
	  arguments = CommandParser.getArgs(command);
	  CommandCd.cd(fs,arguments[0]);
	  	  
	  
	  String fileContent = CommandCat.CommandCat("/f1", fs);
	  assertEquals("Hello World",fileContent);  
	  
  }

}
