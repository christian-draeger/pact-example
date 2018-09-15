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

##### but how to make sure the Producers (supplier) response is in a Suitable format for the Consumer?

## Let's make a Pact

> A formal agreement between individuals or parties.
Synonyms: agreement, protocol, deal, contract 
>>~ Oxford Dictionaries​

### intro to Consumer Driven Contract Testing

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

In general, a contract is between a consumer (for example, a client that wants 
to receive some data) and a provider (for example, an API on a server that 
provides the data the client needs). In microservice architectures, 
the traditional terms client and server are not always appropriate -- for example, 
when communication is achieved through message queues.

### intro to Pact

[Pact](https://docs.pact.io) is a consumer-driven contract testing tool. 
This means the contract is written as part of the consumer tests. 
A major advantage of this pattern is that only parts of the communication 
that are actually used by the consumer(s) get tested. 
This in turn means that any provider behaviour not used by current consumers 
is free to change without breaking tests.