package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.*;
public class FolderTest {
  FileSystem fs;
  Folder f1;
  @Before
  public void setup(){

  }
  
  @Test
  public void FolderGetFileObjFromNameTest() {
    FileSystem fs = new FileSystem();
    Folder f1 = new Folder("f1", fs.getWorkingDirectory());
    
    
    File actualFile = new File("jokes", "lol");
    f1.addFile(actualFile);
    File file = f1.getFileObjFromName("jokes");
    
    assertEquals(file.toString(), actualFile.toString());
    assertEquals(file.getName(), actualFile.getName());
  }
  
  @Test
  public void FolderGetParentFolderTest() {
    FileSystem fs = new FileSystem();
    Folder f1 = new Folder("f1", fs.getWorkingDirectory());
    

    assertEquals(f1.getParentFolder(), fs.getWorkingDirectory());
  }  

  @Test
  public void FolderGetFolderObjFromNameTest() {
    FileSystem fs = new FileSystem();
    Folder f1 = new Folder("f1", fs.getWorkingDirectory());
    Folder f2 = new Folder("f2", f1);
    f1.addFolder(f2);
    
    Folder test = f1.getFolderObjFromName("f2");
    
    assertEquals(test, f2);
  }
}
