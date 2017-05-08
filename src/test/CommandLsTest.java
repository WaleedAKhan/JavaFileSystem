package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.*;


public class CommandLsTest {
	FileSystem fs;
	String command;
	String[] arguments;
	
	@Before
	public void setUp() throws Exception {
		fs = new FileSystem();
	}
	
	@Test
	public void lsNoArgsTest(){
		command = "mkdir folder1";
		arguments = CommandParser.getArgs(command);
		CommandMkdir.mkdir(fs,arguments);
		
		//Implementing ls from the root working directory
		command = "ls";
		arguments = CommandParser.getArgs(command);
		
		String [] a = {};
		assertEquals("folder1".trim(),CommandLs.CommandLs
		    (fs, null, a).trim());
		
	}
	@Test
	public void lsToEmptyFolderTest(){
		command = "mkdir folder1";
		arguments = CommandParser.getArgs(command);
		CommandMkdir.mkdir(fs,arguments);
		
		//ls into an empty folder, empty string should be returned.
		command = "ls folder1";
		arguments = CommandParser.getArgs(command);
		
		String [] a = {};
		assertEquals("",CommandLs.CommandLs(fs, arguments[0], a));
		
	}
	
	@Test
	public void lsToSubFolderTest(){
		command = "mkdir folder1";
		arguments = CommandParser.getArgs(command);
		CommandMkdir.mkdir(fs,arguments);
		
		command = "mkdir folder1/folder2";
		arguments = CommandParser.getArgs(command);
		CommandMkdir.mkdir(fs,arguments);
		
		//ls into an folder which contains a file
		command = "ls folder1";
		arguments = CommandParser.getArgs(command);
		String [] a = {};
		assertEquals("folder2 ",CommandLs.CommandLs(fs, arguments[0], a));
		
	}
	
	@Test
	public void lsToAbsPathTest(){
		command = "mkdir folder1";
		arguments = CommandParser.getArgs(command);
		CommandMkdir.mkdir(fs,arguments);
		
		command = "cd folder1";
		arguments = CommandParser.getArgs(command);
		CommandCd.cd(fs, arguments[0]);
		
		//Ls into the root directory
		command = "ls /";
		arguments = CommandParser.getArgs(command);
		String [] a = {};
		assertEquals("folder1 ",CommandLs.CommandLs(fs, arguments[0], a));	
		
	}
	
	@Test
	public void lsToParentDirectoryTest(){
		command = "mkdir folder1";
		arguments = CommandParser.getArgs(command);
		CommandMkdir.mkdir(fs,arguments);
		
		command = "mkdir folder1/folder2";
		arguments = CommandParser.getArgs(command);
		CommandMkdir.mkdir(fs,arguments);
		
		command = "mkdir folder1/folder2/folder3";
		arguments = CommandParser.getArgs(command);
		CommandMkdir.mkdir(fs,arguments);
		
		command = "cd folder1/folder2/folder3";
		arguments = CommandParser.getArgs(command);
		CommandCd.cd(fs, arguments[0]);
		
		//Ls into the root directory by moving up 3 directories back to root.
		command = "ls ../../../";
		arguments = CommandParser.getArgs(command);
		String [] a = {};
		
		
		assertEquals("folder1 ",CommandLs.CommandLs(fs, arguments[0], a));	
		
	}
}
