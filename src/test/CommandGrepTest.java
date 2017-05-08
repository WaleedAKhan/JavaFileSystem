package test;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import driver.*;
public class CommandGrepTest {
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
       
    command = "echo \"hello world\" > file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
    
    command = "echo \"hello world\" > folder1\file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs,arguments);
  }
  

  @Test
  public void fullStringInFileTest() {
    command = "grep \"hello world\" file1";
    arguments = CommandParser.getArgs(command);
    String[] paths = Arrays.copyOfRange(arguments, 1, arguments.length);
    flag = CommandParser.getFlags(command);
    String output = CommandGrep.CommandGrep(fs, arguments[0], paths, flag);
    
    assertEquals("/file1:hello world", output);
  }
  
  @Test
  public void RegexOnTwoLinesInSameFileTest() {
    command = "echo \"ello\" >> file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);
    
    command = "grep \"ello\" file1";
    arguments = CommandParser.getArgs(command);    
    String[] paths = Arrays.copyOfRange(arguments, 1, arguments.length);
    flag = CommandParser.getFlags(command);
    
    String output = CommandGrep.CommandGrep(fs, arguments[0], paths, flag);
    
    assertEquals("/file1:hello world\n/file1:ello", output);
  }  

  @Test
  public void classStringInFileTest() {
    command = "grep \"[Hh].llo world\" file1";
    arguments = CommandParser.getArgs(command);
    String[] paths = Arrays.copyOfRange(arguments, 1, arguments.length);
    flag = CommandParser.getFlags(command);
    String output = CommandGrep.CommandGrep(fs, arguments[0], paths, flag);
    
    assertEquals("/file1:hello world", output);
  }
  
  @Test
  public void RegexInTwoFilesTest() {
    command = "echo \"hello World\" > file2";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);
    
    command = "echo \"hello World\" > file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);
    
    
    
    command = "grep -r \"World\" /";
    arguments = CommandParser.getArgs(command);
    String[] paths = Arrays.copyOfRange(arguments, 1, arguments.length);
    flag = CommandParser.getFlags(command);
    String output = CommandGrep.CommandGrep(fs, arguments[0], paths, flag);
    
    assertEquals("/file2:hello World\n/file1:hello World", output);
  }

  @Test
  public void RegexInFolderTest() {
    command = "echo \"Tom\" > /folder1/file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);
    
    command = "grep -r \"Tom\" /folder1";
    arguments = CommandParser.getArgs(command);
    String[] paths = Arrays.copyOfRange(arguments, 1, arguments.length);
    flag = CommandParser.getFlags(command);
    String output = CommandGrep.CommandGrep(fs, arguments[0], paths, flag);

    
    assertEquals("/folder1/file1:Tom", output);
  }
  
  @Test
  public void RegexInDiffDirectoriesTest() {
    command = "echo \"Tom\" > file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);
    
    command = "echo \"Tom\" > /folder1/file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);
    
    command = "grep -r \"Tom\" /";
    arguments = CommandParser.getArgs(command);
    String[] paths = Arrays.copyOfRange(arguments, 1, arguments.length);
    flag = CommandParser.getFlags(command);
    String output = CommandGrep.CommandGrep(fs, arguments[0], paths, flag);

    
    assertEquals("/folder1/file1:Tom\n/file1:Tom", output);
  }  
  
  @Test
  public void MultipleDirectoriesAsArgumentsTest() {
    command = "mkdir folder2";
    arguments = CommandParser.getArgs(command);
    CommandMkdir.mkdir(fs, arguments);
    
    command = "echo \"Tom\" > /folder1/file1";
    arguments = CommandParser.getArgs(command);
    CommandEcho.echo(fs, arguments);
    
    command = "grep -r \"Tom\" /folder1 /folder2";
    arguments = CommandParser.getArgs(command);
    String[] paths = Arrays.copyOfRange(arguments, 1, arguments.length);
    flag = CommandParser.getFlags(command);
    String output = CommandGrep.CommandGrep(fs, arguments[0], paths, flag);

    
    assertEquals("/folder1/file1:Tom", output);
  }  
}
