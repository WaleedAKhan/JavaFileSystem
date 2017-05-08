package driver;

/**
 * This class creates and keeps track of files in JShell.
 * The files parents (directories) are also available 
 * through this class for easy interaction for other JShell
 * commands.
 * 
 * @author Priyen, Dmitry, Waleed, Kamal
 *
 */
  public class File {
  private String text;
  private Folder parent;
  private String name; // file name
  
  /**
   * Constructor for File
   * @param name: name of the File
   * @param text: text that goes in the File
   */
  public File(String name, String text){
    this.text = text;
    this.name = name;
  }
  
  /**
   * Retrieves the parent folder of a File
   * @return: the parent directory(folder) 
   */
  public Folder getParentFolder()
  {
    return this.parent;
  }
  
  /**
   * Sets the new parent folder for a File
   * @param parentFolder: the parent folder
   */
  public void setParentFolder(Folder parentFolder)
  {
    this.parent = parentFolder;
  }
  
  /**
   * Sets the parentFolder to null if no argument is provided
   */
  public void setParentFolder()
  {
    this.parent = null;
  }
  
  /**
   * Puts text into a file
   * @param text: the text that goes into the File
   */
  public void setText(String text){
    this.text = text;
  }
  
  /**
   * Adds text to a File without deleting previous 
   * data
   * @param addText: the text to be appended to the File
   */
  public void appendText(String addText){
    this.text = this.text.concat(addText);
  }
  
  /**
   * Gets the full path of the File location
   * @return: the File's path 
   */
  public String getDir(){
    return parent.getDir() + name;
  }
  
  /**
   * Textual representation of information inside the File
   * @return: text inside file
   */
  public String toString(){
    return text;
  }
  
  /**
   * Getter for the file's name variable
   * @return: name of file
   */
  public String getName(){
    return this.name;
  }
  
  /**
   * Set the name of the File object
   * @param name: name to be given to File
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * Creates a new file with the same name and text as this file and returns
   * it
   * @return: a copy of this File object
   */
  public File makeCopy() {
    File newFile= new File(name, text);
    return newFile;
  }
}
