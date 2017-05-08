package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.*;

public class CommandPushPopDTest {
  FileSystem fs;
  @Before
  public void setup(){
    fs = new FileSystem();
  }
  
  @Test
  public void PushRoot() {
    CommandPushPopD.pushd(fs, "/");
    String dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/"));
  }
  
  @Test
  public void PopRoot() {
    CommandPushPopD.pushd(fs, "/");
    String dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/"));
    
    CommandPushPopD.popd(fs);
    dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/"));
  }  
  
  @Test
  public void pushRelativeDir() {
    String[] args = {"a1", "a1/a2"};
    
    CommandMkdir.mkdir(fs, args);
    
    CommandPushPopD.pushd(fs, "/a1/a2");
    String dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/a1/a2/"));
    
    
    CommandPushPopD.popd(fs);
    dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/"));
  }

  @Test
  public void pushRelativeDirFromOneFolderDown() {
    String[] args = {"a1", "a1/a2"};
    
    CommandMkdir.mkdir(fs, args);
    
    CommandCd.cd(fs, "a1");;
    
    CommandPushPopD.pushd(fs, "/a1/a2/");
    String dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/a1/a2/"));
    
    
    CommandPushPopD.popd(fs);
    dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/a1/"));
  }

  @Test
  public void pushAbsDirFromOneFolderDwn() {
    String[] args = {"a1", "a1/a2"};
    
    CommandMkdir.mkdir(fs, args);
    
    CommandCd.cd(fs, "a1");;
    
    CommandPushPopD.pushd(fs, "/a1/a2/");
    String dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/a1/a2/"));
    
    
    CommandPushPopD.popd(fs);
    dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/a1/"));
  }
  
  @Test
  public void pushRelativeDirFromOneFolderDwn() {
    String[] args = {"a1", "a1/a2"};
    
    CommandMkdir.mkdir(fs, args);
    
    CommandCd.cd(fs, "a1");;
    
    CommandPushPopD.pushd(fs, "a2");
    String dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/a1/a2/"));
    
    
    CommandPushPopD.popd(fs);
    dir = fs.getWorkingDirectory().getDir();
    assertTrue(dir.equals("/a1/"));
  }
  
}
