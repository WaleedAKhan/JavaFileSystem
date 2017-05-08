package driver;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * This class helps with redirection for commands, specifically
 * capturing output. It acts like a messenger between what needs
 * to be printed and the System.out class. Since all output except
 * error messages go through this class, it can enable and disable
 * printing. It can also keep track of what is printing and store it
 * for retrieval. 
 * 
 * @author Priyen, Waleed, Priyen, Kamal
 * 
 */
public class outPrintStream {
  private static ArrayList<String> content = new ArrayList<String>();
  private static boolean canPrint = true;

  /**
   * Print the output of the command
   */
  public static void printContent()
  {
    System.out.print(outPrintStream.getContent());
  }
 
  /**
   * Prints a string on a new line if printing is enabled. 
   * Doesn't print when string is 
   * "@@DONOTPRINT@@", this is used by other classes for error messages.
   * Stores the string if printing is disabled.
   * @param s: the string to print
   */
  public static void println(String s)
  {
    if (s.equals("@@DONOTPRINT@@"))
    {
      return;
    }
    if (canPrint == false)
    {
      content.add("\n");
      content.add(s);
    }
    else
    {
      System.out.println(s);
    }
  }
  
  /**
   * Prints a string on the same line if printing is enabled. 
   * Doesn't print when string is 
   * "@@DONOTPRINT@@", this is used by other classes for error messages.
   * Stores the string if printing is disabled.
   * @param s: the string to print
   */
  public static void print(String s)
  {
    if (s.equals("@@DONOTPRINT@@"))
    {
      return;
    }
    if (canPrint == false)
    {
      content.add(s);
    }
    else
    {
      System.out.print(s);
    }
  }
  
  /**
   * Disable's printing to screen
   */
  public static void disablePrinting()
  {
    canPrint = false;
  }
  
  /**
   * Returns a string of all the strings stored while printing was
   * disabled
   * @return c: a string of all the strings stored while printing was
   * disabled
   */
  public static String getContent()
  {
    String c = "";
    Iterator<String> i = content.iterator();
    while(i.hasNext())
    {
      c = c + i.next();
    }
    return c;
  }
  
  /**
   * Enables printing to screen
   */
  public static void enablePrinting()
  {
    canPrint = true;
  }
  
  /**
   * Clear the content array (the array keeping track of strings stored
   * while printing was disabled)
   */
  public static void clearContent()
  {
    content.clear();
  }
  
  /**
   * Distinguishes which output is printed to screen
   * (for JUnit testing purposes)
   * @return: whether or not it is possible to print
   */
  public static boolean isCanPrint() {
    return canPrint;
  }
}
