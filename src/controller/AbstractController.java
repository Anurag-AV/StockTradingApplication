package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Abstract class which contains common functionalities that different controller option classes
 * have.
 */
public abstract class AbstractController {

  /**
   * It validates the integer given as input such that it would be a positive non-zero number. Also,
   * restricts user from entering decimal values.
   *
   * @param count is the integer entered as string by the user.
   * @return if the entered string is a valid integer.
   */
  public boolean validateCount(String count) {
    int cnt = Integer.parseInt(count);
    if (cnt <= 0) {
      throw new NumberFormatException("Please enter valid quantity");
    }
    return true;
  }

  /**
   * This method gives the path of the folder where all our portfolios are saved.
   *
   * @return the Path of the directory where all portfolios are saved.
   * @throws IOException when not able to create the directory.
   */
  protected String getPortfolioFilesPath() throws IOException {
    Path currentDir = Paths.get("").toAbsolutePath();
    Path portfolioFolder = currentDir.resolve("portfolio");
    if (!Files.exists(portfolioFolder)) {
      // Create the portfolio folder if it doesn't exist
      try {
        Files.createDirectory(portfolioFolder);
      } catch (IOException e) {
        throw new IOException("Unable to create portfolio directory");
      }
    }
    return portfolioFolder.toString();
  }

  /**
   * This method gives the path of the folder where all our transactions are saved.
   *
   * @return the Path of the directory where all transactions are saved.
   * @throws IOException when not able to create the directory.
   */
  protected String getTransactionsFilesPath() throws IOException {
    Path currentDir = Paths.get("").toAbsolutePath();
    Path transactionFolder = currentDir.resolve("transactions");
    if (!Files.exists(transactionFolder)) {
      // Create the transaction folder if it doesn't exist
      try {
        Files.createDirectory(transactionFolder);
      } catch (IOException e) {
        throw new IOException("Unable to create portfolio directory");
      }
    }
    return transactionFolder.toString();
  }


  /**
   * Method to return the ticker and company name as comma separated string. So if user enters
   * ticker, we can find the company and if found we know that ticker is valid.
   *
   * @param key is either the ticker symbol or the company name.
   * @return the ticker symbol and a company name as a string separated by comma.
   * @throws IOException if the file to search is not present.
   */
  protected String getCompanyDetails(String key) throws IOException {
    DataManager dataManager = new DataManager();
    String companyInfo = dataManager.getCompanyNameAndTicker(key);
    if (companyInfo == null || companyInfo.isEmpty()) {
      throw new IllegalArgumentException("Entered company details is invalid.");
    }
    return companyInfo;
  }

  /**
   * This method checks whether the portfolio name is already present in the portfolio directory.
   *
   * @param name is the portfolio name.
   * @return true if portfolio is present.
   * @throws IOException if portfolio directory is not present.
   */
  protected boolean checkPortfolioPresent(String name) throws IOException {
    DataManager dataManager = new DataManager();
    String[] filesList = dataManager.listFiles(getPortfolioFilesPath()).split(",");
    for (String s : filesList) {
      if (Objects.equals(s, name + ".csv")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Helper method that checks whether the market was open during the given date.
   *
   * @param date date to be analyzed/verified
   * @return returns a boolean, true if market is open, else false
   */
  protected boolean checkIsStockMarketOpen(LocalDate date) {
    return date.getDayOfWeek() != DayOfWeek.SATURDAY &&
            date.getDayOfWeek() != DayOfWeek.SUNDAY && date.getDayOfYear() != 1;
  }

  protected void validateStartEndDate(LocalDate date, LocalDate date2) {
    if (date.isAfter(date2)) {
      throw new IllegalArgumentException("The start date should be before the end date");
    }
  }

  protected void validateSingularDate(LocalDate date) {
    if (date.isAfter(LocalDate.now()) || date.equals(LocalDate.now())) {
      throw new IllegalArgumentException("Please enter a date that is not " +
              "in the present or future.");
    }
  }

  protected LocalDate validateDateFormat(String d) {
    LocalDate date;
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      date = LocalDate.parse(d.strip(), formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Please enter a valid date");
    }
    return date;
  }

  protected boolean validateRegex(String name) {
    String regex = "[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9]+)*";
    return name.matches(regex);
  }
}
