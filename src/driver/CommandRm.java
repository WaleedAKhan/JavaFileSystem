package driver;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class implements the remove command - rm in JShell
 * which takes a directory or file and removes it. The command has the 
 * option of an optional flag "-f" which if provided will recursively
 * run through the directory in question asking for confirmation for each 
 * child file/folder to be removed. If a no is answered to a path, then the
 * recursion will stop based on if the removal of a parent is impossible
 * given a child still exists. 
 *  
 * @author Waleed, Dmitry, Priyen, Kamal
 *
 */

public class CommandRm {
  
  /**
   * Creating a scanner for user input
   */
 
  //static Scanner userInput = new Scanner(System.in);
  static Scanner userInput;
  
  
  /**
   * Removes a given file/folder 
   * @param flag: String Array which contains the optional flag if present
   * @param path: file/folder path which is to be moved
   * @param fs: the FileSystem where the files and folders exist
   */
 public static void rm(String[] flag, String path, FileSystem fs,
     InputStream in){
         File file = fs.getFileFromPath(path);
         Folder folder = fs.getFolderFromPath(path);
         if(flag.length > 0){
           if (flag.length != 1)
           {
             System.err.println("rm: inappropriate flag usage");
             return;
           }
           if (flag.length == 1)
           {
             if (flag[0].equals("-f"))
             {
               //allow
             }
             else
             {
               System.err.println("rm: invalid flag");
               return;
             }
           }
           else
           {
             System.err.println("rm: inappropriate flag usage");
             return;
           }
         }
         userInput = new Scanner(in);
         

         if((file == null && folder == null) ){
          System.err.println("rm: File/Folder Does not Exist within the cwd");
         }
         else if(folder != null && folder.getName().equals("/")){
           System.err.println("rm: Cannot remove Root");
         }
         else if(folder == fs.getWorkingDirectory()){
           System.err.println("rm: Cannot remove the cwd, "
               + "explicitly or implicitly");
         }
         
         else{
           //Path to take if path is a file
           if(file != null){
             removeFile(flag,path,fs,file);           
             }          
         //Path to take if the path is a folder
         if(folder != null){
           removeFolder(flag,path,fs,folder, in);        
             }      
         } 
    }
 /**
  * Removes a File Object from the FileSystem
   * @param flag: String Array which contains the optional flag if present
   * @param path: The absolute path of the file to be removed
   * @param fs: the FileSystem where the file exists
   * @param file: the file to be removed
   */
private static void removeFile(String[] flag, String path, FileSystem fs, 
    File file){
    //Flag Case, force remove. Command parser handles if the correct flag is 
    // passed
    if (flag.length > 0) //already did flag checks in "rm" method
    {
          fs.remove(file);       
    }
    //Case to handle no flag and user prompts
    else{
    String input = "";
    while(!input.equals("y") && !input.equals("n")){
      outPrintStream.println("Are you sure you want to delete "+path+" ?");
     
      input = userInput.nextLine();
  
      if(input.equals("y")){
        fs.remove(file);
          }
      else if(input.equals("n")){
        return;
          }
      //Invalid input by user
      else{
        System.err.println("Please enter y or n");
          }
      }
    }  
 }
/**
 * Removes a generic Folder Object from the FileSystem
  * @param flag: String Array which contains the optional flag if present
  * @param path: The absolute path of the file to be removed
  * @param fs: the FileSystem where the folder exists
  * @param folder: the folder to be removed
  */
private static void removeFolder(String[] flag, String path, FileSystem fs, 
      Folder folder, InputStream in){
    //Flag Case
    if(flag.length != 0){
      fs.remove(folder);
      }
    //Check if Folder is empty and remove as required
    else if(folder.getFolders().isEmpty() && folder.getFiles().isEmpty()){
      removeIfEmpty(folder, fs, path);
      }
    //Recursively remove subFolders and subFiles
    else{      
      ArrayList<String> subFolders =
          new ArrayList<String>(folder.getFolders().keySet());
      ArrayList<String> subFiles =
          new ArrayList<String>(folder.getFiles().keySet());
      
      removeSubFoldersFiles(subFolders,subFiles,  fs, path, in);
      
      //After recursively deleting files and folders, check if the parent is 
      //now empty and delete as required.
      removeIfEmpty(folder,  fs,  path);
    }
  }
/**
 * Removes the children of a folder, both its folders and files
  * @param subFolders: ArrayList of strings of subFolder names
  * @param subFiles: ArrayList of strings of subFile names
  * @param fs: the FileSystem where the folder lives
  * @param path: the relative path of the parent folder
  */
private static void removeSubFoldersFiles(ArrayList<String> subFolders,
    ArrayList<String> subFiles, FileSystem fs, String path, InputStream in){
  
  String[] flag = {}; 
  for(String subFolder: subFolders){
    String subPath = fs.getAbsolutePath(path)+"/"+subFolder;
    rm(flag, subPath, fs, in );            
    }
  for(String subFile: subFiles){
    String subPath = fs.getAbsolutePath(path)+"/"+subFile;
    rm(flag, subPath, fs, in );
    }   
  }

/**
 * Removes an Empty Folder which has no folders and files
  * @param folder: The empty folder object to be removed
  * @param fs: the FileSystem where the folder exists
  * @param path: the relative path of the folder
  */
private static void removeIfEmpty(Folder folder, FileSystem fs, String path){
  if(folder.getFolders().isEmpty() && folder.getFiles().isEmpty()){
    String input = "";
    while(!input.equals("y") && !input.equals("n")){
      outPrintStream.println("Are you sure you want to delete "+path+" ?");
      
      input = userInput.nextLine();
      
    if(input.equals("y")){
      fs.remove(folder);
      }
    else if(input.equals("n")){
      return;
        }
    //Invalid input by user
    else{
      System.err.println("rm: Please enter y or n");
        }
      }
    }
  }
}
