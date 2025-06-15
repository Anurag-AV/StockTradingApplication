package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

/**
 * Class to provide analysis functionalities to both portfolio and stock which calculates the
 * bar graph for the given stock or portfolio using relative or absolute scaling depending
 * on the data sent to it.
 */
class Plotter {

  /**
   * This method computes a bar chart for the given data with appropriate scaling and gaps.
   *
   * @param date1 first date of the rage.
   * @param date2 second date of the range.
   * @param data  date of the stock.
   * @return a string containing all the lines of the chart.
   */

  public String getPerformance(LocalDate date1, LocalDate date2, String data)
          throws IllegalArgumentException {
    // timestamp,open,high,low,close,volume
    String[] stockValues = data.split("\n", 2)[1].split("\n");
    StringBuilder performanceValues = new StringBuilder();
    long days = Math.abs(Duration.between(date1.atStartOfDay(), date2.atStartOfDay()).toDays());
    double maxValue = 0;
    double minValue = Integer.MAX_VALUE;
    double totalValue = 0;
    for (String stockValue : stockValues) {
      String[] values = stockValue.split(",");
      if (
              (date1.toString().compareTo(values[0]) >= 0
                      && date2.toString().compareTo(values[0]) <= 0)
                      ||
                      (date1.toString().compareTo(values[0]) <= 0
                              && date2.toString().compareTo(values[0]) >= 0)
      ) {
        if (Double.parseDouble(values[4]) > maxValue) {
          maxValue = Double.parseDouble(values[4]);
        }
        if (Double.parseDouble(values[4]) < minValue) {
          minValue = Double.parseDouble(values[4]);
        }
        totalValue += Double.parseDouble(values[4]);
        performanceValues.append(stockValue).append("\n");
      }
    }

    if (performanceValues.length() < 1 || totalValue <= 0) {
      throw new IllegalArgumentException("No data present for the specific range");
    }
    double difference = maxValue - minValue;
    double scale;
    double offset = 0;
    scale = getScale(maxValue, 1);
    if (difference < minValue) {
      offset = (minValue * 0.9);
      scale = getScale(maxValue - offset, 1);
    }
    if (scale > minValue) {
      scale = getScale(difference, 1);
    }

    return analyzer(days, scale, offset, performanceValues);

  }

  private String analyzer(long days, double scale, double offset, StringBuilder performanceValues) {
    if (((int) (days)) <= 30) {
      return daily(performanceValues.toString(), scale, offset);
    } else if (((int) (days)) > 30 && ((int) (days / 30)) < 5) {
      return nDay(performanceValues.toString(), scale, ((int) (days / 6)) - 1, offset);
    } else if (((int) (days / 30)) <= 30) {
      return monthly(performanceValues.toString(), scale, offset);
    } else if (((int) (days / 30)) > 30 && ((int) (days / 90)) < 5) {
      return nMonth(performanceValues.toString(), scale, ((int) (days / 6)) - 1, offset);
    } else if (((int) (days / 90)) <= 30) {
      return nMonth(performanceValues.toString(), scale, 3, offset);
    } else if (((int) (days / 90)) > 30 && ((int) (days / 180)) < 5) {
      return nMonth(performanceValues.toString(), scale, ((int) (days / 6)) - 1, offset);
    } else if (((int) (days / 180)) <= 30) {
      return nMonth(performanceValues.toString(), scale, 6, offset);
    } else {
      return annually(performanceValues.toString(), scale, offset);
    }
  }

  private double getScale(double value, double scl) {
    double scale = scl;
    while (true) {
      if (value / scale == 0) {
        throw new IllegalArgumentException();
      }
      if ((value / scale) <= 50) {
        if ((value / scale) >= 5) {
          break;
        }
        scale *= 0.8;
        continue;
      }
      scale *= 2;
    }
    return scale;
  }

  private String getAsteriskString(double n) {
    return "*".repeat((int) n);
  }

  private double round(double value) {
    return ((double) Math.round(value * 100) / 100);
  }

