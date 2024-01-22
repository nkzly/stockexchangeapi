# stockexchangeapi

There are many stock exchanges and many stocks in the world.

# Stock exchange properties:- id(Number), name(String), description(String) , liveInMarket(boolean)

# Stock properties:- id (Number), name (String), description(String), currentPrice (Amount) and lastUpdate (Timestamp).

Each Stock exchange can have many stocks Stock exchangehaving less than 5 stocks can not be live in the market ie liveInMarket is false. The particular stock can be listed in many stock exchanges and all the properties of a stock will remain same in all the exchanges.

Application contain the below end points:

List one stockExchange with all the stocks( api/v1/stock-exchange/{name} )
Able to create a stock ( api/ v1/stock )
Able to add the stock to the stock exchange(api/ v1/stock-exchange/{name} )
Able to delete the stock from the stock exchange(api/ v1/stock-exchange/{name} )
Update the price of a stock(api/stock )
Able to the delete the stock from the system (api/ v1/stock)
