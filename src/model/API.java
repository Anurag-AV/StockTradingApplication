package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is responsible to call the API using a URL and get the data from the API.
 */
class API {

  String apiKey = "DP8I3RBRX06UZB4F";


  /**
   * Method to calls the specified URL and return the received data in a string format.
   *
   * @param callUrl callUrl a String object representing the URL.
   * @param addKey  is the API key.
   * @return the data received from the API in a string format.
   */
  String callApi(String callUrl, boolean addKey) {
    StringBuilder output = new StringBuilder();
    URL url;
    try {
      if (addKey) {
        url = new URL(callUrl + "&apikey=" + apiKey);
      } else {
        url = new URL(callUrl);
      }

    } catch (MalformedURLException e) {
      throw new RuntimeException("the API has either changed or "
              + "no longer works");
    }

    InputStream in = null;

    try {
      in = url.openStream();
      int buffer;
      while ((buffer = in.read()) != -1) {
        output.append((char) buffer);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No data found");
    }

    return output.toString();
  }
}
