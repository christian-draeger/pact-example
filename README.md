#Pact Example

This is an example project to demonstrate Contract Testing via Pact. 
The Example includes two applications where one is acting as a producer 
(webservice with rest endpoint) and a consumer 
(a CLI app that is hardly coupled on the producer).

Both of the applications (producer and consumer) are testing there-self.
The Consumer-Apps dependency - having the Producer-App available, 
a working internet connection and getting a suitable response - can be decoupled by
mocking (in this example via WireMock) to get possibility to run and test the Consumer
without all the given requisites and locally. Great!!! so far so good.

#####but how to make sure the Producers response is in a Suitable format for the Consumer?

##Let's make a Pact

