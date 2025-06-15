The changes made in this assignment are as follows:

1. We have added a new interface named LongTermInvestmentStrategies which will allow us to create any long term investment strategy.
2. We have added two classes in the commands package. One which creates portfolio using a dollar cost average and the other to invest a fixed amount in an existing portfolio by specifying stock weights.
3. As we are implementing long term investment strategies, portfolio using these strategies need to be updated when loaded. So we have added additional functionalities in the LoadPortfolio class of the command package.
4. We have made additions to the TransactionsManager class to store long term investment strategies into our logs.
5. We have implemented a new controller for the graphical user interface.
6. We have abstracted all the common functionalities of the controller used for command line interface as well as for graphical user interface.
7. Earlier all classes of our commands package took user input using Readable. This created a problem as the new GUI controller does not use such inputs. So we have abstracted out all Readable input related functionalities into the command line controller so that the classes in our commands package can be reused by every controller.
8. We have implemented a new view that supports graphical user interface.
9. This new view implements the MainGUIViewInterface interface.
10. All the common functionalities of the text based view and the GUI based view have been abstracted to an AbstractView. The MVCMain uses the abstract view object.
11. All the GUI based classes and interfaces have been added to the gui package in the view.



We are using model-view-controller(MCV) design pattern for our application. The main class is calling the controller which marks the launch of our program. So the design consists of three parts:

1. model
    
    Our model contains all the entities in the system and performs all the necessary computations on their data.
    The model class is represented by an interface.
    The interface functions are the only functions with which the controller could communicate with the model
    It neither performs File IO operations nor input-output operations.
    The controller interacts with a model interface which in turn branches to other package private classes according to the functionalities needed.
    The entire API functionality is separated from other parts of the model so that further integrations related to new APIs can be easily made.
    We are representing stock and portfolio entities using classes implementing the respective interfaces which are package private within the model.
    The class which pings the API is also package private and can be only accessed using an interface object which consists the URL of the API.
    The model class interacts with the class representing portfolio which in turn interacts with class representing the stocks.
    All the statistical analysis operations that can be performed on a stock have been added into a new interface (StockOperationInterface) which extends the StockInterface. 
    The class which handles the responsibility of generating bar graphs is package private within the model and passes the generated bar graph to controller which in turn prompts the view to display it. This class can handle both a stock or a portfolio bar graph generation.
    
2. view
    
    Our view handles all the output operations.
    The view class is represented by an interface.
    The interface functions are the only functions with which the controller would communicate with the view
    It's sole purpose is to display prompts and data to the user if prompted by the controller.
    The implementation of the view interface consists of various methods which display different menus and messages to the user.
    These messages vary from feature to feature. The view is also capable to show the exception messages which arise is our program so that the application does not crash.
    Each unique message is displayed using a method that takes the message as input.
    We haveimplemented both a command line view as well as a graphical user interface.
    Both these views are represented by an abstract interface. This allows us to support multiple number of views.
    
3. controller
    
    The controller controls the entire flow of the program.
    The controller class is represented by an interface.
    The controller is responsible to take inputs from the user.
    Based on the user input, the controller delegates the flow to model so that some data can be processed or to the view to display data and messages.
    The controller is the only one responsible to handle local file input/output.
    Controller consists of a class that interacts with the main model class. There is a separate class to interact with the model API class. In this way, API related functionalities are isolated within the controller as well as model.
    The controller also handles and updates the cache of the program. If the API calls are exhausted, then it is the responsibility of the controller to provide data from cache.
    The controller also updates the cache if API calls are available. 
    To get historical data, there are no need for API calls in our program.
    The controller also keeps track of changes made to a portfolio i.e. buying and selling of stocks by using a logger which is stored as a file.
    The entire file input/output is segregated into a class.
    The entire caching related operations are segregated in another class.
    The methods of all these classes are package private.
    Each feature that a user can use in our program is represented by a unique class implementing a single interface. All these classes are handled by the main controller.   
    We have implemented two controller classes one for graphical user interface and the other for command line interface. Both these controllers implement the same interface and use the same command classes.  