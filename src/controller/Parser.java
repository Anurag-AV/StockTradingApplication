package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Parser Class to perform file operations like reading, writing, updating and deleting CSV files.
 * Changes: Parser class now implements the ParserInterface which allows us to work with multiple
 * types of files.
 */
public class Parser implements ParserInterface {

  /**
   * The function parses the given data into a csv and stores it in the given path.
   *
   * @param data     string data separated by newline character (\n).
   * @param fileName name of the file e as String.
   * @throws IOException throws IOException when file cannot be found or read.
   */
  public void createFile(String data, String fileName, String filePath) throws IOException {
    File directory = new File(filePath);
    if (!directory.exists()) {
      if (!directory.mkdirs()) {
        throw new IOException("Unable to create directory");
      }
    }
    fileName = filePath + File.separator + fileName;
    if (!fileName.toLowerCase().endsWith(".csv")) {
      fileName += ".csv";
    }
    File newfile = new File(fileName);
    if (newfile.exists()) {
      throw new IOException("File already exists");
    }
    FileWriter fileWriter = new FileWriter(fileName);
    PrintWriter printWriter = new PrintWriter(fileWriter);
    String[] records = data.split("\n");
    int colCount = records[0].split(",").length;
    for (String record : records) {
      if (record.split(",").length != colCount) {
        File file = new File(fileName);
        printWriter.close();
        if (file.delete()) {
          throw new IOException("File deleted due to unmatched number of columns");
        } else {
          throw new IOException("Some error occurred, unable to delete file if created");
        }

      }
      printWriter.print(record);
      if (!record.endsWith(System.lineSeparator())) {
        printWriter.print(System.lineSeparator());
      }
    }
    printWriter.close();
    fileWriter.close();
  }

  /**
   * This method reads a csv file and returns a newline separated string.
   *
   * @param path path to the file to be read with filename.
   * @return a String containing all the data separated by newline.
   * @throws IOException when no file or invalid file is found.
   */
  public String readFile(String path) throws IOException {
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(path));
    } catch (IOException e) {
      throw new IOException("File not found");
    }
    int line;
    StringBuilder data = new StringBuilder();
    while ((line = br.read()) != -1) {
      data.append((char) line);
    }
    br.close();
    return data.toString().replace("\r", "");
  }

  /**
   * This method updates an existing csv with the given data.
   *
   * @param data     string data separated by newline character (\n).
   * @param fileName name of the file as String.
   * @param filePath path of the file.
   * @throws IOException throws IOException when file cannot be found or read.
   */
  public void updateFile(String data, String fileName, String filePath) throws IOException {
    String fullFileName = filePath + File.separator + fileName;
    File newfile = new File(fullFileName);
    if (!fullFileName.endsWith(".csv")) {
      fullFileName += ".csv";
    }
    if (!newfile.exists()) {
      throw new IOException("File does not exist");
    } else {
      if (newfile.delete()) {
        createFile(data, fileName, filePath);
      } else {
        throw new IOException("Some error occurred, unable to delete the old file");
      }
    }
  }

  /**
   * This method deletes the given csv file.
   *
   * @param fileName name of the file in String format.
   * @param filePath pat to the file in a string format.
   */
  public void deleteFile(String fileName, String filePath) {
    fileName = filePath + File.separator + fileName;
    if (!fileName.endsWith(".csv")) {
      fileName += ".csv";
    }
    File newfile = new File(fileName);
    boolean status = newfile.delete();
  }
}
