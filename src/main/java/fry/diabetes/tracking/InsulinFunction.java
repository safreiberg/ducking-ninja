package fry.diabetes.tracking;

public class InsulinFunction {
	public static final double INTEGRAL = getIntegral();
	
	public static double getConcentrationAfterStart(int minutes) {
		if (minutes <= 0) {
			return 0;
		}
		double insulin;
		if (minutes <= 90) {
			insulin = 1.04 / (1.0 + Math.exp(-(minutes - 45.0)/7.5)) - 0.04;
		} else if (minutes <= 120) {
			insulin = 1.0 / (1.0 + Math.exp((minutes - 135.0)/7.5)); 
		} else {
			insulin = 0;
		}
		if (insulin < 0) {
			return 0;
		}
		return insulin;
	}
	
	public static double getIntegral() {
		double total = 0;
		for (int i = 0; i < 180; i++) {
			total += getConcentrationAfterStart(i);
		}
		return total;
	}
}
