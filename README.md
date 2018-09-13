Reactive Programming
====================


### Glossary

* reactive: 响应的，响应式的
* stream: 流式的
* asynchronous: 异步的
* non-blocking: 非阻塞的
* Observable 可观测的
* Single: 单个的
* Flux: 流量
* Mono: 单一

### Reactive Operation

* Creation
* Combine
* transform(map)
* Filter
* Mathematical and Aggregate Operators
* Utility Operators
* Conditional and Boolean Operators
* Error Handling

##### reactive stream

* Publisher: A Publisher is a provider of a potentially unbounded number of sequenced elements, publishing them according to the demand received from its Subscriber(s).
* Subscriber: Receive call to onSubscribe(Subscription) once after passing an instance of Subscriber to Publisher.subscribe(Subscriber).
* Subscription: A Subscription represents a one-to-one lifecycle of a Subscriber subscribing to a Publisher, use subscription.request() method to request items.
* Processor:  Processor represents a processing stage—which is both a Subscriber and a Publisher and obeys the contracts of both

### Java 9

* Flow:  Interrelated interfaces and static methods for establishing flow-controlled components

##### RxJava

* Observable: This class provides methods for subscribing to the Observable as well as delegate methods to the various Observers.
* Single: Reactive Pattern for a single value response
* Observer: Provides a mechanism for receiving push-based notifications
* Subscriber: Provides a mechanism for receiving push-based notifications from Observables, and permits manual unsubscribing from these Observables.

##### Reactor

* Flux: A Reactive Streams {@link Publisher} with rx operators that emits 0 to N elements, and then completes  (successfully or with an error).
* Mono: A Reactive Streams {@link Publisher} with basic rx operators that completes successfully by emitting an element, or with an error.
* FluxSink: next/error/complete sink to push data to flux

### RxJava VS Reactor

* RxJava诞生早，使用广泛，尤其在Netflix产品中
* Reactor和Spring整合密切
* 在Java 8支持方面，Reactor基于Java 8，而RxJava是自己的API
* RxJava的模型在其他语言都有实现，如果你用多语言的场景化，RxJava的模型更好一些，如Angular等

### Kotlin Extension

* https://github.com/ReactiveX/RxKotlin
* https://github.com/reactor/reactor-kotlin-extensions

### RSocket
Please start RSocketServer then execute following command for testing:
```
rsocket-cli --request -i "I am a Client" tcp://localhost:7000
```

###  References

* http://www.reactive-streams.org: Reactive Streams is an initiative to provide a standard for asynchronous stream processing with non-blocking back pressure
* http://reactivex.io: An API for asynchronous programming with observable streams
* http://projectreactor.io: Reactor is a second-generation Reactive library for building non-blocking applications on the JVM based on the Reactive Streams Specification
* https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/content/Intro
* http://reactivex.io/documentation/operators.html: reactive operations
* https://dzone.com/articles/functional-amp-reactive-spring-along-with-netflix: Functional and Reactive Spring with Reactor and Netflix OSS
* RxJava2 响应式编程介绍: https://zouzhberk.github.io/rxjava-study/
* Reactive Streams: https://github.com/reactive-streams/reactive-streams-jvm/blob/v1.0.2/README.md#specification
* RSocket: http://rsocket.io/

### Demo

* https://github.com/reactor/lite-rx-api-hands-on