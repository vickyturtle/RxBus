# Event Bus based on rx java
A draft for implementing event bus using rx java.

- Have to try to maintain size of source map by removing `Source` when not subscribed for certain period of time and if they dont have any sticky events.
- We can use one subject also for all events with using filter on subject to return specific type of observable only, but I *prefer* this approach as there isn't any `instanceOf` check.
- I have tried to model in on the lines of Retrofit adapter and converter API, so that there is a separate source for each type. 