package test;



import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.*;

public class FileSystemTest {
  FileSystem fs;
  String command;
  String[] arguments;
 
  @Before
  public void setUp() throws Exception {
    fs = new FileSystem();
  }

  @Test
  public void testSetWorkingDirectoryToRoot() {
    fs.setWorkingDirectory(fs);
    assertEquals(fs.getWorkingDirectory(), fs);
  }

  @Test
  public void testSetWorkingDirectoryToAFolder() {
    fs.setWorkingDirectory();
    Folder f = new Folder("test");
    fs.setFolderLocation(f,fs);
    fs.setWorkingDirectory(f);
    assertEquals(fs.getWorkingDirectory(), f);
  }
  
  @Test
  public void testGetWorkingDirectory() {
    Folder f = new Folder("test");
    f.setParentFolder(fs);
    fs.setFolderLocation(f);
    fs.setWorkingDirectory(f);
    assertEquals(fs.getWorkingDirectory().getDir(), "/test/");
  }
  
  @Test
  public void testSetFolderLocationToWorkingDirectory() {
    String[] paths = {"test", "test/test2"};
    Folder[] folders = CommandMkdir.mkdir(fs, paths);
    fs.setFolderLocation(folders[1]);
    assertEquals(folders[1].getDir(), "/test2/");
  }
  
  @Test
  public void testSetFolderLocationToAFolder() {
    String[] paths = {"test", "test2"};
    Folder[] folders = CommandMkdir.mkdir(fs, paths);
    fs.setFolderLocation(folders[0], folders[1]);
    assertEquals(folders[0].getDir(), "/test2/test/");
  }
  
  @Test
  public void testSetFileLocationToWorkingDirectory() {
    String[] paths = {"test", "test/test2"};
    Folder[] folders = CommandMkdir.mkdir(fs, paths);
    File file = new File("file", "");
    fs.setWorkingDirectory(folders[0]);
    fs.setFileLocation(file);
    assertEquals(file.getDir(), "/test/file");
  }
  
  @SuppressWarnings("static-access")
  @Test
  public void testSetFileLocationToAFolder() {
    String[] paths = {"test", "test/test2"};
    Folder[] folders = CommandMkdir.mkdir(fs, paths);
    File file = new File("file", "");
    fs.setFileLocation(file, folders[0]);
    assertEquals(file.getDir(), "/test/file");
  }
  
  @Test
  public void testRemoveFile() {
    File file = new File("file", "");
    fs.setFileLocation(file);
    fs.remove(file);
    assertEquals(fs.getFileFromPath("test"), null);
  }
  
  @Test
  public void testRemoveFolder() {
    String[] paths = {"test", "test/test2"};
    Folder[] folders = CommandMkdir.mkdir(fs, paths);
    fs.remove(folders[1]);
    assertEquals(fs.getFolderFromPath("test/test2"), null);
  }
  
  @Test
  public void testGetFileFromPath() {
    File file = new File("file", "");
    fs.setFileLocation(file);;
    assertEquals(fs.getFileFromPath("file"), file);
  }
  
  @Test
  public void testGetFolderFromPath() {
    String[] paths = {"test", "test/test2"};
    Folder[] folders = CommandMkdir.mkdir(fs, paths);
    assertEquals(fs.getFolderFromPath("test/test2"), folders[1]);
  }
  
  @Test
  public void testGetAbsolutePath()
  {
    String[] paths = {"test", "test/test2"};
    Folder[] folders = CommandMkdir.mkdir(fs, paths);
    fs.setWorkingDirectory(folders[1]);
    assertEquals(fs.getAbsolutePath(""), "/test/test2/");
  }
}
