package view.gui;

import com.toedter.calendar.JDateChooser;

import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

/**
 * This class implements the view in our model view controller(MVC) architecture. This view
 * is implemented using java swing and provides a graphical user interface for running our
 * application. It has java swing components to provide interactive usage of the graphical
 * user interface.
 */
public class GUIMainView extends JFrame implements ActionListener, MainGUIViewInterface {
  JPanel masterPanel;
  JPanel rightMainNav;
  JPanel displayPanel;
  ActionListener controllerActionListener;
  InputContainer inputContainer;
  JPanel form;

  List<JPanel> stockList;
  JPanel listOption;

  InputMapper inputMapper;
  JPanel dispP;

  /**
   * Constructor to initialize all the navigation plane, input mappers,
   * buttons and action listeners.
   */
  public GUIMainView() {
    super();

    inputContainer = new InputContainerImpl();
    inputMapper = new InputMapper(inputContainer);


    setTitle("Stock Trading");
    setSize(900, 450);

    masterPanel = new JPanel();
    rightMainNav = new JPanel();
    displayPanel = new JPanel();


    masterPanel.setLayout(new GridBagLayout());
    JPanel leftMainNav = new JPanel();
    leftMainNav.setLayout(new BoxLayout(leftMainNav, BoxLayout.Y_AXIS));
    leftMainNav.add(new CustomButton("Create Portfolio",
            "createPortfolio",
            this,
            true,
            "").get());
    leftMainNav.add(Box.createRigidArea(new Dimension(-1, 4)));
    leftMainNav.add(new CustomButton("Create $ Cost Avg Portfolio",
            "costAvgPortfolio",
            this,
            true,
            "").get());
    leftMainNav.add(Box.createRigidArea(new Dimension(-1, 4)));
    leftMainNav.add(new CustomButton("List & Load Portfolio",
            "loadPortfolio",
            this,
            true,
            "").get());
    leftMainNav.add(Box.createRigidArea(new Dimension(-1, 4)));
    leftMainNav.add(new CustomButton("Stock Analytics",
            "stockAnalysis",
            this,
            true,
            "").get());

    JPanel leftNav = new JPanel();
    leftNav.setLayout(new BoxLayout(leftNav, BoxLayout.Y_AXIS));
    leftNav.setBorder(BorderFactory.createTitledBorder("Main Navigation"));
    leftNav.add(leftMainNav);


    this.displayPanel = new JPanel();

    JScrollPane scrollPane = new JScrollPane(this.displayPanel);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Display"));
    dispP = new DisplayPanel("Welcome to Stock Trading. You can begin " +
            "by selecting an option on the left");
    displayPanel.add(dispP);


    rightMainNav = new RightNav("", controllerActionListener, inputContainer, this);
    rightMainNav.setBorder(BorderFactory.createTitledBorder("Options"));
    leftMainNav.setPreferredSize(new Dimension((getWidth() / 4), 0));
    rightMainNav.setPreferredSize(new Dimension((getWidth() / 4), 0));

    masterPanel.add(leftNav, createGBC(0, GridBagConstraints.VERTICAL, 0));
    masterPanel.add(scrollPane, createGBC(1, GridBagConstraints.BOTH, 1));
    masterPanel.add(rightMainNav, createGBC(2, GridBagConstraints.VERTICAL, 0));


    add(masterPanel);


    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }

