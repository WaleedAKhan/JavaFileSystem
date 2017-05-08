package driver;

import java.util.*;

/**
 * This class creates a stack of previously visited
 * directories, which can then be popped back into
 * the user's current working directory
 * 
 * @author Dmitry, Waleed, Priyen, Kamal
 *
 */
public class CommandPushPopD {
  
  // Stack for pushd/popd - directories are always Strings
  static Stack<String> stack = new Stack<String>();
  
  /**
   * Push the current working directory onto the
   * stack
   * @param fs: FileSystem which is being manipulated
   * @param path: current working directory
   */
  public static void pushd(FileSystem fs, String path) {
    if (fs.getFolderFromPath(path) != null) {
      String toPush = (CommandPwd.pwd(fs));
      CommandCd.cd(fs, path);
      //outPrintStream.println(toPush);
      push(toPush);
    }
    else{
      System.err.println("pushd: Cannot push this directory");
    }
  }
  
  /**
   * Pops the elements from the stack and changes the 
   * working directory
   * @param fs: FileSystem which elements are being popped from
   */
  public static void popd(FileSystem fs) {
    // makes sure it isn't empty
    if (stack.empty()){
      System.err.println("popd: Nothing to pop from stack");
    }
    else{
      CommandCd.cd(fs, pop());
    }
    
  }
 
  /**
   * Pushes an element
   * @param toAdd: element to be pushed
   */
  public static void push (String toAdd){
    stack.push(toAdd);
  }
  
  /**
   * Element to be popped
   * @return the element popped or an error is stack is empty
   */
  public static String pop(){
    if (!(stack.empty())){
      return stack.pop();
    }
    else{
      // internal message in pop()
      return "Nothing to pop from stack";
    }

  }
  
}
