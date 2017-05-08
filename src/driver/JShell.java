// **********************************************************
// Assignment2:
// UTOR user_name1: polyans1
// UTOR user_name2: khanwal1
// UTOR user_name3: patelp57
// UTOR user_name4: aslamkam
//
// Author1: Dmytro Polyanskyy
// Author2: Waleed Khan
// Author3: Priyenbhai Patel
// Author4: Kamal Aslam
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package driver;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class provides the JShell UI,takes in 
 * input commands from the user and displays the 
 * results of each command. 
 * 
 * @author Waleed, Dmitry, Priyen, Kamal
 *
 */
public class JShell {

  /**
   * This is where the JShell begins, takes user input
   * and also where the JShell terminates.
   * @param args: default for main function
   */
  public static void main(String[] args) {
    
    /**
     * Creating a FileSystem (fs) instance object
     */
    FileSystem fs = new FileSystem();
    String input = "";
    String command = "";
    String[] flags = new String[1];
    String[] arguments = new String[0];
    Scanner userInput = new Scanner(System.in);
     
    //keep prompting for input until exit command is given by user
    while(input != "exit"){
      outPrintStream.print("/# ");
      input = userInput.nextLine();
      
      // deals when user enters nothing
      if (input.equals("")) 
      {
        continue;
      }
      if (input.equals("exit"))
      {
        outPrintStream.println("JShell Terminated");
        userInput.close();
        break;
      }
      try {
        if(CommandParser.validCommand(CommandParser.
            getCommandIgnoringRedirection(input)))
        {
          command = CommandParser.getCommand(CommandParser.
              getCommandIgnoringRedirection(input));
          arguments = CommandParser.getArgs(CommandParser.
              getCommandIgnoringRedirection(input));
          flags = CommandParser.getFlags(input);
          if (flags.length > 0 && flags[0].equals("@@@INVALID@@@"))
          {
            System.err.println("Invalid command, please try again");
            continue;
          }
        }
        else
        {
          System.err.println("Invalid command, please try again");
          continue;
        }
      }
      catch (Exception e){
        System.err.println("Invalid command, please try again");
        continue;
      }

      //Calls to appropriate commands are made past here
      try{
        if(CommandParser.getCommandIgnoringRedirection(input) == input)
        {
          //means no redirection
        }
        else
        {
          outPrintStream.clearContent();
          outPrintStream.disablePrinting();
        }
        if(command.equals("mkdir") )
        {
          CommandMkdir.mkdir(fs, arguments);
        }
        if(command.equals("pwd") )
        {
          outPrintStream.println(CommandPwd.pwd(fs));
        }
        if(command.equals("cd") )
        {
          CommandCd.cd(fs, arguments[0]);
        }
        if(command.equals("ls") )
        {
          if (arguments.length == 0)
          {
            String s = CommandLs.CommandLs(fs, null, flags);
              outPrintStream.println(s);
          }
          else if(arguments.length == 1) {
            String s = CommandLs.CommandLs(fs, arguments[0], flags);
              outPrintStream.println(s);
          }
          else
          {
            //adding functionality for ls to accept multiple arguments
            int totalArgs = arguments.length;
            String c = "";
            for (int i=0; i < totalArgs; i++){     
              String s = CommandLs.CommandLs(fs, arguments[i], flags);
              //for when there is an error (error msg printed from class)
              if (s.equals("@@DONOTPRINT@@") == false)
              {
                if (Arrays.asList(flags).contains("-r") == false)
                {
                  c = c + arguments[i]+":" + "\n";
                }  
                if (i == totalArgs - 1)
                {
                  c = c + s; 
                }
                else
                {
                  c = c + s + "\n";
                }
              }              
            }
            outPrintStream.println(c);
          }
        }
        if(command.equals("echo")){
          CommandEcho.echo(fs, arguments);
        }
        if(command.equals("man")){
          CommandMan.man(fs, arguments);
        }
        if(command.equals("mv")){
          CommandMv.objectMove(arguments[0], arguments[1], fs);
        }
        if(command.equals("cp")){
          CommandCp.objectCopy(arguments[0], arguments[1], fs);
        }
        if (command.equals("cat")){
          outPrintStream.println(CommandCat.CommandCat(arguments[0], fs));
        }
        if (command.equals("get")){
          outPrintStream.println(CommandGetUrl.CommandGetUrl(arguments[0],
              fs));
        }
        if (command.equals("grep"))
        {
          String r = arguments[0];
          String[] paths = Arrays.copyOfRange(arguments, 1, arguments.length);
          String s = CommandGrep.CommandGrep(fs, r, 
              paths, flags);
            outPrintStream.println(s);
          
        }
        if(command.equals("rm") )//
        {
          for(String argument:arguments){
          CommandRm.rm(flags, argument,fs, System.in);
          }
        }
        if(command.equals("pushd") ) {
          CommandPushPopD.pushd(fs,arguments[0]);
        }
        if(command.equals("popd") ) {
          CommandPushPopD.popd(fs);
        }
        if(CommandParser.getCommandIgnoringRedirection(input) != input)
        {
          //captured what ever needs to be redirected
          String content = outPrintStream.getContent();
          outPrintStream.enablePrinting();
          if (content.equals("") == false)
          {
            String[] _args = CommandParser.getArgs("echo \"x\" " +
          CommandParser.
              getRedirectionStringFromCommand(input));
            _args[0] = content.substring(content.indexOf("\n")+1);
            CommandEcho.echo(fs, _args);
          }
        }
      }
      catch(Exception e){
        System.err.println("Please type in an valid command");
      }
    }
  }
}