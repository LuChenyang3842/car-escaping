package mycontroller;

import controller.CarController;
import world.Car;

public class MyAIController extends CarController{

	public MyAIController(Car car) {
		super(car);
	}

	@Override
	public void update() {
        //update the map with current view
		ExploredMap.getInstance().updateMap(getView());
		
		//choose strategy
		if(){
		    
		}else if(){
		    ...
		}
	}

}
