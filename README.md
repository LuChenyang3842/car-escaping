# run-program instruction

## step1:
go to assests --> Driving.Properties

## step2:
change to: "Controller=mycontroller.ManualController" to manually play the game, otherwise the car will find path automatically.

# Game rule
### 1.must collect all keys before escaping
### 2.hit to the wall will result in blood reduction
### 3.fail if car crush into mud
### 4. health can be recovered in certian points.


# Major algorithm for automatic path-finding
# A* is the major algorithm
the algorithm can be found in src ---> mycontroller ---> routing strategy

# Design pattern implemented in the project
### singleton pattern
### strategy pattern
### Factory pattern
### controller pattern
Detail of design pattern can be find in report.pdf
