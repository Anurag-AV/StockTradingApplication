package model;

/**
 * This class represents an API manager which creates and formats API URLs so that the respective
 * API calls can be made.
 */
public class APIManager implements APIManagerInterface {

  API api;

  /**
   * Constructor to initialize class variables to API object.
   */
  public APIManager() {
    api = new API();
  }

  @Override
  public String timeSeriesData(String ticker) {
    return api.callApi("https://www.alphavantage"
            + ".co/query?function=TIME_SERIES_DAILY"
            + "&outputsize=full"
            + "&symbol"
            + "=" + ticker + "&datatype=csv", true);
  }

  @Override
  public String listingStatus() {
    return api.callApi("https://www.alphavantage.co/query?function=LISTING_STATUS" +
            "&apikey=demo", false);
  }
}
