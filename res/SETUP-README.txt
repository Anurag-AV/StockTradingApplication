To run our jar file in the command prompt follow the following steps:
1. Open the command-prompt/terminal.
2. Change the directory and go into the folder where the jar file is present.
3. Now type "java -jar StockTrading.jar" without the quotes to run through the Graphical User Interface.
4. To run using Command Line Interface type "java -jar StockTrading.jar cli"
Note: You should store the jar file in a folder as the program creates local directories.

Requirements:
1. If you are launching the program for the first time and have an active internet connection, then the jar file is sufficient to run it.

*************Note*************

1. The manually created portfolios should always be in CSV format.
2. These should be saved in a "portfolio" directory which should be present at the same level of jar file.
3. If you are launching the application for the first time and want to load a manual portfolio, then you are required to create the "portfolio" directory and save portfolio in it.
4. The files present in the "cache" and "transactions" directory should not be tempered with. Though these files update themselves regularly, it may result in incorrect program execution at times.
5. An example of the portfolio file(name P1.csv) is as follows:
	Ticker Symbol,Company Name,Quantity,Purchase Price,Purchase Date
	MSFT,Microsoft Corporation,10,404.52,2024-03-11
	GOOG,Alphabet Inc - Class C,10,138.94,2024-03-11
6. Once a portfolio is saved, it's historical data gets stored in the cache and can be referred to any date irrespective of internet connection.

***************Limitations***************
1. We only restrict the user in terms of date. The user cannot enter today's date or any date in future to get the value of the portfolio on that specific date(Exception to this will be a long term strategy where the user can specify a future or no end date).

***************Dependencies***************
1. The program depends on the AlphaVantage API. Any changes made by AlphaVantage in terms of API call structure or return values will affect the program.
2. The program does not work with today's date or any date coming up in future(Except for a long term investment strategy). It will not crash if such a date is entered.
3. Stocks once added can be used in the future even without an internet connection, but adding a new stock will always require an internet connection.