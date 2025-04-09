# Shopping Cart System


## Requirements Completed

### Part 1: Java Core

- [x] Java Backend with core business logic and pricing
- [x] Unit tests for core business logic

### Part 2: JavaScript API Layer

- [x] API to add items to the cart
- [x] API to view the cart
- [x] API to calculate the total
- [x] Communication with Java Backend over REST (http)
- [x] Error Handling and Response formatting
- [x] Added JWT authentication to secure the APIs 

### Part 3: Extension

- [ ] Real Time Synchronization for items added to the cart when Java Backend is down (partially implemented)


## Getting Started

### 1. shopping-cart-backend (Java Backend)

1. Navigate to `shopping-cart-backend` folder
2. Run the spring boot application using maven wrapper with the following command
	
	**On Linux/macOS:**
	```shell
	./mvnw spring-boot:run
	```
	
	**On Windows:**
	```shell
	mvnw.cmd spring-boot:run
	```
3. You should see the spring boot application start in the terminal.
4. You can go to localhost:8080 on your browser to confirm that the application has started.


### 2. shopping-cart-api (Javascript API Layer)

1. Navigate to `shopping-cart-api` folder
2. Run `npm install` to install all node_modules 
3. Once the node_modules are installed, populate `.env` at the root of `shopping-cart-api` with the following fields

```.dotenv
SECRET_KEY=<Secret-key-for-JWT>
PORT=<Port for the Javascript layer>
```
4. Run `npm start` to start the express server
5. Please go to `localhost:<port-in-.env>` or `localhost:3000` to veify that the server is running


### Assumptions for the two systems

1. Assumed the Java backend to be the single source of truth and used an in-memory map to store the cart items. 
2. The JavaScript server acts as an API Layer/proxy to relay the cart items to the backend service and is stateless.
3. Since the Java backend service is considered the single source of truth, there is loss of cart items. Need persistence using Database to support retention of state. 
4. The Javascript layer requires JWT authentication to access the shopping cart APIs. Details are mentioned under API Endpoints. 



## API Endpoints 

### 1. shopping-cart-backend (Java Backend)

**BASE_URL = localhost:8080/**

1. `GET /` - Home Page with the message `Shopping Cart Backend is up and running`
2. `POST /api/cart/items` - Add items to the cart. The POST request takes in a request body as shown below
	```json
	{ "items" : ["Apple", "Banana", "Melon", "Melon"] }
	```
3. `GET /api/cart/items` - Gets the items in a cart with the count. Sample output:
	```json
   { "items": { "Lime": 4, "Apple": 6, "Banana": 4, "Melon": 6 } }
	```
4. `GET /api/cart/total` - Gets the total price along with the cart items
	```json
	{ "items": { "Lime": 2, "Apple": 3, "Banana": 2, "Melon": 3 }, "totalPrice": 2.75 }
	 ```
 
### 2. shopping-cart-api (JavaScript API Layer)

**BASE_URL = localhost:3000/**

1. `/` - Home Page with the message `Shopping Cart API Layer Home Page`
2. `/api/auth/signup` - Adds the user to an in-memory array to maintain signed-up users. The body is as follows
	```json
   { "username": "<name>" }
 	```
3. `/api/auth/sigin` - Signs the user in only if they are registered and sends a JWT token expected in every non auth endpoints.
	```json
	{ "username": "<name>" }
 	```
 	```json
   { "message": "User signed in successfully", "token": "<jwt-token>" }
  	```
  	The user is first expected to sign up and then sign in to get the jwt token. The jwt token must be present in the authorization header of each cart request
 
4. `/api/cart/add` - Adds the items to the cart by calling the Java Backend. Same request body. 
5. `/api/cart/view` - Gets the items in a cart with the count from the Java Backend.
6. `/api/cart/total` - Gets the total price along with the cart items from the Java Backend.



## Real-time synchronization

One of the assumptions of the two systems is that the Java Backend acts as the single source of truth. Any downtime of the Backend Service leads to loss of cart items.  
In a real world scenario, the Java Backend would be using a persistent database to store the cart items thereby retaining state even after downtime.  
The JavaScript layer only relays the request from the user to the Java backend. In case, there is downtime of the Java Backend Service, I have implemented a retry cart cache which maintains the list of items to be added to the cart.  
Every time, a new request to add the items is received the pending items along with the new items are retried. At the point the cache is invalidated. If the request with the pending items and the new items fails again, the cache is again updated with the items list.  
There is also a setInterval function running at an interval to clear the cache and add the items to the cart instead of waiting for another request. 

### Caveats and how they can be fixed
1. If the API Layer goes down, we lose the inmemory cache just like the Backend Server. 
2. Downtime of any service leads to loss of data, java backend - actual cart items, API Layer - cached cart items. 
3. In order to solve this in a real world scenario, a database layer for storing the data of the cart items in the java backend and a persistent cache like redis in the API Layer can be added to prevent any data loss.
4. This will ensure that the systems eventually would sync and maitain the same state.


