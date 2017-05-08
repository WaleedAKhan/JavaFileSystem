package driver;

/**
 * 
 * @author Priyen, Waleed, Dmitry, Kamal
 * 
 * This class implements the concatenation command, cat,
 * which allows the the contents of a file to be displayed 
 * to the JShell. 
 *
 */
public class CommandCat {
      /**
       * Shows the contents of a file in JShell, provided
       * it exists. Works on all files in JShell.
       * 
       * @param path: the path of where the file is located
       * @param fs: The FileSystem which the file is at
       * @return contents of the file or "NonExistent File" 
       *    if it does not exist
       */
      public static String CommandCat(String path, FileSystem fs){
        // Making sure there is a valid path to the file
        if(fs.getFileFromPath(fs.getAbsolutePath(path)) != null)
        {
          return (fs.getFileFromPath(fs.getAbsolutePath(path)).toString());
        }
        else
        {
          // File does not exist
          System.err.println("cat: Non existent file");
          return ("@@DONOTPRINT@@");
        }      
      }
}