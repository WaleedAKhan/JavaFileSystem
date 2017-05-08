package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

import driver.*;

public class CommandCpTest {
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
    
    command = "echo \"Hello World\" > file";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);   
  
   }
  @Test
  public void rootFolderFolderCopy() {
    command = "cp f1 f2";
    arguments = CommandParser.getArgs(command);
    CommandCp.objectCopy(arguments[0], arguments[1], fs);
    
    //f1 should now be in both root and f2
    boolean rootHasF1 = false;
    if (fs.getFolderFromPath("f1") != null)
    {
      rootHasF1 = true;
    }
    
    //Check if f1 is now in f2
    boolean F2HasF1 = false;
    if (fs.getFolderFromPath("f2/f1") != null)
    {
      F2HasF1 = true;
    }
    assertEquals(true, (rootHasF1 == true) && (F2HasF1 == true));
    
  }

 
  @Test
  public void recursiveFolderCopy() {
    command = "mkdir f1/f2 f4";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs, arguments);
    
    HashMap<String, Object> f1Folders = fs.getFolderFromPath("f1").
        getFolders();
    
    command = "cp f1 f4";
    arguments = CommandParser.getArgs(command);  
    CommandCp.objectCopy(arguments[0], arguments[1], fs);
    
    //Tracking the new location of f2 (should be in f4).
    //Root should also have f1:
    
    boolean rootHasF1 = false;
    if (fs.getFolderFromPath("f1") != null)
    {
      rootHasF1 = true;
    }
    //See if f1 is now in f2
    boolean F4HasF1 = false;
    if (fs.getFolderFromPath("/f4/f1") != null)
    {
      F4HasF1 = true;
    }
    
    //Check if the subFolders were recursively copied, irrespective of 
    //uniqueness
    boolean subFolders = false;
    HashMap<String, Object> f4Folders =
        fs.getFolderFromPath("f4/f1").getFolders();
    if(f4Folders.keySet().equals((f1Folders).keySet())){
      subFolders = true;
    }    
    
    //Check if the subFolders were recursively copied but made independent
    //of the original f1
    boolean subFoldersUnique = false;
    if(!f4Folders.equals((f1Folders))){
      subFoldersUnique = true;
    }    
    
    assertEquals(true, (rootHasF1 == true) && (F4HasF1 == true) &&
        subFolders == true &&  subFoldersUnique == true);
  }
  
  @Test
  public void illegalFolderToFileCopy() {
    
    
    command = "cp f1 file";
    arguments = CommandParser.getArgs(command);  
    CommandCp.objectCopy(arguments[0], arguments[1], fs);
    
   }
  @Test
  public void fileToFolderCopy() {
    
    command = "cp file f1";
    arguments = CommandParser.getArgs(command);  
    CommandCp.objectCopy(arguments[0], arguments[1], fs);
    
    //Check to see if root has File, it should
    boolean rootHasFile = false;
    if (fs.getFileFromPath("file") != null)
    {
      rootHasFile = true;
    }
  
    //Check to see if f1 has the file as well
    boolean f1HasFile = false;
    if (fs.getFileFromPath("f1/file") != null)
    {
      f1HasFile = true;
    }
    
    assertEquals(true, (rootHasFile == true) && (f1HasFile == true));   
    
   }
 
  @Test
  public void fileToFileCopy() {
    command = "echo \"ToBeReplaced\" > file2";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);   
    
           
    command = "cp file file2";
    arguments = CommandParser.getArgs(command);  
    CommandCp.objectCopy(arguments[0], arguments[1], fs);
    
    //Check to see if root has File, it should
    boolean rootHasFile = false;
    if (fs.getFileFromPath("file") != null)
    {
      rootHasFile = true;
    }
    
    //Check to see if root has File2 as well, it should
    boolean rootHasFile2 = false;
    if (fs.getFileFromPath("file2") != null)
    {
      rootHasFile2 = true;
    }
  
    //Check to see if file2 has the same contents as file
    boolean file2HasContents = false;
    if (fs.getFileFromPath("file").toString().equals
        (fs.getFileFromPath("file2").toString()))
    {
      file2HasContents = true;
    }
    
    assertEquals(true, (rootHasFile == true) && (rootHasFile2 == true)
        && (file2HasContents==true));   
    
   }
  
  @Test
  public void fileToNewFileCopy() {

    //Check to see if root does not have a File2 
    boolean rootBeforeCopy = false;
    if (fs.getFileFromPath("file2") == null)
    {
      rootBeforeCopy = true;
    }
    
    command = "cp file file2";
    arguments = CommandParser.getArgs(command);  
    CommandCp.objectCopy(arguments[0], arguments[1], fs);
    
    //Check to see if root has File, it should
    boolean rootHasFile = false;
    if (fs.getFileFromPath("file") != null)
    {
      rootHasFile = true;
    }
    
    //Check to see if root has a new File2 as well, it should
    boolean rootHasFile2 = false;
    if (fs.getFileFromPath("file2") != null)
    {
      rootHasFile2 = true;
    }
  
    //Check to see if file2 has the same contents as file
    boolean file2HasContents = false;
    if (fs.getFileFromPath("file").toString().equals
        (fs.getFileFromPath("file2").toString()))
    {
      file2HasContents = true;
    }
    
    assertEquals(true, (rootBeforeCopy == true) && (rootHasFile == true)
        && (rootHasFile2 == true) && (file2HasContents==true));   
    
   }
  
  

}