  private String daily(String performanceValues, double scale, double offset) {
    StringBuilder finalView = new StringBuilder();
    for (String performance : performanceValues.split("\n")) {
      String[] values = performance.split(",");
      finalView.append(values[0])
              .append(": ")
              .append(getAsteriskString((Double.parseDouble(values[4]) - offset) / scale))
              .append("\n");
    }
    finalView.append("\n");
    finalView.append("Scale: * = $")
            .append(round(scale));
    if (offset > 0) {
      finalView.append(" more than $")
              .append(round(offset));
    }

    return finalView.toString();
  }

  private String nDay(String performanceValues, double scale, int days, double offset) {
    StringBuilder finalView = new StringBuilder();
    String day = "";
    int dayChange = 0;
    String[] performance = performanceValues.split("\n");
    for (int i = 0; i < performance.length; i++) {
      String[] values = performance[i].split(",");
      if (Objects.equals(day, "")
              ||
              dayChange == days || i == performance.length - 1
      ) {
        finalView
                .append(values[0])
                .append(": ")
                .append(getAsteriskString((Double.parseDouble(values[4]) - offset) / scale))
                .append("\n");
        day = values[0].split("-")[2];
        dayChange = 0;
      } else {
        dayChange += 1;
      }


    }
    finalView.append("\n");
    finalView.append("Scale: * = $")
            .append(round(scale));
    if (offset > 0) {
      finalView.append(" more than $")
              .append(round(offset));
    }
    return finalView.toString();
  }

  private String monthly(String performanceValues, double scale, double offset) {
    StringBuilder finalView = new StringBuilder();
    String month = "";
    for (String performance : performanceValues.split("\n")) {
      String[] values = performance.split(",");
      if (!Objects.equals(values[0].split("-")[1], month)) {
        finalView
                .append(LocalDate.parse(values[0]).getMonth()
                        .getDisplayName(TextStyle.SHORT, Locale.US))
                .append(" ")
                .append(values[0].split("-")[0])
                .append(": ")
                .append(getAsteriskString((Double.parseDouble(values[4]) - offset) / scale))
                .append("\n");
        month = values[0].split("-")[1];
      }
    }
    finalView.append("\n");
    finalView.append("Scale: * = $")
            .append(round(scale));
    if (offset > 0) {
      finalView.append(" more than $")
              .append(round(offset));
    }
    return finalView.toString();
  }

  private String nMonth(String performanceValues, double scale, int months, double offset) {
    StringBuilder finalView = new StringBuilder();
    String month = "";
    int monthChange = 0;
    String tempMonth = "0";
    String[] performance = performanceValues.split("\n");
    for (int i = 0; i < performance.length; i++) {
      String[] values = performance[i].split(",");
      if (
              !Objects.equals(values[0].split("-")[1], month)
                      && (Objects.equals(month, "")
                      ||
                      monthChange == months || i == performance.length - 1)
      ) {
        finalView
                .append(LocalDate.parse(values[0]).getMonth()
                        .getDisplayName(TextStyle.SHORT, Locale.US))
                .append(" ")
                .append(values[0].split("-")[0])
                .append(": ")
                .append(getAsteriskString((Double.parseDouble(values[4]) - offset) / scale))
                .append("\n");
        month = values[0].split("-")[1];
        tempMonth = values[0].split("-")[1];
        monthChange = 0;
      }
      if (!Objects.equals(values[0].split("-")[1], tempMonth)) {
        tempMonth = values[0].split("-")[1];
        monthChange += 1;
      }

    }
    finalView.append("\n");
    finalView.append("Scale: * = $")
            .append(round(scale));
    if (offset > 0) {
      finalView.append(" more than $")
              .append(round(offset));
    }
    return finalView.toString();
  }

  private String annually(String performanceValues, double scale, double offset) {
    StringBuilder finalView = new StringBuilder();
    String year = "";
    for (String performance : performanceValues.split("\n")) {
      String[] values = performance.split(",");
      if (!Objects.equals(values[0].split("-")[0], year)) {
        finalView
                .append(values[0].split("-")[0])
                .append(": ")
                .append(getAsteriskString((Double.parseDouble(values[4]) - offset) / scale))
                .append("\n");
        year = values[0].split("-")[0];
      }
    }
    finalView.append("\n");
    finalView.append("Scale: * = $")
            .append(round(scale));
    if (offset > 0) {
      finalView.append(" more than $")
              .append(round(offset));
    }
    return finalView.toString();
  }
}
