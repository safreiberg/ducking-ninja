package fry.diabetes.tracking;

public class RateConfiguration {
	private final double basalRate;
	private final double pointsPerUnit;
	private final double carbsPerUnit;
	
	public RateConfiguration(double basalRate, double pointsPerUnit, double carbsPerUnit) {
		this.basalRate = basalRate;
		this.pointsPerUnit = pointsPerUnit;
		this.carbsPerUnit = carbsPerUnit;
	}

	// units / minute
	public double getBasalRate() {
		return basalRate;
	}

	public double getPointsPerUnit() {
		return pointsPerUnit;
	}

	public double getCarbsPerUnit() {
		return carbsPerUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(basalRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(carbsPerUnit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pointsPerUnit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RateConfiguration other = (RateConfiguration) obj;
		if (Double.doubleToLongBits(basalRate) != Double
				.doubleToLongBits(other.basalRate))
			return false;
		if (Double.doubleToLongBits(carbsPerUnit) != Double
				.doubleToLongBits(other.carbsPerUnit))
			return false;
		if (Double.doubleToLongBits(pointsPerUnit) != Double
				.doubleToLongBits(other.pointsPerUnit))
			return false;
		return true;
	}
	
}