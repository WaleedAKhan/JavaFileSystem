package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import driver.*;

public class CommandMkdirTest {
  FileSystem fs;
  
  @Before
  public void setUp() throws Exception {
    fs = new FileSystem();
  }

  @Test
  public void testMakingAFolder() {
    String[] paths = {"test"};
    Folder[] actual = CommandMkdir.mkdir(fs, paths);
    Folder[] expected = {fs.getFolderFromPath("test")};
    assertArrayEquals(actual, expected);
  }
  
  @Test
  public void testPathAlreadyExists() {
    String[] paths = {"Folder"};
    //make the folder once
    CommandMkdir.mkdir(fs, paths);
    //try it again
    Folder[] actual = CommandMkdir.mkdir(fs, paths);
    Folder[] expected = {null};
    assertArrayEquals(actual, expected);
  }
  
  @Test
  public void testMultiplePaths() {
    String[] paths = {"test1", "test2", "test3"};
    Folder[] actual = CommandMkdir.mkdir(fs, paths);
    Folder[] expected = {fs.getFolderFromPath("test1"),
        fs.getFolderFromPath("test2"), fs.getFolderFromPath("test3")};
    assertArrayEquals(actual, expected);
  }
  
  @Test
  public void testMultiplePathsWithSubdirectories() {
    String[] paths = {"test1", "test2", "test2/test3"};
    Folder[] actual = CommandMkdir.mkdir(fs, paths);
    Folder[] expected = {fs.getFolderFromPath("test1"),
        fs.getFolderFromPath("test2"), fs.getFolderFromPath("test2/test3")};
    assertArrayEquals(actual, expected);
  }

  @Test
  public void testIllegalCharacters() {
    String[] paths = {"test1@", "test2....", "test3"};
    Folder[] actual = CommandMkdir.mkdir(fs, paths);
    Folder[] expected = {null, null, fs.getFolderFromPath("test3")};
    assertArrayEquals(actual, expected);
  }
}
