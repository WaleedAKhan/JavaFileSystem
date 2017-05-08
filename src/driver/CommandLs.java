package driver;

import java.util.Arrays;
import java.util.HashMap;

/**
 * This class implements the list command - ls,
 * which allows the the contents of a directory to be outputted 
 * to the JShell. 
 * 
 * @author Dmitry, Waleed, Kamal, Priyen
 *
 */
public class CommandLs {
  
  /**
   * Shows the contents of a directory - may take in a full 
   * path or if no path is provided, it uses the current
   * working directory.
   * 
   * @param fs: The FileSystem which needs contents of a directory
   *    to be shown 
   * @param path: the path at which the directory is at
   * @param flags: option for -R (recursive)
   */
  public static String CommandLs(FileSystem fs, String path, String[] flags)
  {
    if (Arrays.asList(flags).size() > 1 || 
        ((Arrays.asList(flags).size() == 1) && (Arrays.asList(flags).
            contains("-r") == false)))
    {
      System.err.println("ls: inappropriate flag usage");
      return "@@DONOTPRINT@@";
    }
    if (path == null){
      if (Arrays.asList(flags).contains("-r"))
      {
        return listRecursively(fs, fs.getWorkingDirectory(), 
            "/");
      }
      else
      {
        return  fs.getWorkingDirectory().listContents();
      }
    }
    else
    {
      if (fs.getFileFromPath(path) != null)
      {
        return fs.getFileFromPath(path).getName();
      }
      if(fs.getFolderFromPath(path) != null)
      {
        if (Arrays.asList(flags).contains("-r"))
        {
          String s = fs.getFolderFromPath(path).getDir().replace
            (fs.getWorkingDirectory().getDir(), "");
          if (fs.getWorkingDirectory() == fs)
          { 
            s = fs.getFolderFromPath(path).getDir(); 
          }
          return listRecursively(fs, fs.getFolderFromPath(path), s);
        }
        else
        {
          return fs.getFolderFromPath(path).listContents();
        }
      }      
      else
      {
        // path does not exist
        System.err.println("ls: Invalid path provided: " + path);
        return "@@DONOTPRINT@@";
      }
   }
 }
  
  /**
   * Lists all the contents of a directory (recursively) 
   * @param fs: FileSystem in which contents will be listed
   * @param folder: The directory
   * @param master: The listing of for each directory
   * @return: recursive list of directories
   */
  private static String listRecursively(FileSystem fs, Folder folder, 
      String master)
  {
    String content = folder.listContents() + "\n";
    String listingDirectoryString = master;
    if (listingDirectoryString.equals("")) { listingDirectoryString = 
        folder.getName(); }
    content = listingDirectoryString + ": \n" + content;
    HashMap<String, Object> folders = folder.getFolders();
    //to account for fact that root folder contains a reference of itself
    //in its folder' hashmap
    if ((folders.size() > 0 && folder != fs) || (folders.size() > 1))
    {
      content = content + "\n";
    }
    else
    {
      return listingDirectoryString + ":" + "\n" + folder.listContents();
    }
    for(String key:folders.keySet())
    {
      Folder f = ((Folder) folders.get(key));
      if (f != null && f != folder)
      {
        String c = listRecursively(fs, f, master + "/" + f.getName());
        content = content + c + "\n";
      }
    }
    return content.replace("//", "/");
  }
}
