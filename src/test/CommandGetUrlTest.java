package test;

import static org.junit.Assert.*;

import driver.*;

import org.junit.Before;
import org.junit.Test;

public class CommandGetUrlTest {
  FileSystem fs;
  @Before
  public void setup(){
    fs = new FileSystem();
  }
  @Test
  public void MakeFileInRootTest() {
    CommandGetUrl.CommandGetUrl("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt"
        , fs);
    File f1 = fs.getFileFromPath("/073.txt");
    assertTrue(f1 != null);
    
    String a = f1.toString().split("\n")[0];
    assertTrue(f1.getName().equals("073.txt"));
    assertTrue(a.equals("There was once a king who had an illness, "
        + "and no one believed that he"));
    assertTrue(f1 != null);
  }

  @Test
  public void MakeFileInSubFolderTest() {
    String[] args = {"a1"};
    
    CommandMkdir.mkdir(fs, args);
    
    CommandCd.cd(fs, "a1");
    CommandGetUrl.CommandGetUrl("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt"
        , fs);
    
    File f1 = fs.getFileFromPath("/a1/073.txt");
    
    String a = f1.toString().split("\n")[0];
    assertTrue(f1.getName().equals("073.txt"));
    assertTrue(a.equals("There was once a king who had an illness, "
        + "and no one believed that he"));
    assertTrue(f1 != null);
  }  
  
  @Test
  public void MakeHtmlInRootTest() {
    CommandGetUrl.CommandGetUrl("http://www.ub.edu/web/ub/ca/index.html"
        , fs);
    File f1 = fs.getFileFromPath("/index.html");
    assertTrue(f1.getName().equals("index.html"));
    assertTrue(f1 != null);
  }
  
  @Test
  public void MakeHtmlInFolderTest() {
    FileSystem fs = new FileSystem();
    String[] args = {"a1"};
    
    CommandMkdir.mkdir(fs, args);
    
    CommandCd.cd(fs, "a1");
    CommandGetUrl.CommandGetUrl("http://www.ub.edu/web/ub/ca/index.html"
        , fs);
    File f1 = fs.getFileFromPath("/a1/index.html");
    assertTrue(f1.getName().equals("index.html"));
    assertTrue(f1 != null);
  }
  
  @Test
  public void OverWriteFilewithSameName() {
    String[] args = {"Hello", ">", "index.html"};
    
    CommandEcho.echo(fs, args);
    File f1 = fs.getFileFromPath("/index.html");
    
    assertTrue(f1.toString().equals("Hello"));
    
    
    CommandGetUrl.CommandGetUrl("http://www.ub.edu/web/ub/ca/index.html"
        , fs);
    // get url instead of overwriting previous file, destroys the previous
    // file and creates a new one with the contents from the web.
    File f2 = fs.getFileFromPath("index.html");
    assertFalse(f2.toString().equals("Hello"));
    
  }
}
