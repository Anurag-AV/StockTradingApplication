package controller;

import java.io.IOException;

/**
 * This interface represents a file parser. Implementations of this interface will perform all File
 * IO related operations.
 */
public interface ParserInterface {

  /**
   * The function parses the given data into a csv and stores it in the given path.
   *
   * @param data     string data separated by newline character (\n).
   * @param fileName name of the file e as String.
   * @throws IOException throws IOException when file cannot be found or read.
   */
  void createFile(String data, String fileName, String filePath) throws IOException;


  /**
   * This method reads a csv file and returns a newline separated string.
   *
   * @param path path to the file to be read with filename.
   * @return a String containing all the data separated by newline.
   * @throws IOException when no file or invalid file is found.
   */
  String readFile(String path) throws IOException;

  /**
   * This method updates an existing csv with the given data.
   *
   * @param data     string data separated by newline character (\n).
   * @param fileName name of the file as String.
   * @param filePath path of the file.
   * @throws IOException throws IOException when file cannot be found or read.
   */
  void updateFile(String data, String fileName, String filePath) throws IOException;

  /**
   * This method deletes the given csv file.
   *
   * @param fileName name of the file in String format.
   * @param filePath pat to the file in a string format.
   */
  void deleteFile(String fileName, String filePath);

}
