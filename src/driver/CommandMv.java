package driver;

/**
 * This class implements the move command - mv in JShell
 * which takes a directory or file and moves it to 
 * another location (can also change the files name).
 * The location at which the directory or file was 
 * previously at is removed.
 * 
 * @author Waleed, Dmitry, Priyen, Kamal
 *
 */
public class CommandMv {
  //Move a Folder path to another Folder path
  //Going to assume paths are valid and checked by command parser
  //before call to mv function
 
  /**
   * Moves a file/folder from one location to another
   * @param src: file/folder path which is to be moved
   * @param dest: the destination path to which the folder/file is to be moved 
   * to
   * @param fs: the FileSystem where the moving will take place
   */
  public static void objectMove(String src, String dest, FileSystem fs){
    
    // path locations
    Folder destFolder;
    File destFile; 
    File srcFile = fs.getFileFromPath(src);
    Folder srcFolder = fs.getFolderFromPath(src);
   
    if(dest.equals("/")){
      destFolder = fs;
      destFile = null;
    }
    else{
      destFolder = fs.getFolderFromPath(dest);
      destFile = fs.getFileFromPath(dest);
    }
    
    if(srcFile == null && srcFolder == null){
      System.err.println("mv: Non existent File");
    }
    
    //Case for if The source is a folder object
    if (srcFolder != null)
    {  
      FolderMove(srcFolder, destFolder, destFile,dest,fs);        
    }
  //Case for if source is a file object
   if (srcFile != null)
   {
     FileMove(srcFile, destFolder, destFile, dest, fs);   
     }
  } 
  
  /**
   * Moves the Folder Object from source to destination 
   * and allows name changes (if applicable).
   * @param srcFolder: source Folder
   * @param destFolder: destination Folder
   * @param destFile: destination File
   * @param dest: the destination path to move to
   * @param fs: FileSystem where move is happening
   */
  private static void FolderMove(Folder srcFolder, Folder destFolder, 
      File destFile,String dest,FileSystem fs){
    // Rename or an illegal command.
    if(destFolder == null || destFile != null)
      {
    //sub-case for if destination a file, illegal command type 
      if(destFile != null){
        System.err.println("mv: Invalid commmand");
      }
     //Case to rename a file
      else{
        if(!dest.contains("/")){
         srcFolder.getParentFolder().removeFolder(srcFolder);
         srcFolder.setName(dest);
         srcFolder.setParentFolder(fs.getWorkingDirectory());
         fs.getWorkingDirectory().addFolder(srcFolder);
        }
        else{
          System.err.println("mv: Invalid commmand");
        }
      }   
    }
  //sub-case for if destination a folder, folder->folder move
    else
      {
        if (srcFolder == fs.getWorkingDirectory())
        {
          System.err.println("mv: Illegal operation - you can't move the "
              + "current working directory");
          return;
        }
        if (fs.isFolderSubfolder(destFolder, srcFolder))
        {
          System.err.println("mv: Illegal operation - you can't move a folder"
              + " into one of its subfolders");
          return;
        }
        fs.setFolderLocation(srcFolder, destFolder);
      }
    
  }
  @SuppressWarnings("static-access")
  /**
   * Moves the File object from source to destination
   * and allows for name changes (if applicable).
   * @param srcFile: source File
   * @param destFolder: destination Folder
   * @param destFile: destination File
   * @param dest: destination path to move to
   * @param fs: FileSystem where move is happening
   */
  private static void FileMove(File srcFile, Folder destFolder, File destFile,
      String dest,FileSystem fs){
    //sub-case for if destination is a file, file->file move
    //dest is deleted, and file is renamed to dest
      if (destFile!= null)
        {
        Folder destPath = destFile.getParentFolder();
        String newName = destFile.getName();
        destPath.removeFile(destFile);
        srcFile.setParentFolder(destPath);
        srcFile.setName(newName);
        
        }
    //sub-case for if destination a folder, file ->folder move
      else if(destFolder!=null)
        {
        fs.setFileLocation(srcFile, destFolder);
        }
      //Case for if a file is to be renamed, covers case for illegal naming,
      //where in the file name contains a / character.
      else
        {
        if(dest.contains("/")){
          System.err.println("mv: Invalid directory or File");
        }
        else{
          srcFile.getParentFolder().removeFile(srcFile);
          srcFile.setName(dest);   
          srcFile.getParentFolder().addFile(srcFile);
        } 
        }
      } 
  }