  private static GridBagConstraints createGBC(int x, int fill, int weighty) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = x;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.fill = fill;
    gbc.weightx = weighty; // Horizontal weight
    gbc.weighty = weighty; // Vertical weight
    gbc.insets = new Insets(5, 5, 5, 5); // Padding
    return gbc;
  }

  /**
   * Creates an input form so that user can enter an input for the create portfolio option.
   *
   * @return the input form object.
   */
  private InputForm createPortfolioForm() {
    InputForm ticker = new InputForm("createPortfolio", masterPanel,
            controllerActionListener, "Get portfolio value");

    JLabel enterName = new JLabel("Enter the name of the Portfolio");
    JTextField name = new JTextField();

    inputContainer.setText("createPortfolioName", name);

    return ticker
            .addLabel(enterName)
            .addTextField(name);

  }

  /**
   * Creates an input form for the dollar cost average option.
   */
  private void costAvgForm() {
    form = new JPanel();
    stockList = new ArrayList<>();
    stockList.add(listOption(0));

    JPanel name = new JPanel(new GridLayout(2, 0, 2, 2));
    form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
    JLabel portfolioNameLabel = new JLabel("Portfolio Name");
    JTextField portfolioName = new JTextField();
    name.add(portfolioNameLabel);
    name.add(portfolioName);
    inputContainer.setText("costAvgPortfolioName", portfolioName);

    JPanel amount = new JPanel(new GridLayout(2, 0, 2, 2));

    JLabel totalAmountLabel = new JLabel("Total Amount");
    JTextField totalAmount = new JTextField();
    amount.add(totalAmountLabel);
    amount.add(totalAmount);
    inputContainer.setText("costAvgTotalAmount", totalAmount);

    JPanel factorType = new JPanel(new GridLayout(2, 0, 2, 2));

    JLabel factorTypeLabel = new JLabel("Period");

    String[] options = {"Day(s)", "Month(s)", "Year(s)"};
    JComboBox<String> comboBox = new JComboBox<String>();
    inputContainer.setComboBox("costAvgFactorType", comboBox);


    for (int i = 0; i < options.length; i++) {
      comboBox.addItem(options[i]);
    }
    factorType.add(factorTypeLabel);
    factorType.add(comboBox);


    JPanel frequencyPanel = new JPanel(new GridLayout(2, 0, 2, 2));
    JLabel frequencyLabel = new JLabel("Frequency");
    JTextField frequency = new JTextField();
    inputContainer.setText("costAvgFrequency", frequency);
    frequencyPanel.add(frequencyLabel);
    frequencyPanel.add(frequency);


    JPanel topPanel = new JPanel(new GridLayout(1, 0, 5, 5));
    topPanel.add(name);
    topPanel.add(amount);
    topPanel.add(factorType);
    topPanel.add(frequencyPanel);
    form.add(topPanel);

    JPanel datesPanel = new JPanel(new GridLayout(1, 0, 5, 2));
    JLabel startDate = new JLabel("startDate", SwingConstants.RIGHT);
    JDateChooser startDateValue = new JDateChooser();
    startDateValue.setMaxSelectableDate(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));
    startDateValue.getDateEditor().setEnabled(false);
    inputContainer.setDate("costAvgStartDate", startDateValue);

    JPanel endDatePanel = new JPanel(new BorderLayout());
    JLabel endDate = new JLabel("endDate", SwingConstants.RIGHT);
    JDateChooser endDateValue = new JDateChooser();
    endDateValue.getDateEditor().setEnabled(false);
    JLabel notice = new JLabel("Leave blank if ongoing");
    Font newLabelFont = new Font(notice.getFont().getName(), Font.ITALIC,
            (int) (notice.getFont().getSize() * 0.9));
    notice.setFont(newLabelFont);
    endDatePanel.add(endDate, BorderLayout.CENTER);
    endDatePanel.add(notice, BorderLayout.SOUTH);
    inputContainer.setDate("costAvgEndDate", endDateValue);
    datesPanel.add(startDate);
    datesPanel.add(startDateValue);
    datesPanel.add(endDatePanel);
    datesPanel.add(endDateValue);

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
    scrollPane.setPreferredSize(new Dimension(500, 240));
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    int option = JOptionPane.showConfirmDialog(this, scrollPane,
            "Create cost average portfolio", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE);
    if (option == JOptionPane.OK_OPTION) {
      controllerActionListener.actionPerformed(new ActionEvent(controllerActionListener,
              ActionEvent.ACTION_PERFORMED, "costAvgPortfolio"));
    }
  }

  /**
   * This method adds a dynamic list so that user can add multiple inputs.
   *
   * @param size is length of the option.
   * @return a JPanel object.
   */
  private JPanel listOption(int size) {
    listOption = new JPanel(new GridLayout(1, 0, 3, 0));
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
    inputContainer.setText("costAvgName" + (size), stockName);
    inputContainer.setText("costAvgQuantity" + (size), quantity);
    inputContainer.setText("costAvgSize", new JTextField(String.valueOf(size + 1)));
    return listOption;
  }

  /**
   * Invoked when an action occurs with the main view as the listener.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "portfolioOptions":
        this.rightMainNav.removeAll();
        this.rightMainNav.add(new RightNav("portfolioOptions", controllerActionListener,
                inputContainer, this));
        break;
      case "stockAnalysis":
        this.rightMainNav.removeAll();
        this.rightMainNav.add(new RightNav("stockAnalysis", controllerActionListener,
                inputContainer, this));
        break;
      case "loadPortfolio":
        this.controllerActionListener.actionPerformed(new ActionEvent(this,
                ActionEvent.ACTION_PERFORMED, "listPortfolio"));
        this.rightMainNav.removeAll();
        this.rightMainNav.add(new RightNav("loadPortfolio", controllerActionListener,
                inputContainer, this));
        break;
      case "clearFields":
        inputContainer.clearFields();
        break;
      case "createPortfolio":
        createPortfolioForm().show();
        break;
      case "resetDisplayNoStock":
        displayPanel.removeAll();
        dispP = new DisplayPanel("Wow such empty! Try buying a stock using " +
                "the option on the right.");
        displayPanel.add(dispP);
        displayPanel.revalidate();
        displayPanel.repaint();
        break;
      case "costAvgPortfolio":
        costAvgForm();
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
          inputContainer.setText("costAvgSize",
                  new JTextField(String.valueOf(stockList.size())));
          form.revalidate();
          form.repaint();
        }
        break;
      default:
    }
    rightMainNav.revalidate();
    rightMainNav.repaint();
  }

  /**
   * set the action listener so that the controller can listen to view actions.
   *
   * @param listener controller Action listener object
   */
  @Override
  public void setActionListener(ActionListener listener) {
    this.controllerActionListener = listener;
  }

  @Override
  public void showList(String portfolioList) {
    StringBuilder result = new StringBuilder();
    String[] list = portfolioList.split(",");
    for (int i = 1; i <= list.length; i++) {
      result.append(i).append(". ").append(list[i - 1]).append("\n");
    }
    displayPanel.removeAll();
    JPanel newContent = new DisplayPanel(result.toString());
    displayPanel.add(newContent);
    masterPanel.revalidate();
    masterPanel.repaint();
  }

  /**
   * Displays the composition of the portfolio is proper column format.
   *
   * @param stockDetails is the composition present in the portfolio.
   */
  @Override
  public void showPortfolioComposition(String[] stockDetails) {
    StringBuilder result = new StringBuilder(
            String.format("%s\t%s\t%s\t%s\t%s\n", "Ticker", "Quantity",
                    "Price", "Date", "Name"));
    for (String stockDetail : stockDetails) {
      String[] data = stockDetail.split(",");
      result.append(String.format("%s\t%s\t%s\t%s\t%s\n", data[0], data[2], data[3],
              data[4], data[1]));
    }
    displayPanel.removeAll();
    JPanel newContent = new DisplayPanel(result.toString());
    displayPanel.add(newContent);
    masterPanel.revalidate();
    masterPanel.repaint();
  }

  /**
   * Used by controller to set the output value to a display panel in the UI.
   *
   * @param content content that has to be displayed
   */
  @Override
  public void showMsg(String content) {

    displayPanel.removeAll();
    JPanel newContent = new DisplayPanel(content);
    displayPanel.add(newContent);
    masterPanel.revalidate();
    masterPanel.repaint();
  }

  @Override
  public void showErrorMsg(String s) {
    JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showSuccessMsg(String s) {
    JOptionPane.showMessageDialog(this, s, "Success",
            JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void fetchDetailsMsg() {
    return;
  }

  /**
   * Used by controller to retrieve the inputs for the necessary action in the UI.
   *
   * @param inputType the action type for which the input is retrieved
   * @return
   */
  @Override
  public String getInput(String inputType) {
    try {
      Function<Void, String> inputs = inputMapper.get(inputType);
      if (inputs == null) {
        return null;
      }
      return inputs.apply(null);
    } catch (DateTimeParseException e) {
      this.showErrorMsg("Date error");
      return null;
    }
  }


  /**
   * Used by controller to call a certain action of the view Listener if required.
   *
   * @param action action string available in the view.
   */
  @Override
  public void takeAction(String action) {
    this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, action));
  }
}
