# stockexchangeapi
# Backend assessment

There are many stock exchanges and many stocks in the world.

# Stock exchange properties:- id(Number), name(String), description(String) , liveInMarket(boolean)

# Stock properties:- id (Number), name (String), description(String), currentPrice (Amount) and lastUpdate (Timestamp).

Rules:

Each Stock exchange can have many stocks Stock exchangehaving less than 5 stocks can not be live in the market ie liveInMarket is false. The particular stock can be listed in many stock exchanges and all the properties of a stock will remain same in all the exchanges.

Application should contain the below end points:

List one stockExchange with all the stocks( api/v1/stock-exchange/{name} )
Able to create a stock ( api/ v1/stock )
Able to add the stock to the stock exchange(api/ v1/stock-exchange/{name} )
Able to delete the stock from the stock exchange(api/ v1/stock-exchange/{name} )
Update the price of a stock(api/stock )
Able to the delete the stock from the system (api/ v1/stock)
Implementation

We would like you to create a java based backend application using framework Spring which manages the stock exchange application All the endpoints should be only be available to authorized user(This is not an absolute requirement but good to have) Application can us a memory database like h2. Multiple users of the system can use the system simultanously. Treat this application as a real MVP that should go to production. Make sure to include an explanation on how to run and build your solution, and provide initialization resources (e.g. scripts,...) as well.



Duration

This assignment should take 4-5 hours at the most but you can spend as much time as you want on it. Just don’t over engineer it 😝



What we will be looking for

We want you to express your best design and coding skills. We will be looking for quality code and best practices. It is enough if you walk us through your code with screensharing. You don’t have to push your code to any repo.

Good luck!