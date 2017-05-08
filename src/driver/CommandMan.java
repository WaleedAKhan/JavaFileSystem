package driver;

/**
 * An extensive set of instructions on how to  
 * use all the commands in JShell
 * 
 * @author Dmitry, Waleed, Priyen, Kamal
 *
 */
public class CommandMan {
  
  /**
   * Displays information about a certain command
   * in JShell 
   * 
   * @param fs: the FileSystem that the command is at
   * @param args: the argument which stores the command
   */
  public static void man(FileSystem fs, String [] args)  {
    // information for pwd
    if (args[0].equals("pwd")){
      outPrintStream.println("pwd: Print the current working directory "
          + "path (i.e whole absolute path)");
    }
    else if (args[0].equals("man")) {
      //information for the command "man" itself
      outPrintStream.println("man CMD: shows how to use JShell commands,");
      outPrintStream.println("Type man CMD where CMD can be: ");
      outPrintStream.println("ls, mkdir, cd, echo, pwd, cp, mv, cat, exit,"
          + "rm, get, popd, pushd, grep");
    }
    else if (args[0].equals("ls")){
      // information for the ls command
      outPrintStream.println("ls [-R] PATH: Display the contents "
          + "of a directory");
      outPrintStream.println("If no paths are given, print "
          + "the contents (file or directory) of ");
      outPrintStream.println("the current directory, with a new line "
          + "following each of the content (file or directory)");
      outPrintStream.println("If -R is present, recursively list all"
          + "the subdirectories.");
    }
    else if (args[0].equals("mkdir")){
      // information for the mkdir command
      outPrintStream.println("mkdir DIR: Create directories, each of which"
          + " may be relative to the current directory "
          + "or may be a full path.");
    }
    else if (args[0].equals("cd")){
      // information for the cd command
      outPrintStream.println("cd DIR: Change directory to DIR, which may be "
          + "relative to the current directory or may be a full path.");
    }
    else if (args[0].equals("echo")){
      // information for the echo command (many types)
      outPrintStream.println("echo 'STRING' [>[>] OUTFILE]: ");
      outPrintStream.println("If outfile is not provided, then simply print "
          + "string onto the screen");
      outPrintStream.println("Otherwise, put STRING into file OUTFILE.");
      outPrintStream.println("STRING is a string of characters "
          + "surrounded by double quotation marks.");
      outPrintStream.println("This creates a new file if OUTFILE does not "
          + "exists and erases the old contents if OUTFILE already exits.");
      outPrintStream.println("In either case the only thing "
          + "in OUTFILE should be STRING.");
    }
    else if (args[0].equals("cat")){
      // information for the cat command
      outPrintStream.println("cat: Display the contents of a file "
          + "in the shell");
    }
    else if (args[0].equals("cp")){
      // information for the cp command
      outPrintStream.println("cp OLDPATH NEWPATH: Copy a folder/file"
          + " from OLDPATH to NEWPATH");
    }
    else if (args[0].equals("mv")){
      // information for the mv command
      outPrintStream.println("mv OLDPATH NEWPATH: Move item OLDPATH "
          + "to NEWPATH. Both OLDPATH"
          + " and NEWPATH may be relative to the current working directory"
          + " or full paths.");
          outPrintStream.println("If OLDPATH is a directory, move the item "
              + "into a directory.");
    }
    else if (args[0].equals("exit")){
      // information on how to exit the JShell
      outPrintStream.println("exit: Quits the program.");
    }
    else if (args[0].equals("get")){
      // information on how to exit the JShell
      outPrintStream.println("get [URL]: URL is a web address. Retrieve the "
          + "file at that URL and add it to the current working directory.");
    }
    else if (args[0].equals("pushd")){
      // information for the cd command
      outPrintStream.println("pushd DIR: Saves the current working "
          + "directory onto"
          + " stack and then changes the new current working "
          + "directory to DIR.");
      outPrintStream.println("Then pushD command saves the old current "
          + "working "
          + "directory in memory so that it can be returned to at anytime "
          + "(via popd command).");
    }
    else if (args[0].equals("popd")){
      // information for the cd command
      outPrintStream.println("popd DIR: Remove the top entry from the "
          + " directory stack, and cd to the new top directory ");
      outPrintStream.println("The popd command removes the top most "
          + "directory onto the"
          + " stack and makes it the current working directory. ");
    }
    else if (args[0].equals("grep")){
      // information on how to find regex in the JShell
      outPrintStream.println("grep [-R] REGEX PATH: Find regular expressions."
          + " Print any lines that contain REGEX in PATH which must be files"
          + "\nIf PATH is  a directory and [-R] is specified, then,\n"
          + "recursively traverse the directory and for all files "
          + "that contain REGEX, print the path to file, then a colon"
          + "and then the line that contained regex. ");
    }
    else if (args[0].equals("rm")){
      // information on how to delete in JShell
      outPrintStream.println("rm [-f]: Remove a path from JShell. "
          + "If PATH is  a directory,"
          + " recursively remove all files and directories in it each "
          + "time prompting for confirmation.");
      outPrintStream.println("[-f] do not confirm prompt - force remove");
    }
    else {
      // if user requests info on a CMD that JShell does not have
      System.err.println("man: No such command");
    } 
  }
}
