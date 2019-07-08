# run-program instruction

## step1:
go to assests --> Driving.Properties

## step2:
change to: "Controller=mycontroller.ManualController" to manually play the game, otherwise the car will find path automatically.

# Game rule
* must collect all keys before escaping
* hit to the wall will result in blood reduction
* fail if car crush into mud
* health can be recovered in certian points.


# Major algorithm for automatic path-finding
The routing algorithm we used here is A* heuristic search. The basic idea is that a high heuristic value was given to dangerous traps so that the car will less likely to go over the trap unless it is necessary. In addition, we defined the legal actions and successors based on the gaming rule, for example, the coordinate with wall cannot be regarded as successor, if the car is on the grass, the legal directions would be current direction and reverse direction.
The A* algorithm would return a list of coordinates that navigates the car from current location to goal location. However, in each round, only the first coordinate returned by A* algorithm is used to navigate the car.
The reason for using A* is that A* is very suitable for strategy pattern. For different strategies, we only need to define different goal states (i.e. target coordinate), which makes the system highly extensible

**The algorithm can be found in src ---> mycontroller ---> routing strategy**

# Design pattern implemented in the project
* singleton pattern
* strategy pattern
* Factory pattern
* controller pattern <br/>
**Detail of design pattern can be find in report.pdf**
