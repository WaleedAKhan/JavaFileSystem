package driver; 
import java.util.HashMap;

/**
 * This class keeps track of where the directories
 * are stored/updated in the JShell. Most of the 
 * JShell commands interact with this class.
 * 
 * @author Waleed, Priyen, Kamal, Dmitry
 *
 */
public class Folder {
  private String name;
  private Folder parentFolder = this;
  //list is the HashMap keeping track of everything
  private HashMap<String, Object> folders = new HashMap<String, Object>();
  private HashMap<String, Object> files = new HashMap<String, Object>();
  
  //key = name, value = object (only contains things in folder)
  
  /**
   * Constructor for Folder
   * @param name: name of folder
   * @param parentFolder: parent of folder
   */
  public Folder(String name, Folder parentFolder){
    this.setName(name);
    this.parentFolder = parentFolder;
  }
  
  /**
   * Alternate constructor for Folder when 
   * parent is unknown
   * @param name: name of folder
   */
  public Folder(String name){
    this.setName(name);
  }
  
  /**
   * Gets the File object from the File name
   * @param nam: name of File
   * @return: the File object 
   */
  public File getFileObjFromName(String nam)
  {
    return (File) files.get(nam);
  }
  
  /**
   * Gets the Folder object fronm the Folder name
   * @param nam: name of Folder
   * @return: the Folder object
   */
  public Folder getFolderObjFromName(String nam)
  {
    if (nam.equals(".."))
    {
      return this.parentFolder;
    }
    return (Folder) folders.get(nam);
  }
  
  /**
   * Gets the parent folder of a folder
   * @return: parent folder
   */
  public Folder getParentFolder()
  {
    return this.parentFolder;
  }
  
  /**
   * Set the parent Folder of the specified Folder
   * @param parent: a Folder object
   */
  public void setParentFolder(Folder parent)
  {
    this.parentFolder = parent;
  }
  
  /**
   * Sets the parent folder when no name is 
   * provided (for root directory)
   */
  public void setParentFolder()
  {
    this.parentFolder = null;
  }
  
  /**
   * Adds a Folder in the current Folder
   * @param folder: name of Folder object
   */
  public void addFolder(Folder folder){
    folders.put(folder.getName(), folder);
  }
  
  /**
   * Adds a File of same name 
   * (used for mv, cp)
   * @param file: file object
   */
  public void addFile(File file){
    files.put(file.getName(), file);
  }

  /**
   * Removes a folder recursively
   * @param folder: Folder object
   */
  public void removeFolder(Folder folder){
    if (folders.containsKey(folder.getName()))
    {
      folders.remove(folder.getName());
    }
  }
  
  /**
   * Removes the file and its contents
   * @param file: File object
   */
  public void removeFile(File file){
    if (files.containsKey(file.getName()))
    {
      files.remove(file.getName());
    }
  }
  
  /**
   * Get the path of a Folder 
   * @return: path (directory)
   */
  public String getDir(){
    return parentFolder.getDir() + getName() + "/";
  }
  
  /**
   * Displays all the contents of a folder(s) to 
   * the Jshell user
   * @return: contents of a Folder
   */
  public String listContents()
  {
	String contents = "";
    for(String key:folders.keySet())
    {
    if(!key.equals("/"))
    	{
      contents += (key + " " );
    	}
    }
    for(String key:files.keySet())
    {
      contents += (key + " ");
    }
    return contents;
  }
  
  /**
   * Gets the folder/subfolders of Folders
   * @return: A hashmap of a Folder Object
   */
  public HashMap<String, Object> getFolders (){
    return this.folders;
  }
  
  /**
   * Gets the files with contents from Folders
   * @return: A hashmap of a File Object
   */
  public HashMap<String, Object> getFiles (){
    return this.files;
  }
  
  /**
   * Sets the Folder object in Folder
   * @param folders: Sets Object's Folders to the folders Hashmap. 
   */
  public void setFolders(HashMap<String, Object> folders){
    this.folders.putAll(folders);
  }
 
  /**
   * Sets the File object in Folder
   * @param files: Sets Object's files to the files Hashmap.
   */
  public void setFiles(HashMap<String, Object> files){
    this.files.putAll(files);
  }

  /**
   * Gets the name of a Folder (directory)
   * @return: name of directory
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of a Folder (directory)
   * @param name: name of the directory
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Creates a folder that is a copy of this folder and returns it 
   * By copy, it means all files and folders and their subfolders inside
   * this folder are new objects but copies.
   * @return: A folder that is a copy of this folder 
   */
  //for the current folder and all its children i.e. subfiles and subfolder
  public Folder makeCopy() {
    Folder f = new Folder(name);
    for(String key:folders.keySet())
    {
      Folder f_temp = (Folder) folders.get(key);
      Folder new_folder = f_temp.makeCopy();
      new_folder.setParentFolder(f);
      f.addFolder(new_folder);
    }
    for(String key:files.keySet())
    {
      File file_temp = (File) files.get(key);
      File new_file = file_temp.makeCopy();
      new_file.setParentFolder(f);
      f.addFile(new_file);
    }
    return f;
    }
}
