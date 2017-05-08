package driver;

/**
 * 
 * @author Priyen, Waleed, Dmitry, Kamal
 * 
 * This class implements that change directory (cd)
 * command which allows a user to change directories
 * in this JShell.
 *
 */
public class CommandCd {
  
  /**
   * Changes and sets the current working directory to 
   * a new one provided by the user. Updates the working 
   * directory in the Folder class.
   * 
   * @param fs: The FileSystem which the directory is at
   * @param folder: the target directory 
   */
  public static void cd(FileSystem fs, String folder){
    // Up one directory (relative to current path)
    if(folder.equals(".."))     
    {
        fs.setWorkingDirectory(fs.getWorkingDirectory().getParentFolder());
    }
    else if (fs.getFolderFromPath(folder) != null) // Folder exists
    {
        fs.setWorkingDirectory(fs.getFolderFromPath(folder));
    }
    else
    {
        // Invalid path
    	System.err.println("Directory: Invalid path, please try again"); 
    }
  }
}
