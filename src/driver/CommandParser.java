package driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * This class works with the Filesystem to process the commands
 * entered by the JShell user. 
 * 
 * @author Dmitry, Waleed, Kamal, Priyen
 *
 */
public class CommandParser {
  @SuppressWarnings("serial")
  private static Hashtable<String, Integer> commands = new 
      Hashtable<String, Integer>()
  {{
   put("mkdir", 1);
   put("cp", 2);
   put("mv", 2);
   put("cd", 1);
   put("cat", 1);
   put("exit", 0);
   put("ls", 1);
   put("pwd", 0);
   put("echo", 3);  
   put("man", 1);
   put ("get", 1);
   put ("pushd", 1);
   put ("popd", 0);
   put ("rm", 2);
   put ("grep", 3);
  }}; 
  
  @SuppressWarnings("serial")
  private static Hashtable<String, Integer> flagCommands = new 
      Hashtable<String, Integer>()
  {{
   put("ls", 1);
   put("rm", 1);
   put("grep", 1);
  }}; 
    
  /**
   * Determine if the command the user entered is valid 
   * based on the hashtable argument counts.
   * 
   * @param command: user's command 
   * @return: whether or not command entered is valid (boolean)
   */
   public static boolean validCommand(String command){
    String cmd = getCommand(command);
    String[] args = getArgs(command);
    String[] flags = getFlags(command);
    if (flagCommands.containsKey(cmd) && (flags.length > 0 && 
        flags.length != flagCommands.get(cmd)))
    {
      return false;
    }
    // returns a boolean value
    return commands.containsKey(cmd) &&
        validParamCount(cmd, args);             
  }
   
   public static String getCommandIgnoringRedirection(String command)
   {
     if (command.contains(">") || command.contains(">>"))
     {
       if (getCommand(command).equals("echo") == false)
       {
         if (command.contains(">>"))
         {
           command = command.substring(0, command.lastIndexOf(">>"));
         }
         else
         {
           command = command.substring(0, command.lastIndexOf(">"));
         }
       }
     }
    return command;
   }
   
   public static String getRedirectionStringFromCommand(String command)
   {
     return command.substring(command.indexOf(">"));
   }
   /**
    * Determines if the number of arguments the user 
    * supplied to valid for the command in JShell.
    * 
    * @param cmd: user's command
    * @param args: the arguments entered for the command
    * @return whether or not the number of parameters are valid for command
    */
   public static boolean validParamCount(String cmd, String[] args){ 
     if(cmd.equals("mkdir")){ 
       return ((args.length) >= commands.get(cmd));
     }   
     if(cmd.equals("ls")){ 
       return ((args.length) >= commands.get(cmd)||
           (args.length) <= commands.get(cmd));
     }
     else if (cmd.equals("echo")){       
        return (((args.length) == 3)||((args.length == 1))); 
     }
     else if (cmd.equals("grep")){       
       return (((args.length) == 3)||((args.length == 2))); 
    }
     else if (cmd.equals("rm")){       
       return (((args.length) >= 2)||((args.length == 1))); 
    }
     else{
       return (args.length == commands.get(cmd));
     }
   }    
   
   /**
    * Used to retrieve arguments from JShellcommands 
    * which have only one input parameter.
    * 
    * @param command: user entered command
    * @return: first argument of command
    */
   public static String getCommand(String command){
     String[] args = command.split(" ");
     //Returns the Command that was entered.
     return args[0];
     }
   
   public static String[] removeFlagsFromString(String s)
   {
     String[] args = s.split(" +");
     ArrayList<String> _args = new ArrayList<String>
     (Arrays.asList(args));
     for (int i = 0; i < getFlags(s).length; i++)
     {
       _args.remove(getFlags(s)[i]);
       _args.remove(getFlags(s)[i].toUpperCase());
     }
     return _args.toArray(new String[_args.size()]);
   }
   
