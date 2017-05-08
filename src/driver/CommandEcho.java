package driver;

/**
 * This class implements the echo command, echo, which allows the the 
 * contents a file to be created in JShell purposes. Also displays 
 * the string if no outfile is specified
 * 
 * @author Waleed, Priyen, Kamal, Dmitry
 * 
 */
public class CommandEcho {

  /**
   * Displays a string to JShell or (over)writes/appends to a given outfile
   * 
   * @param fs: The FileSystem to which the echo command will work
   * @param args: the parameters for the echo command
   */
  @SuppressWarnings("static-access")
  public static void echo(FileSystem fs, String[] args) {
    if (args.length == 1) {
      outPrintStream.println(args[0].replace("\"", "").trim());
      return;
    }
    String fileDir = fs.getAbsolutePath(args[2]);
    Folder dir = fs.getWorkingDirectory();
    if (args[2].contains("/")) {
      dir =
          fs.getFolderFromPath(fileDir.substring(0,
              fileDir.lastIndexOf("/") + 1));
    }

    File file = fs.getFileFromPath(fileDir);
    Folder nameCheck = fs.getFolderFromPath(fileDir);

    if (nameCheck != null) {
      System.err.println("echo: Folder of the same name already exists");
    } else {
      // put string in outfile
      if (args[1].equals(">")) {
        // if outfile does not exist, create newFile
        if (file == null) {
          File newFile = new File(getFileName(args[2]), args[0]);
          fs.setFileLocation(newFile, dir);
        }
        // outfile exists, overwrite its text
        else {
          file.setText(args[0]);
        }
      }
      // same story here, but append to the file text
      else if (args[1].equals(">>")) {

        // if file does not exist, create newFile
        if (file == null) {
          File newFile = new File(getFileName(args[2]), args[0]);
          fs.setFileLocation(newFile, dir);
        } else {
          file.appendText("\n" + args[0]);
        }
      }
    }
  }

  /**
   * Finds where the last child in the path is located to pass to be able to
   * work with directories
   * 
   * @param dir: the directory at which the file to write to is present
   * @return: the position of where the child in the path is
   */
  public static String getFileName(String dir) {
    return dir.substring(dir.lastIndexOf("/") + 1);
  }
}
