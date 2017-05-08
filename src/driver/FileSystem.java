package driver;

import java.util.HashMap;

/**
 * This class is the backend for the JShell. It keeps track of both Files and
 * Folders and there respective directories, contents and the various ways to
 * depict there location.
 * 
 * @author Priyen, Dmitry, Waleed, Kamal
 * 
 */
public class FileSystem extends Folder {

  /**
   * Constructor for FileSystem
   */
  public FileSystem() {
    super("/");
    this.addFolder(this);
    this.setParentFolder(this);
  }

  private Folder rootFolder = this;
  private Folder workingDirectoryFolder = rootFolder;

  /**
   * Sets working directory to specified folder
   * 
   * @param theFolder: A Folder in JShell
   */
  public void setWorkingDirectory(Folder theFolder) {
    workingDirectoryFolder = theFolder;
  }

  /**
   * Sets working directory to root
   */
  public void setWorkingDirectory() {
    workingDirectoryFolder = rootFolder;
  }

  /**
   * Displays working directory if folder exists, else null if it is root
   * 
   * @return: working directory
   */
  public Folder getWorkingDirectory() {
    // type is Object so null can be returned (otherwise it wouldn't work)
    return workingDirectoryFolder;
  }

  /**
   * Sets location to specified parent folder
   * 
   * @param theFolder: the child of parent
   * @param parent: the parent of theFolder
   */
  public void setFolderLocation(Folder theFolder, Folder parent) {
    if (theFolder.getParentFolder() != null) {
      theFolder.getParentFolder().removeFolder(theFolder);
    }
    theFolder.setParentFolder(parent);
    parent.addFolder(theFolder);
  }

  /**
   * set location to working directory folder (no parent means root)
   * 
   * @param theFolder: A folder in JShell
   */
  public void setFolderLocation(Folder theFolder) {
    setFolderLocation(theFolder, workingDirectoryFolder);
  }

  /**
   * Sets location to specified parent folder
   * 
   * @param theFile: File object
   * @param parent: parent of theFile
   */
  public static void setFileLocation(File theFile, Folder parent) {
    if (theFile.getParentFolder() != null) {
      theFile.getParentFolder().removeFile(theFile);
    }
    theFile.setParentFolder(parent);
    parent.addFile(theFile);
  }

  /**
   * Sets location to Working Directory if a location is not specified
   * 
   * @param theFile: File object
   */
  public void setFileLocation(File theFile) {
    setFileLocation(theFile, workingDirectoryFolder);
  }

  /**
   * Deletes a folder from JShell
   * 
   * @param theFolder: a directory
   */
  public void remove(Folder theFolder) {
    theFolder.getParentFolder().removeFolder(theFolder);
  }

  /**
   * Deletes a file from JShell
   * 
   * @param theFile: a File object
   */
  public void remove(File theFile) {
    theFile.getParentFolder().removeFile(theFile);
  }

  /**
   * Determines path of a Folder/File
   * 
   * @return: path of a Folder/File object in JShell
   */
  public String getDir() {
    return "/";
  }

  /**
   * Gives the entire (not relative) path
   * 
   * @param path: path of Folder/File object
   * @return: full path of Folder/File object
   */
  public String getAbsolutePath(String path) {
    if (path.startsWith("/") == false && this.getWorkingDirectory() != this
        && (path.startsWith(".") == false || path.startsWith("..") == true)) {
      path = this.getWorkingDirectory().getDir() + path;
    } else if (path.startsWith(".") && path.startsWith("..") == false) {
      if (path.length() > 1) {
        path = this.getWorkingDirectory().getDir() + path.substring(1);
      } else {
        path = this.getWorkingDirectory().getDir();
      }

    }
    return path;
  }

  /**
   * Isolates the file from the full path eg
   * getFolderFromPath("/folder1/test/test1") will return test1 file obj
   * 
   * @param path: the full path
   * @return: the file object
   */
  public File getFileFromPath(String path) {
    if (path.equals(".")) {
      return null;
    }
    File obj = null;
    Folder temp = this;
    path = getAbsolutePath(path);

    String[] split = path.split("/");
    // -1 since last element will be the file name, and not a folder


    for (int i = 0; i < split.length - 1; i++) {
      if (split[i].equals("")) {

      } else {
        temp = temp.getFolderObjFromName(split[i]);
        if (temp == null) {
          return null;
        }
      }
    }
    try {
      String name = split[split.length - 1];
      obj = temp.getFileObjFromName(name);
      return obj;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Isolates the folder from full path (e.g getFolderFromPath
   * ("/folder1/test/" will return folder obj of test
   * 
   * @param path:the full path
   * @return: the folder object
   */
  public Folder getFolderFromPath(String path) {
    Folder temp = this;
    path = getAbsolutePath(path);

    String[] split = path.split("/");

    for (int i = 0; i < split.length; i++) {
      if (split[i].equals("")) {
        // do nothing
      } // root/test1/../
      else {
        temp = temp.getFolderObjFromName(split[i]);
        if (temp == null) {
          return null;
        }
      }
    }
    return temp;
  }

  /**
   * Returns a boolean of whether folder f1 is inside folder f2 or inside
   * any of its subfolders.
   * @param f1: the folder to check if it is inside folder f2 or inside its
   * subfolders.
   * @param f2: the folder to see if f2 is inside it.
   * @return
   */
  public boolean isFolderSubfolder(Folder f1, Folder f2) {
    boolean sub = false;
    HashMap<String, Object> folders = f2.getFolders();
    for (String key : folders.keySet()) {
      if (f1.getDir().equals(((Folder) folders.get(key)).getDir())) {
        return true;
      } else {
        if (isFolderSubfolder(f1, (Folder) folders.get(key))) {
          return true;
        }
      }
    }
    return sub;
  }
}
