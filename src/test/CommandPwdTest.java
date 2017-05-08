package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import driver.*;

public class CommandPwdTest {
  FileSystem fs;
  
  @Before
  public void setUp() throws Exception {
    fs = new FileSystem();
  }
  
  @Test
  public void testPWDAfterInitializingNewFileSystem() {
    fs = new FileSystem();
    String actual = CommandPwd.pwd(fs);
    String expected = "/";
    assertEquals(actual, expected);
  }
  
  @Test
  public void testPWDAfterMakingAFolder() {
    String[] paths = {"test"};
    CommandMkdir.mkdir(fs, paths);
    String actual = CommandPwd.pwd(fs);
    String expected = "/";
    assertEquals(actual, expected);
  }
  
  @Test
  public void testPWDAfterChangingWorkingDirectoryToAFolder() {
    String[] paths = {"test"};
    CommandMkdir.mkdir(fs, paths);
    fs.setWorkingDirectory(fs.getFolderFromPath("test"));
    String actual = CommandPwd.pwd(fs);
    String expected = "/test/";
    assertEquals(actual, expected);
  }
 
}
