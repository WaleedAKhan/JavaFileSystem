package driver;

/**
 * This class implements the copy command - cp, in JShell which takes a
 * directory or file and copies it to another location (can also change the
 * files name).
 * 
 * @author Waleed, Dmitry, Priyen, Kamal
 * 
 */
public class CommandCp {

  /**
   * Copies a file/folder from one location to another
   * 
   * @param src: file/folder path which is to be copied
   * @param dest: the destination path to which the folder/file is to be
   * copied to
   * @param fs: the FileSystem where the copying will take place
   */
  public static void objectCopy(String src, String dest, FileSystem fs) {

    // path locations
    Folder srcFolder = fs.getFolderFromPath(src);
    Folder destFolder = fs.getFolderFromPath(dest);
    File srcFile = fs.getFileFromPath(src);
    File destFile = fs.getFileFromPath(dest);

    // determining if the file/folder to be moved exists
    if (srcFile == null && srcFolder == null) {
      System.err.println("cp: Non existent file");
    }

    // Case for if The source is a folder object
    if (srcFolder != null) {
      folderCopy(srcFolder, destFolder, destFile, dest, src, fs);
    }

    // Case for if source is a file object
    if (srcFile != null) {
      fileCopy(srcFile, destFolder, destFile, dest, src, fs);
    }
  }

  @SuppressWarnings("static-access")
  /**
   * Copies file from source to destination and allows 
   * for name changes (if applicable)
   * @param srcFile: File source
   * @param destFolder: Folder destination
   * @param destFile: File destination
   * @param dest: destination path to copy to
   * @param src: source path to copy from
   * @param fs: FileSystem where copy is happening
   */
  private static void fileCopy(File srcFile, Folder destFolder, File destFile,
      String dest, String src, FileSystem fs) {
    // sub-case for if destination is a file, file->file copy
    // contents of dest are replaced with those of src
    if (destFile != null) {
      destFile.setText(srcFile.toString());
    }

    // sub-case for if destination a folder, file ->folder copy
    else if (destFolder != null) {
      File copy = srcFile.makeCopy();
      fs.setFileLocation(copy, destFolder);
    }
    // Case for if a file is to be duplicated, covers case for illegal
    // naming, where in the file name contains a / character.
    // To reach here destFile does not exist and neither does a destFolder
    else {
      // Check for if the path exists
      if (dest.contains("/")) {
        Folder destDir =
            fs.getFolderFromPath(dest.substring(0, dest.lastIndexOf("/")));
        if (destDir != null)
        // Creating new copy of File
        {
          File newFile =
              new File(
                  dest.substring(dest.lastIndexOf("/") + 1, dest.length()),
                  srcFile.toString());
          fs.setFileLocation(newFile, destDir);
        } else {
          System.err.println("cp: Invalid directory or filename");
        }
      } else {
        File newFile = new File(dest, srcFile.toString());
        fs.setFileLocation(newFile, srcFile.getParentFolder());
      }
    }

  }

  /**
   * Copies Folder from source to destination and allows for name changes (if
   * applicable)
   * 
   * @param srcFolder: Folder source
   * @param destFolder: Folder destination
   * @param destFile: File destination
   * @param dest: destination path to copy to
   * @param src: source path to copy from
   * @param fs: FileSystem where copying is happening
   */
  private static void folderCopy(Folder srcFolder, Folder destFolder,
      File destFile, String dest, String src, FileSystem fs) {
    // Fresh Copy or an illegal command.
    if (destFolder == null || destFile != null) {
      // sub-case for if destination a file, illegal command type
      if (destFile != null) {
        System.err.println("cp: Invalid operation (source: a folder"
            + " and destination:  a file)");
      }
      // Case to create a new copy of the folder given a legal name
      else {
        if (!dest.contains("/")) {
          Folder copy = srcFolder.makeCopy();
          copy.setName(dest);
          fs.setFolderLocation(copy, srcFolder.getParentFolder());
        } else {
          System.err.println("cp: Invalid path or name");
        }
      }
    }
    // sub-case for if destination a folder, folder->folder move
    else {
      Folder copy = srcFolder.makeCopy();
      copy.setName(srcFolder.getName());
      fs.setFolderLocation(copy, destFolder);
    }
  }
}
