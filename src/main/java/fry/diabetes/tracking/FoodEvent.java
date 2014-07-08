package fry.diabetes.tracking;

import org.apache.commons.lang3.Validate;

public class FoodEvent {
	private final double carbs;
	private final int time;
	
	private FoodEvent(double carbs, int time) {
		Validate.isTrue(carbs > 0);
		Validate.notNull(time);
		this.carbs = carbs;
		this.time = time;
	}
	
	public static FoodEvent fromCarbs(double carbs, int time) {
		return new FoodEvent(carbs, time);
	}

	public double getCarbs() {
		return carbs;
	}

	public int getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "FoodEventImpl [carbs=" + carbs + ", time=" + time + "]";
	}
	
}
