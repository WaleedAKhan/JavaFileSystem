package driver;

import java.net.*;
import java.io.*;

/**
 * This class implements the get command which retrieves the contents of a
 * file from a URL and writes it to a file in the current working directory.
 * 
 * @author Dmitry, Waleed, Priyen, Kamal
 * 
 */
public class CommandGetUrl {

  // starts off with invalid
  private static String urlProcess = "invalid";

  /**
   * Determines if URL is valid and parses it by reading each line and writing
   * it to a file
   * 
   * @param urlToParse: the URL which will be processed
   * @param fs: The FileSystem which for which the get command will work for
   * @return: outcome of process
   */
  public static String CommandGetUrl(String urlToParse, FileSystem fs) {
    try {
      Folder dir = fs.getWorkingDirectory();
      URL url = new URL(urlToParse);
      // sets file to the correct name with error checking
      urlProcess = fileChecker(url.getFile());
      // establishing connection
      URLConnection openURL = url.openConnection();
      BufferedReader br =
          new BufferedReader(new InputStreamReader(openURL.getInputStream()));
      File newFile = new File(urlProcess, br.readLine());
      while (true) {
        String eachLine = br.readLine();
        if (eachLine == null) {
          break;
        } else {
          newFile.appendText("\n" + eachLine);
          FileSystem.setFileLocation(newFile, dir);
        }
      }
    } catch (IOException e) {
      //cannot be read
      System.err.println("Bad URL: " + urlToParse);
      return "@@DONOTPRINT@@";
    }
    return "@@DONOTPRINT@@";
  }

  /**
   * Determines if the File name retrieved contains the correct syntax and
   * whether or not it is the actual file name or a part of the URL.
   * 
   * @param urlProcess: unprocessed File name
   * @return: processed File Name
   */
  public static String fileChecker(String urlProcess) {
    int counter = 0;
    while (counter < urlProcess.length()) {
      if (urlProcess.contains("/")) {
        // makes sure that this is last file in URL
        int slashLocater = urlProcess.indexOf("/");
        urlProcess =
            urlProcess.substring(slashLocater + 1, urlProcess.length());
      }
      counter++;
    }
    return urlProcess;
  }
}
