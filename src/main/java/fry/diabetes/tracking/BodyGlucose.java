package fry.diabetes.tracking;

public class BodyGlucose {
	private final double rate;
	
	public BodyGlucose(double rate) {
		this.rate = rate;
	}
	
	// carbs / minute
	public double getRate() {
		return rate;
	}
}
