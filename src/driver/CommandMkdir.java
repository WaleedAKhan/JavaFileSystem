package driver;

/**
 * This class implements the make directory command, mkdir,
 * which allows for directories (folders) to be created 
 * in the JShell.  
 * 
 * @author Priyen, Kamal, Waleed, Dmitry
 *
 */
public class CommandMkdir {
  
  /**
  * Creates the directories the user requests
  * 
  * @param fs: the FileSystem where the directories are made
  * @param paths: all the names of the directories to be created
  */
  static public Folder[] mkdir(FileSystem fs, String[] paths)
  {
    // accounts for multiple arguments supplied by user
    Folder[] folders = new Folder[paths.length];    
    for (int i=0;i < paths.length; i++)
    {
      folders[i] = actualMkdir(fs, paths[i]);
    }
    return folders;
  }
  
  /**
   * 
   * @param s: String which is checked for Illegal characters
   * ("!@$&*()?:.\\[\\]\"<>\'`|=\\/,{}")
   * @return whether string s has illegal characters 
   */
  static public boolean containsIllegalCharacters(String s)
  {
    String illegal = "!@$&*()?:.\\[\\]\"<>\'`|=\\/,";
    char[] chars = illegal.toCharArray();
    for (int i = 0; i < chars.length; i++)
    {
      if (s.contains(String.valueOf(chars[i])) || s.contains(
          String.valueOf('{')) || s.contains(String.valueOf('}')))
      {
        return true;
      }
    }
    return false;
    
  }
  
  /**
   * Reflects changes in the Folder class to keep the 
   * JShell system updated.
   * 
   * @param fs: the FileSystem where the directories are made
   * @param name: the name of the directory to be created
   * @return: the entire path of the directory
   */
  static private Folder actualMkdir(FileSystem fs, String name)
  {
    //name is path including name, for example /folder2/folder name is folder
    //makes the folder in the WD or somewhere else based on path
    String[] split = name.split("/");
    String actualName = split[split.length-1];
    if (actualName.equals(""))
    {
      actualName = name;
    }
    if (containsIllegalCharacters(actualName))
    {
      System.err.println("mkdir: illegal characters in path and/or name");
      return null;
    }
    String path = "";
    //-1 to take out name of folder from path lookup
    for (int i = 0;i<split.length-1;i++)
    {
      path = path + split[i] + "/";
    }
    
    //if path already exists raise error
    if (fs.getFolderFromPath((path + actualName)) != (null) ||
        fs.getFileFromPath((path + actualName)) != (null))
    {
      System.err.println("mkdir: "+ path + actualName + " already exists");    
      return null;
    }
    Folder dir = new Folder(actualName);     
    Folder parent = fs.getFolderFromPath(path);
    if (parent == null)
    {
      System.err.println("mkdir: " + path + " doesn't exist");
      return null;
    }
    fs.setFolderLocation(dir, parent);
    return dir;
  }
}
