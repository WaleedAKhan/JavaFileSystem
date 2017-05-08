package driver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class searches the FileSystem for a regular expression and shows the
 * results and locations of the regular expression (if it exists)
 * 
 * @author Priyen, Waleed, Priyen, Kamal
 * 
 */
public class CommandGrep {

  /**
   * Displays the results found from a query
   * 
   * @param fs: The FileSystem which will be searched
   * @param regex: the pattern to search for
   * @param paths: the paths to traverse
   * @param flags: determines which flags to execute
   * @return: all the matches
   */
  public static String CommandGrep(FileSystem fs, String regex, String[] paths,
      String[] flags) {
    String c = "";
    boolean foldersAllowed = false;
    if (Arrays.asList(flags).size() == 1) {
      // if valid flag
      if (Arrays.asList(flags).contains("-r")) {
        foldersAllowed = true;
      } else {
        System.err.println("grep: inappropriate flag use");
        return "@@DONOTPRINT@@";
      }
    }
    for (int index = 0; index < paths.length; index++) {
      if (foldersAllowed) {
        Folder f = fs.getFolderFromPath(paths[index]);
        File file;
        if (f == null) {
          file = fs.getFileFromPath(paths[index]);
          if (file != null) {
            System.err.println("grep: -r flag supplied, "
                + "path cannot be to files");
            return "@@DONOTPRINT@@";
            // c = c + getMatchingLines(regex, file);
          } else {
            System.err.println("grep: path doesn't exist: " + paths[index]);
            return "@@DONOTPRINT@@";
          }
        } else {
          c = c + getMatchingLinesForAllFilesInFolder(regex, f);
        }
      } else {
        File file = fs.getFileFromPath(paths[index]);
        if (file != null) {
          c = c + getMatchingLines(regex, file);
        } else {
          System.err.println("grep: path doesn't exist: " + paths[index]);
          return "@@DONOTPRINT@@";
        }
      }
    }
    // correct parsing
    if (c.contains("\n")) {
      return c.substring(0, c.lastIndexOf("\n"));
    } else {
      return c;
    }
  }

  /**
   * Locating the matching lines in all the files of a directory
   * 
   * @param regex: the lines
   * @param f: the folder
   * @return: the lines which match the pattern
   */
  public static String getMatchingLinesForAllFilesInFolder(String regex,
      Folder f) {
    String c = "";
    HashMap<String, Object> folders = f.getFolders();
    HashMap<String, Object> files = f.getFiles();
    for (String key : folders.keySet()) {
      // to account for fact that fs has a reference of itself in its folders
      // hashmap
      if (folders.get(key) != f) {
        c =
            c
                + getMatchingLinesForAllFilesInFolder(regex,
                    (Folder) folders.get(key));
      }
    }
    // finding all the matches
    for (String key : files.keySet()) {
      c = c + getMatchingLines(regex, (File) files.get(key));
    }
    return c;
  }

  /**
   * Locating the lines that match the regex pattern
   * 
   * @param regex: the regex (pattern)
   * @param file: the File to be searched
   * @return: result from query
   */
  public static String getMatchingLines(String regex, File file) {
    String s = "";
    if (file != null) {
      String[] lines = file.toString().split("\n");
      Pattern pattern = Pattern.compile(regex);
      for (int i = 0; i < lines.length; i++) {
        // Matcher object for pattern search
        Matcher matcher = pattern.matcher(file.toString());
        if (matcher.find()) {
          s = s + file.getDir() + ":" + lines[i] + "\n";
        }
      }
    }
    return s;
  }
}
