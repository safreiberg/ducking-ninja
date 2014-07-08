package fry.diabetes.tracking;

import org.apache.commons.lang3.Validate;

public class InsulinEvent {
	private final double quantity;
	private final InsulinUnit unit;
	private final int time;
	
	private InsulinEvent(double quantity, InsulinUnit unit, int time) {
		Validate.isTrue(quantity > 0, "Quantity must be greater than zero");
		Validate.notNull(unit);
		this.quantity = quantity;
		this.unit = unit;
		this.time = time;
	}
	
	public static InsulinEvent bolusOfUnits(double quantity, int time) {
		return new InsulinEvent(quantity, InsulinUnit.UNIT, time);
	}
	
	public double getQuantity() {
		return quantity;
	}

	public InsulinUnit getUnit() {
		return unit;
	}

	public int getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return "InsulinEventImpl [quantity=" + quantity
				+ ", unit=" + unit + ", time=" + time + "]";
	}
	
}