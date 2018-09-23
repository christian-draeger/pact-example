# Pact Example

This is an example project to demonstrate **Consumer Driven Contract Testing** via Pact. 
The Example includes two applications where one is acting as a producer 
(webservice with rest endpoint) and a consumer 
(a CLI app that is hardly coupled on the producer).

Both of the applications (producer and consumer) are testing there-self.
The Consumer-Apps dependencies (having the Producer-App available, 
a working internet connection and getting a suitable response) can be detached by
mocking (in this example via WireMock) to run locally and independent. 
Great!!! so far so good.

##### But how to make sure the Producers (supplier) response is in a Suitable format for the Consumer?

## Let's make a Pact

> A formal agreement between individuals or parties.
Synonyms: agreement, protocol, deal, contract 
>>~ Oxford Dictionaries​

### Intro to Consumer Driven Contract Testing

The [concept](https://www.martinfowler.com/articles/consumerDrivenContracts.html) isn’t new, but with the mainstream acceptance of microservices, 
it's important to remind people that consumer-driven contracts are an essential 
part of a mature microservice testing portfolio, enabling independent 
service deployments.

When two independently developed services are collaborating, 
changes to the supplier’s API can cause failures for all its consumers. 
Consuming services usually cannot test against live suppliers since such 
tests are slow and brittle, so it’s best to use Test Doubles, 
leading to the danger that the test doubles get out of sync with the real 
supplier service. Consumer teams can protect themselves from these failures 
by using integration contract tests – tests that compare actual service 
responses with test values. While such contract tests are valuable, 
they are even more useful when consuming services provide these tests to 
the supplier, who can then run all their consumers’ contract tests to determine 
if their changes are likely to cause problems.

Contract testing is immediately applicable anywhere where you have two 
services that need to communicate - such as an API client and a web front-end. 
Although a single client and a single service is a common use case, 
contract testing really shines in an environment with many services 
(as is common for a microservice architecture). 
Having well-formed contract tests makes it easy for developers to avoid 
version hell. Contract testing is the killer app for microservice development and deployment.

In general, a contract is between a consumer (for instance a client that wants 
to receive some data) and a provider (for instance an API on a server that 
provides the data the client needs). In microservice architectures, 
the traditional terms client and server are not always appropriate -- for example, 
when communication is achieved through message queues.

### Intro to Pact

[Pact](https://docs.pact.io) is a consumer-driven contract testing tool. 
This means the contract is written as part of the consumer tests. 
A major advantage of this pattern is that only parts of the communication 
that are actually used by the consumer(s) get tested. 
This in turn means that any provider behaviour not used by current consumers 
is free to change without breaking tests.

## Defining a pact
Defining a Pact should be splitted into 3 steps:
* Define
* Test
* Publish 

### Define
We'll start defining our Pact at the **Consumer** Application. 
I mean hey, we want to work Consumer Driven and who could know its 
requirements regarding a producer API better then the Consumer itself?

#### prerequisites on consumer side
First let's add the relevant **Pact** dependency for our use-case to the consumer applications *pom.xml*
``` xml
<dependency>
	<groupId>au.com.dius</groupId>
	<artifactId>pact-jvm-consumer-java8_2.12</artifactId>
	<version>3.5.21</version>
	<scope>test</scope>
</dependency>
```

Now we are able to define how the **Producer** APIs response needs to look like from the **Consumers** point of view.
We'll begin by creating a test class named `ContractTest` that implements `ConsumerPactTestMk2`.

You'll need to implement `providerName()`, `consumerName()`, `createPact()` and `runTest()`.

The implementation of the `providerName()` method should return a string that describes the name of the provider API.
Since our **Provider** is responsible for user data we should call it something like "user-data-provider":

> using kotlin
>``` kotlin
>override fun providerName(): String = "user-data-provider"
>```

> using java
>``` java
>@Override
>protected String providerName() {
>	return "user-data-provider";
>}
>```

The implementation of the `consumerName()` method should return a string that describes the name of the consuming service.
Since our **Consumer** is an cli-tool that displays user data we should call it something like "user-data-cli":

> using kotlin
>``` kotlin
>override fun consumerName(): String = "user-data-cli"
>```

> using java
>``` java
>@Override
>protected String consumerName() {
>	return "user-data-cli";
>}
>```

Now let's define how a request from the **Consumer** looks like and what's the 
expected format of the payload by implementing the `createPact()` method.
We are using the `PactDslWithProvider` builder to describe the request 
and (because we are expecting a response with a JSON body)
the `PactDslJsonBody` builder to define the payload:

> using kotlin:
>``` kotlin
>override fun createPact(builder: PactDslWithProvider): RequestResponsePact {
>
>	val body = PactDslJsonBody()
>			.stringType("firstName")
>			.stringType("lastName")
>			.numberType("age")
>			.`object`("ids", PactDslJsonBody()
>					.integerType("id")
>					.uuid("uuid"))
>
>	return builder.uponReceiving("can get user data from user data provider")
>			.path("/user")
>			.willRespondWith()
>			.status(200)
>			.body(body)
>			.toPact()
>}
>```

> using java:
>``` java
>@Override
>protected RequestResponsePact createPact(PactDslWithProvider builder) {
>	PactDslJsonBody body = new PactDslJsonBody()
>			.stringType("firstName")
>			.stringType("lastName")
>			.numberType("age")
>			.object("ids", new PactDslJsonBody()
>				.integerType("id")
>				.uuid("uuid")
>			);
>
>	return builder.uponReceiving("can get user data from user data provider")
>			.path("/user")
>			.method("GET")
>			.willRespondWith()
>			.status(200)
>			.body(body)
>			.toPact();
>}
>```

### Test
Last but not least we should define our client side test based on the defined request we
described in the step before. So let our *UserClient* (that is talking to the **Provider**)
call a mockServer (that is created for us by **Pact**) as we defined it in our `createPact()` implementation.  

>using kotlin:
>``` kotlin
>override fun runTest(mockServer: MockServer) {
>   val expectedKeys = listOf("firstName", "lastName", "ids", "age")
>   val result = UserClient("${mockServer.getUrl()}/user").callProducer()
>   assertThat(result.keys).containsAll(expectedKeys)
>}
>```

>using java:
>``` java
>@Override
>protected void runTest(MockServer mockServer) {
>    List<String> expectedKeys = Arrays.asList("firstName", "lastName", "ids", "age");
>    Map<String, String> result = new UserClient(mockServer.getUrl() + "/user").callProducer();
>    assertThat(result.keySet()).containsAll(expectedKeys);
>}
>```

> ##### So your test class should look something like [THIS](consumer/src/test/kotlin/com/example/demo/ContractTest.kt) if you are using Kotlin afterwards.
> ##### So your test class should look something like [THIS](consumer/src/test/kotlin/com/example/demo/JavaContractTest.java) if you are using Java afterwards.

At this point we already archived a lot. We verifying our *UserClient* is working correctly and
we created the contract definition - or better said, Pact generated one for us :) - our **Provider** will validate his Api against later on.
You can have look at it under `/target/pacts/user-data-cli-user-data-provider.json` (it should look like [THIS](consumer/src/test/resources/example-pact.json) one).

### Publish

dfd
 
----------------

### Extra infos on Pact
#### Terminology

##### Service Consumer
A component that initiates a HTTP request to another component 
(the service provider). Note that this does not depend on the way the 
data flows - whether it is a `GET` or a `PUT` / `POST` / `PATCH` / `DELETE`, the consumer is the initiator of the HTTP request.

##### Service Provider
A server that responds to an HTTP request from another component 
(the service consumer). A service provider may have one or more HTTP endpoints, 
and should be thought of as the "deployable unit" - endpoints that get 
deployed together should be considered part of the same provider.

##### Mock Service Provider
Used by tests in the consumer project to mock out the actual service provider, 
meaning that integration-like tests can be run without requiring the actual 
service provider to be available.

##### Interaction
A request and response pair. A pact consists of a collection of interactions.

##### Pact file
A file containing the JSON serialised interactions (requests and responses) 
that were defined in the consumer tests. This is the Contract. A Pact defines:

* the consumer name
* the provider name
* a collection of interactions
* the pact specification version (see below)

##### Pact verification
To verify a Pact contract, the requests contained in a Pact file are replayed 
against the provider code, and the responses returned are checked to ensure 
they match those expected in the Pact file.

##### Provider state
A name describing a “state” (like a fixture) that the provider should be 
in when a given request is replayed against it - e.g. “when user John Doe 
exists” or “when user John Doe has a bank account”. These allow the same 
endpoint to be tested under different scenarios.

A provider state name is specified when writing the consumer specs, then, 
when the pact verification is set up in the provider the same name will be 
used to identify the set up code block that should be run before the request 
is executed.

##### Pact Specification
The Pact Specification is a document that governs the structure of the actual 
generated Pact files to allow for interoperability between languages 
(consider, for example, a JavaScript consumer connecting to a Scala JVM-based 
provider) , using semantic versioning to indicate breaking changes.

Each language implementation of Pact needs to implement the rules of this 
specification, and advertise which version(s) are supported, corresponding 
closely to which features are available.
