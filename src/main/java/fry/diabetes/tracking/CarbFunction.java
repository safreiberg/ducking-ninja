package fry.diabetes.tracking;

public class CarbFunction {
	public static double getAddedDuringMinute(int minutes) {
		return getAvailabilityAfterConsumption(minutes) - getAvailabilityAfterConsumption(minutes - 1);
	}
	
	public static double getAvailabilityAfterConsumption(int minutes) {
		if (minutes <= 0) {
			return 0;
		}
		double carbs = 1.15 / (1.0 + Math.exp(-(minutes - 35)/15.0)) - .15;
		if (carbs < 0) {
			return 0;
		}
		return carbs;
	}
}
