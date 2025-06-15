The following features of our program are complete and correctly working:

1. The program allows the user to create one or more portfolios by setting portfolio name and adding one or more stocks to it.
2. The user can add stocks to a portfolio either by ticker symbol or by company name.
3. The user can load a previously created portfolio into the program.
4. The loaded portfolio can either be created using our program or manually by using a text editor following the csv format.
5. All the values of the loaded portfolio are checked along with file checking to see if there are any missing or extra values.
6. Our program restricts the user from entering stock quantities in fraction.
7. Our program does not allow the user to save or load a portfolio which has no stocks.
8. The program successfully displays the portfolio value rounded to two decimal places if the portfolio is loaded, for a given date.
9. The program successfully handles the scenarios if the date for get portfolio value is not valid.
10. The program is not entirely dependent on internet connection, rather it uses caching to store historical values and displays the latest values if API calls are exceeded or internet is not available.
11. The program successfully displays the portfolio composition of a portfolio with proper formatting in columns.
12. The program successfully displays a list of all the available/loadable portfolios.
13. After creating, the user can successfully save the portfolio. Once saved, stocks cannot be added or deleted in the portfolio.
14. The saving and loading of portfolio is done using Comma Separated Value (CSV) files making them human readable.
15. We are successfully using AlphaVantage API in our program where we have handled the limited queries per day using cache.
16. If operations like show portfolio value or show portfolio composition are selected without a loaded portfolio, then appropriate messages are displayed.
17. Loading messages and success messages are shown accordingly for each feature.
18. Our program uses an interactive text based interface that allows the user to use all of the above features.
19. Our program successfully implements the model view controller Architecture to implement all the above feature.
20. All the features are implemented using only JDK classes and no external classes.
21. Our program allows the user to purchase a specific number of shares of a specific stock on a specific date and add them to portfolio while preserving the previous functionality of buying a stock without date input.
22. Our program allows the user to sell a specific stock on a specified date from a given portfolio.
23. The program successfully displays the cost basis i.e. the total amount of money invested in a portfolio by a specific date.
24. The program also displays the value of a portfolio on a specific date such that the value before the first purchase would be 0.
25. Our program successfully displays whether a particular stock has gained or lost on a given day.
26. Our program successfully dispalys whether a particular stock has gained or lost value over a given period of time.
27. Our program successfully displays the X day moving average for a given stock on a given day.
28. The program successfully displays the positive and negative crossovers that have occurred over a specified period of time for a given stock.
29. Our program successfully displays the positive and negative moving crossovers over a specified period of time for a given stock.
30. Our program successfully displays the bar chart for a given stock as well as a portfolio over a given period of time using the text based interface.
31. The bar chart supports both relative as well as absolute scaling depending on the stock values for given time period.  
Assignment 6 Features-
32. Our program allows the user to invest a fixed amount into an existing portfolio containing multiple stocks, using a specified weight for each stock.
33. Our program offers creating a start-to-finish dollar-cost averaging to the user.
34. Our program supports an ongoing dollar cost averaging strategy.
35. Our program handles executing the dollar cost averaging and other long term investments at on a holiday where it chooses the next available day to invest.
36. Along with dollar cost averaging, our design supports adding other high-level time based investment strategies which can be created by implementing an interface.
37. We have added a new view to our graphical user interface using java swing.
38. Our program supports both text based view as well as graphical user interface.
39. Our graphical user interface allows the user to create a new flexible portfolio.
40. Our GUI supports the ability to buy/sell stocks by specifying the number of shares and date.
41. Our GUI supports the ability to query the cost basis and value of a flexible portfolio at a certain date.
42. The user can save and retrieve flexible portfolios from files using the GUI.
43. The user can query all the statistics about a stock.
44. The GUI enables the user to invest a specific amount in an existing portfolio on a specific date by specifying the weights of stocks.
45. The user can create a portfolio using a dollar cost investment strategy using the gui.
46. The user can query cost basis and value of such a portfolio on a specific date.
47. The GUI is user friendly and self explanatory.