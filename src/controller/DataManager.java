package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

import model.APIManager;

/**
 * This class acts like a data manager and manages the access of cache data if found. If cache not
 * present, it sends the data to the model to make an API call.
 * Changes: The methods have been given less restrictive access modifiers for code reuse.
 */
public class DataManager {

  /**
   * Method to get a complete days record of the stock for the given ticker and date.
   * @param ticker is the ticker symbol of stock.
   * @param date is the date.
   * @return the stock details.
   * @throws IOException is stock not found.
   */
  public String getStockDateRecord(String ticker, LocalDate date) throws IOException {
    //what if today's price did not come
    //what if today is weekend
    String path = getCachePath();
    String files = listFiles(path);
    String filename = "";
    boolean cacheExists = false;
    for (String file : files.split(",")) {
      // Check if the file is a CSV file
      if (file.toLowerCase().endsWith(".csv")) {
        if (Objects.equals(file.split("_")[0], ticker)) {
          filename = file;
          cacheExists = true;
          break;
        }
      }
    }

    if (cacheExists) {
      ParserInterface parser = new Parser();
      if (!LocalDate.parse(filename.split("_")[1].split("\\.")[0])
              .isEqual(LocalDate.now())) {
        createCache(ticker, path, true);
        return getStockDateRecord(ticker, date);
      }
      String data = parser.readFile(path + File.separator + filename);
      String[] records = data.split("\n");
      LocalDate firstDate = LocalDate.parse(records[1].split(",")[0]);
      if (date.isAfter(firstDate)) {
        return records[1];
      }
      for (int i = 1; i < records.length; i++) {
        String[] values = records[i].split(",");
        if (LocalDate.parse(values[0]).isEqual(date)) {
          return records[i];
        } else if (LocalDate.parse(values[0]).isBefore(date)) {
          return records[i];
        }
      }
      return null;
    } else {
      createCache(ticker, path, false);
      return getStockDateRecord(ticker, date);
    }
  }

  /**
   * This method retrieves the stock value of that ticker at the specific date first from cache then
   * from API.
   *
   * @param ticker ticker symbol of the stock.
   * @param date   date on which the stock value is to be retrieved.
   * @return String value as whole record value at that day and return 0 if invalid date.
   */
  public String getStockValueAll(String ticker, LocalDate date) throws IOException {
    return getStockDateRecord(ticker, date);
  }

  /**
   * This method retrieves the stock value of that ticker at the specific date first from cache then
   * from API.
   *
   * @param ticker ticker symbol of the stock.
   * @param date   date on which the stock value is to be retrieved.
   * @return double value as stock value at that day and return 0 if invalid date.
   */
  public double getStockValue(String ticker, LocalDate date) throws IOException {
    String data = getStockDateRecord(ticker, date);
    if (!data.isEmpty()) {
      return Double.parseDouble(data.split(",")[4]);
    }
    return 0.0;
  }

  /**
   * This method creates a cache file to store the historical data provided by the API.
   *
   * @param ticker   is the ticker symbol of company.
   * @param filePath is the path where file is to be created.
   * @param update   checker to indicate if file is present or not.
   * @throws IOException if cache not found.
   */
  private void createCache(String ticker, String filePath, boolean update) throws IOException {
    APIManager dataset = new APIManager();
    String data = dataset.timeSeriesData(ticker);
    ParserInterface parser = new Parser();
    String filename = "";

    String files = listFiles(filePath);
    for (String file : files.split(",")) {
      // Check if the file is a CSV file
      if (file.toLowerCase().endsWith(".csv")) {
        if (file.split("_")[0].equals(ticker)) {
          filename = file;
        }
      }
    }
    if (data.charAt(0) == '{') {
      if (update) {
        data = parser.readFile(filePath + File.separator + filename);
        parser.deleteFile(filename, filePath);
      } else {
        throw new IOException("API calls exceeded and no cache found");
      }

    }
    if (update) {
      parser.deleteFile(filename, filePath);
      parser.createFile(data, ticker + "_" + LocalDate.now() + ".csv", filePath);
    } else {
      parser.createFile(data, ticker + "_" + LocalDate.now() + ".csv", filePath);
    }

  }

  /**
   * Method to get the path of the cache directory where the cache needs to be stored.
   *
   * @return the cache folder path as a string.
   * @throws IOException if cache is not present.
   */
  public String getCachePath() throws IOException {
    Path currentDir = Paths.get("").toAbsolutePath();
    Path cacheFolder = currentDir.resolve("cache");
    if (!Files.exists(cacheFolder)) {
      // Create the portfolio folder if it doesn't exist
      try {
        Files.createDirectory(cacheFolder);
      } catch (IOException e) {
        throw new IOException("Cache Folder does not exist. Please read setup.txt first.");
      }
    }
    return cacheFolder.toString();
  }

  /**
   * Method that returns the company name and ticker symbol. It stores the cache value. of company
   * name and ticker mapping if required.
   *
   * @param key a string value of either a valid ticker symbol or a valid company name.
   * @return a comma separated string first element is ticker symbol and second is company name.
   */
  public String getCompanyNameAndTicker(String key) throws IOException {
    String path = getCachePath();
    String files = listFiles(path);
    ParserInterface parser = new Parser();
    boolean cacheExists = false;
    for (String file : files.split(",")) {
      // Check if the file is a CSV file
      if (file.toLowerCase().endsWith(".csv")) {
        if (file.equals("listing_status.csv")) {
          cacheExists = true;
          break;
        }
      }
    }
    if (cacheExists) {
      String data = parser.readFile(getCachePath() + File.separator + "listing_status.csv");
      if (!data.isEmpty()) {
        String[] records = data.split("\n");
        String[] values;
        for (int i = 1; i < records.length; i++) {
          values = records[i].split(",");
          if (values[0].equalsIgnoreCase(key) || values[1].equalsIgnoreCase(key)) {
            return values[0] + "," + values[1];
          }
        }
      } else {
        APIManager dataset = new APIManager();
        data = dataset.listingStatus();
        parser.updateFile(data, "listing_status.csv", path);
        return getCompanyNameAndTicker(key);
      }
    } else {
      APIManager dataset = new APIManager();
      String data = dataset.listingStatus();
      parser.createFile(data, "listing_status.csv", path);
      return getCompanyNameAndTicker(key);
    }
    throw new IOException("Company data not found.");
  }

  /**
   * Method to list all the files present in the folder.
   *
   * @param path string representing the path of the folder in which we should check.
   * @return a comma separated string of file names.
   */
  public String listFiles(String path) {
    File directory = new File(path);
    File[] files = directory.listFiles();
    StringBuilder filesList = new StringBuilder();
    if (files != null) {
      // Loop through files
      for (File file : files) {
        // Check if the file is a CSV file
        if (file.isFile()) {
          filesList.append(file.getName());
          filesList.append(",");
        }
      }
    }
    return filesList.toString();
  }

  /**
   * Function to check if the specified file exists.
   *
   * @param path     path of the folder where we have to check
   * @param fileName name of the file to be searched
   * @return a boolean upon the existence of the file in the folder
   */
  public boolean fileExists(String path, String fileName) {
    String[] files = listFiles(path).split(",");
    for (String file : files) {
      if (Objects.equals(file, fileName)) {
        return true;
      }
    }
    return false;
  }
}
