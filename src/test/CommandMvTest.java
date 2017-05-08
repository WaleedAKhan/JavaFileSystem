package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

import driver.*;

public class CommandMvTest {
  FileSystem fs;
  Folder f1,f2,f3;
  String command;
  String[] arguments;
 
  @Before
  public void setUp() throws Exception {
    fs = new FileSystem();
    command = "mkdir f1 f2";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs, arguments);
  
   }
  @Test
  public void rootFolderFolderMove() {
    command = "mv f1 f2";
    arguments = CommandParser.getArgs(command);
    CommandMv.objectMove(arguments[0], arguments[1], fs);
    //what we want to do is track the new location of f1 (should be in f2).
    //first, root shouldn't have f1 anymore, so:
    boolean rootHasF1 = true;
    if (fs.getFolderFromPath("f1") == null)
    {
      rootHasF1 = false;
    }
    //second, see if f1 is now in f2
    boolean F2HasF1 = false;
    if (fs.getFolderFromPath("f2/f1") != null)
    {
      F2HasF1 = true;
    }
    assertEquals((rootHasF1 == false) && (F2HasF1 == true), true);
    
  }
  @Test
  public void pathFolderFolderMove() {
    command = "cd f2";
    arguments = CommandParser.getArgs(command);
    CommandCd.cd(fs, arguments[0]);
    command = "mv ../f1 f2";
    arguments = CommandParser.getArgs(command);
    
    CommandMv.objectMove(arguments[0], arguments[1], fs);
    //Again, tracking the new location of f1 (should be in f2).
    //Root shouldn't have f1 anymore, so:
    
    boolean rootHasF1 = true;
    if (fs.getFolderFromPath("f1") == null)
    {
      rootHasF1 = false;
    }
    //See if f1 is now in f2
    boolean F2HasF2 = false;
    if (fs.getFolderFromPath("/f2/f2") != null)
    {
      F2HasF2 = true;
    }
    assertEquals((rootHasF1 == false) && (F2HasF2 == true) , true);
    
  }
  @Test
  public void recursiveFolderMove() {
    command = "mkdir f1/f2/f3 f4";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs, arguments);
    HashMap<String, Object> f1Folders = fs.getFolderFromPath("f1").
        getFolders(); 
    
    command = "mv f1 f2";
    arguments = CommandParser.getArgs(command);  
    CommandMv.objectMove(arguments[0], arguments[1], fs);
    
    //Tracking the new location of f1 (should be in f2).
    //Root shouldn't have f1 anymore, so:
    
    boolean rootHasF1 = true;
    if (fs.getFolderFromPath("f1") == null)
    {
      rootHasF1 = false;
    }
    //See if f1 is now in f2
    boolean F2HasF1 = false;
    if (fs.getFolderFromPath("/f2/f1") != null)
    {
      F2HasF1 = true;
    }
    
    //Check if the subFolders were recursively moved
    boolean subFolders = false;
    if(fs.getFolderFromPath("/f2/f1").getFolders() == f1Folders){
      subFolders = true;
    }    
    
    assertEquals((rootHasF1 == false) && (F2HasF1 == true) &&
        subFolders == true, true);
  }
  
  @Test
  public void illegalFolderMove() {
    command = "mkdir f10";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs, arguments);   
    command = "mv f1 f2";
    arguments = CommandParser.getArgs(command);  
    CommandMv.objectMove(arguments[0], arguments[1], fs);
    
   }
  @Test
  public void fileRenameMove() {
    command = "echo \"Hello World\" > file";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);   
    command = "mv file file2";
    arguments = CommandParser.getArgs(command);  
    CommandMv.objectMove(arguments[0], arguments[1], fs);
    
    //Check to see if root has File, it shouldn't
    boolean rootHasFile = true;
    if (fs.getFileFromPath("file") == null)
    {
      rootHasFile = false;
    }
  
    //Check to see if root has the new renamed file
    boolean rootHasFile2 = false;
    if (fs.getFileFromPath("file2") != null)
    {
      rootHasFile2 = true;
    }
    
    assertEquals(true, (rootHasFile == false) && (rootHasFile2 == true));   
    
   }
  @Test
  public void fileToFolderMove() {
    command = "echo \"Hello World\" > file";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);  
    
    command = "mkdir folder";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs, arguments);  
    
    command = "mv file folder";
    arguments = CommandParser.getArgs(command);  
    CommandMv.objectMove(arguments[0], arguments[1], fs);
    //Check if File has been removed from root
    boolean rootHasFile = true;
    if (fs.getFileFromPath("file") == null)
    {
      rootHasFile = false;
    }
    //Check if Folder Now contains that file
    boolean folderHasFile = false;
    if (fs.getFileFromPath("folder/file") != null)
    {
      folderHasFile = true;
    }
    
    assertEquals(true, (rootHasFile == false) && (folderHasFile == true));   
    
   }
  
  

}
