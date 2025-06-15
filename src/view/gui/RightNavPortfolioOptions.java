package view.gui;


import com.toedter.calendar.JDateChooser;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 * This class represents the right navigation panel with all the options available once a portfolio
 * is loaded or created into the application.
 */
public class RightNavPortfolioOptions extends JPanel implements ActionListener {
  ActionListener actionListener;
  InputForm buyStock;
  InputForm sellStock;
  InputForm getValue;
  InputForm getCostBasis;
  InputContainer inputContainer;
  JPanel form;

  List<JPanel> stockList;
  JPanel listOption;

  /**
   * Constructor to set all the buttons, action listeners and input containers in the panel.
   *
   * @param listener       is the action listener.
   * @param inputContainer is the input container.
   * @param parent         is the parent.
   */
  public RightNavPortfolioOptions(ActionListener listener, InputContainer inputContainer,
                                  ActionListener parent) {
    this.actionListener = listener;
    this.inputContainer = inputContainer;
    buyStock = buyStockForm();
    sellStock = sellStockForm();
    getValue = getValueForm();
    getCostBasis = getCostBasisForm();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(new CustomButton("Buy Stock",
            "buyStock",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("Weighted Invest",
            "multiStock",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("Sell Stock",
            "sellStock",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("Get Portfolio Value",
            "getValue",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("Get Portfolio cost basis",
            "costBasis",
            this,
            true,
            "").get());
    add(Box.createRigidArea(new Dimension(-1, 4)));
    add(new CustomButton("Show Composition",
            "showComposition",
            actionListener,
            true,
            "").get());

  }

  private InputForm buyStockForm() {
    InputForm ticker = new InputForm("buyStock", this, actionListener,
            "Buy stocks");

    JLabel stockNameLbl = new JLabel("Enter stock name");
    JTextField stockName = new JTextField();
    JLabel enterQuantity = new JLabel("Enter quantity");
    JTextField quantity = new JTextField();
    JLabel enterDate = new JLabel("Enter date");
    JDateChooser date = new JDateChooser();
    date.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    date.getDateEditor().setEnabled(false);

    inputContainer.setText("buyStockName", stockName);
    inputContainer.setText("buyStockQuantity", quantity);
    inputContainer.setDate("buyStockDate", date);

    return ticker
            .addLabel(stockNameLbl)
            .addTextField(stockName)
            .addLabel(enterQuantity)
            .addTextField(quantity)
            .addLabel(enterDate)
            .addDateField(date);

  }

  private InputForm sellStockForm() {
    InputForm ticker = new InputForm("sellStock", this, actionListener,
            "Sell Stock");

    JLabel stockNameLbl = new JLabel("Enter stock name");
    JTextField stockName = new JTextField();
    JLabel enterQuantity = new JLabel("Enter quantity");
    JTextField quantity = new JTextField();
    JLabel enterDate = new JLabel("Enter date");
    JDateChooser date = new JDateChooser();
    date.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    date.getDateEditor().setEnabled(false);

    inputContainer.setText("sellStockName", stockName);
    inputContainer.setText("sellStockQuantity", quantity);
    inputContainer.setDate("sellStockDate", date);

    return ticker
            .addLabel(stockNameLbl)
            .addTextField(stockName)
            .addLabel(enterQuantity)
            .addTextField(quantity)
            .addLabel(enterDate)
            .addDateField(date);

  }

  private InputForm getValueForm() {
    InputForm ticker = new InputForm("getValue", this, actionListener,
            "Get portfolio value");

    JLabel enterDate = new JLabel("Enter date");
    JDateChooser date = new JDateChooser();
    date.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    date.getDateEditor().setEnabled(false);

    inputContainer.setDate("getValueDate", date);

    return ticker
            .addLabel(enterDate)
            .addDateField(date);

  }

  private InputForm getCostBasisForm() {
    InputForm ticker = new InputForm("getCostBasis", this, actionListener,
            "Get cost basis");

    JLabel enterDate = new JLabel("Enter date");
    JDateChooser date = new JDateChooser();
    date.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    date.getDateEditor().setEnabled(false);

    inputContainer.setDate("getCostBasisDate", date);

    return ticker
            .addLabel(enterDate)
            .addDateField(date);

  }

  private void multiStockForm() {
    form = new JPanel();
    stockList = new ArrayList<>();
    stockList.add(listOption(0));
    form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
    JPanel amount = new JPanel(new GridLayout(2, 0, 2, 2));

    JLabel totalAmountLabel = new JLabel("Total Amount");
    JTextField totalAmount = new JTextField();
    amount.add(totalAmountLabel);
    amount.add(totalAmount);
    inputContainer.setText("multiStockTotalAmount", totalAmount);


    JPanel topPanel = new JPanel(new GridLayout(1, 0, 5, 5));
    topPanel.add(amount);
    form.add(topPanel);

    JPanel datesPanel = new JPanel(new GridLayout(1, 0, 2, 2));
    JLabel startDate = new JLabel("Purchase Date", SwingConstants.CENTER);
    com.toedter.calendar.JDateChooser jdateChooser = new com.toedter.calendar.JDateChooser();
    jdateChooser.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    jdateChooser.getDateEditor().setEnabled(false);
    inputContainer.setDate("multiStockStartDate", jdateChooser);
    datesPanel.add(startDate);
    datesPanel.add(jdateChooser);

    form.add(datesPanel);

    JPanel buttons = new JPanel();
    buttons.add(new CustomButton("+ stock",
            "expandList",
            this,
            true,
            "").get());
    buttons.add(new CustomButton("- stock",
            "shrinkList",
            this,
            true,
            "").get());
    form.add(buttons);
    form.add(stockList.get(0));


    JScrollPane scrollPane = new JScrollPane(form);
    scrollPane.setPreferredSize(new Dimension(360, 240));
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    int option = JOptionPane.showConfirmDialog(this, scrollPane,
            "Add multi stock", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE);
    if (option == JOptionPane.OK_OPTION) {
      actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
              "multiStock"));
    }
  }

  private JPanel listOption(int size) {
    listOption = new JPanel(new GridLayout(1, 0, 0, 0));
    listOption.setSize(new Dimension(80, 20));
    JLabel stock = new JLabel("Stock  ", SwingConstants.RIGHT);
    JLabel count = new JLabel("Percent  ", SwingConstants.RIGHT);
    JTextField stockName = new JTextField();
    JTextField quantity = new JTextField();
    JPanel stockPanel = new JPanel(new GridLayout());
    JPanel countPanel = new JPanel(new GridLayout());
    stockPanel.add(stock);
    stockPanel.add(stockName);
    countPanel.add(count);
    countPanel.add(quantity);
    listOption.add(stockPanel);
    listOption.add(countPanel);
    inputContainer.setText("multiStockName" + (size), stockName);
    inputContainer.setText("multiStockQuantity" + (size), quantity);
    inputContainer.setText("multiStockSize", new JTextField(String.valueOf(size + 1)));
    return listOption;
  }


  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "buyStock":
        buyStock.show();
        break;
      case "sellStock":
        sellStock.show();
        break;
      case "getValue":
        getValue.show();
        break;
      case "costBasis":
        getCostBasis.show();
        break;
      case "multiStock":
        multiStockForm();
        break;
      case "expandList":
        for (JPanel listOption : stockList) {
          form.remove(listOption);
        }
        stockList.add(listOption(stockList.size()));
        for (JPanel listOption : stockList) {
          form.add(listOption);
        }
        form.revalidate();
        form.repaint();
        break;
      case "shrinkList":
        if (stockList.size() > 1) {
          form.remove(stockList.remove(stockList.size() - 1));
          inputContainer.setText("multiStockSize", new JTextField(
                  String.valueOf(stockList.size())));
          form.revalidate();
          form.repaint();
        }
        break;
      default:
    }
  }
}
