package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import driver.*;

public class CommandRmTest {
  FileSystem fs;
  String command;
  String[] arguments;
  String [] flag;
  
  @Before
  public void setUp() throws Exception {
    fs = new FileSystem();
    command = "mkdir folder1";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs,arguments);
    
    command = "mkdir folder1/folder2";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs,arguments);
    
    command = "mkdir folder1/folder3";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs,arguments);
    
    command = "mkdir folder4";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs,arguments);
    
    command = "echo \"file\" > file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
  }

  @Test
  public void removeFolderInRoot(){
    
    boolean folderCreated = false; 
    boolean folderRemoved = false; 
    //Creation was successful
    if(fs.getFolderFromPath("/folder4")!= null){
      folderCreated = true;
    }
    String inputData = "y\n";
    InputStream stream = new ByteArrayInputStream(inputData.getBytes());
    
    command = "rm folder4";
    arguments = CommandParser.getArgs(command);
    flag = CommandParser.getFlags(command);
    CommandRm.rm(flag, arguments[0], fs, stream );
    
    //Folder was Removed
    if(fs.getFolderFromPath("/folder4")== null){
      folderRemoved = true;
    }
    assertEquals(true, folderRemoved && folderCreated);    
  }
  
  @Test
  public void noToRemoveFolder(){
    // Test if a user entering "n" keeps the folder intact
    boolean folderCreated = false; 
    boolean folderKept = false; 
    //Creation was successful
    if(fs.getFolderFromPath("/folder4")!= null){
      folderCreated = true;
    }
    String inputData = "n\n";
    InputStream stream = new ByteArrayInputStream(inputData.getBytes());
    
    command = "rm folder4";
    arguments = CommandParser.getArgs(command);
    flag = CommandParser.getFlags(command);
    CommandRm.rm(flag, arguments[0], fs, stream );
    
    //Folder was Removed
    if(fs.getFolderFromPath("/folder4") != null){
      folderKept = true;
    }
    assertEquals(true, folderKept && folderCreated);    
  }
  
  
 
  @Test
  public void forceRemoveFolder() throws NoSuchElementException  {
      
    boolean folderCreated = false; 
    boolean folderRemoved = false; 
    //Creation was successful
    if(fs.getFolderFromPath("/folder1")!= null){
      folderCreated = true;
    }

    String inputData = "";
    ByteArrayInputStream stream = new 
        ByteArrayInputStream(inputData.getBytes());
    
    command = "rm -f folder1";
    arguments = CommandParser.getArgs(command);
    flag = CommandParser.getFlags(command);
    CommandRm.rm(flag, arguments[0], fs,stream );
    

    
    //Folder was Removed
    if(fs.getFolderFromPath("/folder1")== null){
      folderRemoved = true;
    }
    assertEquals(true, folderRemoved && folderCreated);    
  }
  @Test
  public void removeFile() {
      
    boolean fileCreated = false; 
    boolean fileRemoved = false; 
    //Creation was successful
    if(fs.getFileFromPath("/file1")!= null){
      fileCreated = true;
    }

    String inputData = "y\n";
    ByteArrayInputStream stream = new 
        ByteArrayInputStream(inputData.getBytes());
    
    command = "rm file1";
    arguments = CommandParser.getArgs(command);
    flag = CommandParser.getFlags(command);
    CommandRm.rm(flag, arguments[0], fs,stream );
    

    
    //Folder was Removed
    if(fs.getFolderFromPath("/file1")== null){
      fileRemoved = true;
    }
    assertEquals(true, fileRemoved && fileCreated);    
  }

  @Test
  public void forceRemoveSubFolder() {
      
    boolean folderCreated = false; 
    boolean folderRemoved = false; 
    //Creation was successful
    if(fs.getFolderFromPath("/folder1/folder2")!= null){
      folderCreated = true;
    }

    String inputData = "{}";
    ByteArrayInputStream stream = 
        new ByteArrayInputStream(inputData.getBytes());
    
    command = "rm -f folder1/folder2";
    arguments = CommandParser.getArgs(command);
    flag = CommandParser.getFlags(command);
    CommandRm.rm(flag, arguments[0], fs,stream );
    

    
    //Folder was Removed
    if(fs.getFolderFromPath("folder1/folder2")== null){
      folderRemoved = true;
    }
    assertEquals(true, folderRemoved && folderCreated);    
  }
  @Test
  public void removeSubFolder(){
  
    boolean folderCreated = false; 
    boolean folderRemoved = false; 
    //Creation was successful
    if(fs.getFolderFromPath("/folder1/folder2")!= null){
      folderCreated = true;
    }
    
    command = "cd folder1";
    arguments = CommandParser.getArgs(command);
    CommandCd.cd(fs, arguments[0]);
    
    String inputData = "y\n";
    ByteArrayInputStream stream = 
        new ByteArrayInputStream(inputData.getBytes());
    
   
    command = "rm folder2";
    arguments = CommandParser.getArgs(command);
    flag = CommandParser.getFlags(command);
    CommandRm.rm(flag, arguments[0], fs,stream );
  
    //Folder was Removed
    if(fs.getFolderFromPath("/folder1/folder2")== null){
      folderRemoved = true;
    }
    assertEquals(true, folderRemoved && folderCreated);    
  }

}
