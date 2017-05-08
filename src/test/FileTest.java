package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import driver.*;
public class FileTest {
  FileSystem fs;
  
  @Before
  public void setup(){
    
  }
  
  @Test
  public void FileGetParentFolderTest() {
    FileSystem fs = new FileSystem();
    File f1 = new File("f1","Hello");
    f1.setParentFolder(fs.getParentFolder());
    fs.addFile(f1);
    assertEquals(f1.getParentFolder(), fs.getParentFolder());
    
  }

  
  @Test
  public void FileAppendTest() {
    File f1 = new File("f1","Hello");
    f1.appendText("hey");
    assertEquals(f1.toString(), "Hellohey");
  }
  
  @Test
  public void FileGetDirTest() {
    FileSystem fs = new FileSystem();
    File f1 = new File("f1", "Hello World");
    f1.setParentFolder(fs.getParentFolder());
    fs.addFile(f1);
    assertEquals(f1.getDir(), "/f1");
    
  }  
  
}
