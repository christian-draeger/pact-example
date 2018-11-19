Example Project
===============

Playing around with the Example Project
---------------------------------------

### Run all tests

* this will start and stop the Pact Broker automatically during test run
* execute all tests (including pact verification and result publishing)

	mvn clean verify
	
if you don't want the broker to shutdown run:
* this will give you the opportunity to play around with the [Pact-Broker UI](http://localhost)


	mvn clean verify -P-broker-down

### The Broker

#### start Pact-Broker manually

	docker compose-up
	
#### stop Pact-Broker manually

	docker compose-down