package driver;

/**
 * This class implements the present working directory 
 * command, pwd, which allows the JShell user to see 
 * where he is located in the FileSystem at any moment.
 * 
 * @author Dmitry, Waleed, Priyen, Kamal
 *
 */
public class CommandPwd {
  
  /**
   * Displays the current directory at which the
   * user is at
   * 
   * @param currDir: the directory at which the user is at
   * @return: the current/present working directory
   */
  public static String pwd(FileSystem currDir) {
      // prints out the full path for the working directory
      return currDir.getWorkingDirectory().getDir();
  }
}
