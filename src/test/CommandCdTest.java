package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import driver.*;

public class CommandCdTest {
  FileSystem fs;
  
  @Before
  public void setUp() throws Exception {
    fs = new FileSystem();
  }

  @Test
  public void testCdToAFolder() {
    String[] paths = {"test"};
    CommandMkdir.mkdir(fs, paths);
    CommandCd.cd(fs, "test");
    String actual = CommandPwd.pwd(fs);
    String expected = "/test/";
    assertEquals(actual, expected);
  }
  
  @Test
  public void testCdWithNoPathArgument() {
    CommandCd.cd(fs, "");
    String actual = CommandPwd.pwd(fs);
    String expected = fs.getWorkingDirectory().getDir();
    assertEquals(actual, expected);
  }
  
  @Test
  public void testCdWithInvalidPathArgument() {
    CommandCd.cd(fs, "randomFolder");
    String actual = CommandPwd.pwd(fs);
    String expected = fs.getWorkingDirectory().getDir();
    assertEquals(actual, expected);
  }
  
  @Test
  public void testCdWithDotDotSpecialSyntax() {
    String[] paths = {"test1", "test1/test2"};
    CommandMkdir.mkdir(fs, paths);
    fs.setWorkingDirectory(fs.getFolderFromPath("test1/test2"));
    CommandCd.cd(fs, "../../");
    String actual = CommandPwd.pwd(fs);
    String expected = "/";
    assertEquals(actual, expected);
  }
  
  @Test
  public void testCdWithDotDotSpecialSyntax2() {
    String[] paths = {"test1", "test1/test2"};
    CommandMkdir.mkdir(fs, paths);
    fs.setWorkingDirectory(fs.getFolderFromPath("test1/test2"));
    CommandCd.cd(fs, "../");
    String actual = CommandPwd.pwd(fs);
    String expected = "/test1/";
    assertEquals(actual, expected);
  }
}
