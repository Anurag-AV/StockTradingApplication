package model;

/**
 * This interface represents an API manager which creates and formats API URLs so that the
 * respective API calls can be made.
 */
public interface APIManagerInterface {

  /**
   * Method to format a URL which will return us historical time series data from an API.
   *
   * @param ticker is the ticker symbol of the company.
   * @return the time series data as a string.
   */
  String timeSeriesData(String ticker);

  /**
   * Method to get the listing status of all the companies from the API.
   *
   * @return the listing status of companies as a string.
   */
  String listingStatus();
}
