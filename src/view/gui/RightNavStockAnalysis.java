package view.gui;

import com.toedter.calendar.JDateChooser;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.Dimension;

/**
 * This class represents the right navigation panel with all the options available to the
 * user that can be performed on individual stocks.
 */
public class RightNavStockAnalysis extends JPanel implements ActionListener {

  ActionListener actionListener;
  InputContainer inputContainer;
  InputForm dayPerformance;
  InputForm timeSpanPerformance;
  InputForm xDayMovAvg;
  InputForm crossover;
  InputForm movCrossover;

  /**
   * Constructor to set all the buttons, action listeners and input containers in the panel.
   *
   * @param listener       is the action listener.
   * @param inputContainer is the input container.
   * @param parent         is the parent.
   */
  public RightNavStockAnalysis(ActionListener listener, InputContainer inputContainer,
                               ActionListener parent) {
    this.actionListener = listener;
    this.inputContainer = inputContainer;
    dayPerformance = dayPerformanceForm();
    timeSpanPerformance = timeSpanForm();
    xDayMovAvg = xDayForm();
    crossover = crossoverForm();
    movCrossover = movCrossoverForm();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(new CustomButton("Day performance",
            "dayPerformance",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("Timespan performance",
            "timespanPerformance",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("X day Moving Avg",
            "xDayMovingAvg",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("Crossover",
            "crossover",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("Moving Crossover",
            "movingCrossover",
            this,
            true,
            "").get());
  }

  private InputForm dayPerformanceForm() {
    InputForm ticker = new InputForm("dayPerformance", this, actionListener,
            "Performance Over a Day");
    JLabel stockNameLbl = new JLabel("Enter stock name");
    JTextField stockName = new JTextField();
    JLabel enterDate = new JLabel("Enter date");
    JDateChooser date = new JDateChooser();
    date.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    date.getDateEditor().setEnabled(false);

    inputContainer.setText("dayPerformanceName", stockName);
    inputContainer.setDate("dayPerformanceDate", date);

    return ticker
            .addLabel(stockNameLbl)
            .addTextField(stockName)
            .addLabel(enterDate)
            .addDateField(date);
  }

  private InputForm timeSpanForm() {
    InputForm ticker = new InputForm("timeSpanPerformance", this, actionListener,
            "Performance Over a Given Period");
    JLabel stockNameLbl = new JLabel("Enter stock name");
    JTextField stockName = new JTextField();
    JLabel enterStartDate = new JLabel("Enter start date");
    JDateChooser startDate = new JDateChooser();
    startDate.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    startDate.getDateEditor().setEnabled(false);
    JLabel enterEndDate = new JLabel("Enter end date");
    JDateChooser endDate = new JDateChooser();
    endDate.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    endDate.getDateEditor().setEnabled(false);

    inputContainer.setText("timeSpanPerformanceName", stockName);
    inputContainer.setDate("timeSpanPerformanceStartDate", startDate);
    inputContainer.setDate("timeSpanPerformanceEndDate", endDate);
    return ticker
            .addLabel(stockNameLbl)
            .addTextField(stockName)
            .addLabel(enterStartDate)
            .addDateField(startDate)
            .addLabel(enterEndDate)
            .addDateField(endDate);
  }

  private InputForm xDayForm() {
    InputForm ticker = new InputForm("xDayMovingAvg", this, actionListener,
            "X Day Moving Average");
    JLabel stockNameLbl = new JLabel("Enter stock name");
    JTextField stockName = new JTextField();
    JLabel enterStartDate = new JLabel("Enter date");
    JDateChooser startDate = new JDateChooser();
    startDate.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    startDate.getDateEditor().setEnabled(false);
    JLabel enterX = new JLabel("Enter number of days for X Day Moving Average");
    JTextField x = new JTextField();

    inputContainer.setText("xDayPerformanceName", stockName);
    inputContainer.setDate("xDayPerformanceDate", startDate);
    inputContainer.setText("xDays", x);
    return ticker
            .addLabel(stockNameLbl)
            .addTextField(stockName)
            .addLabel(enterStartDate)
            .addDateField(startDate)
            .addLabel(enterX)
            .addTextField(x);
  }

  private InputForm crossoverForm() {
    InputForm ticker = new InputForm("crossover", this, actionListener,
            "Crossovers");
    JLabel stockNameLbl = new JLabel("Enter stock name");
    JTextField stockName = new JTextField();
    JLabel enterStartDate = new JLabel("Enter start date");
    JDateChooser startDate = new JDateChooser();
    startDate.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    startDate.getDateEditor().setEnabled(false);
    JLabel enterEndDate = new JLabel("Enter end date");
    JDateChooser endDate = new JDateChooser();
    endDate.setMaxSelectableDate(new Date());
    endDate.getDateEditor().setEnabled(false);

    inputContainer.setText("crossPerformanceName", stockName);
    inputContainer.setDate("crossPerformanceStartDate", startDate);
    inputContainer.setDate("crossPerformanceEndDate", endDate);
    return ticker
            .addLabel(stockNameLbl)
            .addTextField(stockName)
            .addLabel(enterStartDate)
            .addDateField(startDate)
            .addLabel(enterEndDate)
            .addDateField(endDate);
  }

  private InputForm movCrossoverForm() {
    InputForm ticker = new InputForm("movingCrossover", this, actionListener,
            "Moving Crossovers");
    JLabel stockNameLbl = new JLabel("Enter stock name");
    JTextField stockName = new JTextField();
    JLabel enterStartDate = new JLabel("Enter start date");
    JDateChooser startDate = new JDateChooser();
    startDate.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    startDate.getDateEditor().setEnabled(false);
    JLabel enterEndDate = new JLabel("Enter end date");
    JDateChooser endDate = new JDateChooser();
    endDate.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    endDate.getDateEditor().setEnabled(false);
    JLabel enterX = new JLabel("Enter number of days for X Day Moving " +
            "Average from Start Date");
    JTextField x = new JTextField();
    JLabel enterY = new JLabel("Enter number of days for X Day Moving Average from End Date");
    JTextField y = new JTextField();

    inputContainer.setText("movCrossPerformanceName", stockName);
    inputContainer.setDate("movCrossPerformanceStartDate", startDate);
    inputContainer.setDate("movCrossPerformanceEndDate", endDate);
    inputContainer.setText("xDay", x);
    inputContainer.setText("yDay", y);
    return ticker
            .addLabel(stockNameLbl)
            .addTextField(stockName)
            .addLabel(enterStartDate)
            .addDateField(startDate)
            .addLabel(enterEndDate)
            .addDateField(endDate)
            .addLabel(enterX)
            .addTextField(x)
            .addLabel(enterY)
            .addTextField(y);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "dayPerformance":
        dayPerformance.show();
        break;
      case "timespanPerformance":
        timeSpanPerformance.show();
        break;
      case "xDayMovingAvg":
        xDayMovAvg.show();
        break;
      case "crossover":
        crossover.show();
        break;
      case "movingCrossover":
        movCrossover.show();
        break;
      default:
    }
  }
}