   //Command Returns a String[] which holds the list of parameters
   //passed with the command.
   /**
    * Used to retrieve arguments from JShell commands which 
    * have more than one input parameter
    * 
    * @param command: user entered command
    * @return: retrieves more than 1 argument from command 
    */
   public static String[] getArgs(String command){
     String[] cmdWithArgs = removeFlagsFromString(command);
     String[] invalid = new String[0];
     //Handle Echo
     if (cmdWithArgs[0].equals("echo") && (cmdWithArgs.length == 2))
     {
         if (cmdWithArgs[1].startsWith("\"") == false || cmdWithArgs[1].
             endsWith("\"") == false)
         {
           return invalid;
         }
         String[] args = {cmdWithArgs[1]};
         return args;
     }     
     if(cmdWithArgs[0].equals("echo") && cmdWithArgs.length > 2)
     {  
      return getArgsForEcho(command);
     }    
     if(cmdWithArgs[0].equals("grep"))
     {  
      String s = "";
      for (int i = 0; i < removeFlagsFromString(command).length;
          i++)
          {
          s = s + removeFlagsFromString(command)[i] + " ";
          }
      return getArgsForGrep(s);
     } 
         return Arrays.copyOfRange(cmdWithArgs, 1, cmdWithArgs.length);
     }   
   
   public static String[] getFlags(String command){
     String[] args = command.split(" +");
     ArrayList<String> _flags = new ArrayList<String>();
     boolean flagsAfterCommand = true;
     for (int i = 1; i < args.length; i++)
     {
       if (args[i].startsWith("-"))
       {
         if (flagsAfterCommand == false)
         {
           //a flag found after a non flag argument ( = invalid command )
           _flags.add(0, "@@@INVALID@@@");
         }
         _flags.add(args[i].toLowerCase());         
       }
       else if (i == 1 && flagsAfterCommand == true)
       {
         flagsAfterCommand = false;
       }
     }
     return _flags.toArray(new String[_flags.size()]);
   }
   
   /**
    * Used to retrieve arguments from JShell for echo command specifically
    * 
    * @param command: user entered command
    * @return: retrieves more than 1 argument from echo command 
    */
   public static String[] getArgsForEcho(String command)
   {
     String[] echoArguments = new String[3];
     String stringWithoutCommand = command.substring(5);
     if (stringWithoutCommand.startsWith("\"") == false || 
         (stringWithoutCommand.lastIndexOf("\"") == 
         stringWithoutCommand.indexOf("\"")))
     {
       return new String[0];
     }
     String content = stringWithoutCommand.substring(1, 
         stringWithoutCommand.lastIndexOf("\""));
     if (stringWithoutCommand.lastIndexOf("\"") ==
         stringWithoutCommand.length()-1)
     {
       String[] args = {content};
       return args;
     }
     String stringStartingWithOperator = stringWithoutCommand.substring(
         stringWithoutCommand.lastIndexOf("\"")+2);
     String split[] = stringStartingWithOperator.split(" +");
     if (split.length > 2)
     {
       return new String[0];
     }
     echoArguments[0] = content; 
     echoArguments[1] = split[0];
     echoArguments[2] = split[1];
     return echoArguments;
   }
   
   public static String[] getArgsForGrep(String command)
   {
     ArrayList<String> grepArguments = new ArrayList<String>();
     String stringWithoutCommand = command.substring(5);
     if (stringWithoutCommand.startsWith("\"") == false || 
         (stringWithoutCommand.lastIndexOf("\"") == 
         stringWithoutCommand.indexOf("\"")))
     {
       return new String[0];
     }
     String content = stringWithoutCommand.substring(1, 
         stringWithoutCommand.lastIndexOf("\""));
     String stringStartingWithOperator = stringWithoutCommand.substring(
         stringWithoutCommand.lastIndexOf("\"")+2);
     String[] split = stringStartingWithOperator.split(" +");
     grepArguments.add(content);
     for (int i = 0; i < split.length; i++)
     {
       grepArguments.add(split[i]);
     }
     return grepArguments.toArray(new String[grepArguments.size()]);
   }
     
}
