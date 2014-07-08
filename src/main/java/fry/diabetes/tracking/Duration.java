package fry.diabetes.tracking;

import org.apache.commons.lang3.Validate;

public class Duration {
	private final long startTimeMillis;
	private final long endTimeMillis;
	private static final long HOURS_IN_DAY = 24;
	public static final long MINUTES_IN_DAY = HOURS_IN_DAY * 60;
	public static final long MILLIS_IN_DAY = MINUTES_IN_DAY * 60 * 1000;
	
	private Duration(long startTimeMillis, long endTimeMillis) {
		Validate.isTrue(startTimeMillis >= 0);
		Validate.isTrue(endTimeMillis > 0);
		Validate.isTrue(startTimeMillis < endTimeMillis);
		this.startTimeMillis = startTimeMillis;
		this.endTimeMillis = endTimeMillis;
	}
	
	public static Duration betweenHours(int start, int end) {
		return new Duration(convertHour(start), convertHour(end) - 1);
	}
	
	public long getStart() {
		return startTimeMillis;
	}
	
	public long getEnd() {
		return endTimeMillis;
	}
	
	public boolean containsHour(int hour) {
		long converted = convertHour(hour);
		return containsMillis(converted);
	}
	
	public boolean containsMinute(int minute) {
		long converted = convertMinute(minute);
		return containsMillis(converted);
	}
	
	public boolean containsMillis(long millis) {
		return startTimeMillis <= millis && millis < endTimeMillis;
	}
	
	private static long convertMinute(int minute) {
		Validate.isTrue(minute >= 0);
		Validate.isTrue(minute <= MINUTES_IN_DAY);
		return minute * 60 * 1000;
	}
	
	private static long convertHour(int hour) {
		Validate.isTrue(hour >= 0);
		Validate.isTrue(hour <= HOURS_IN_DAY);
		return hour * 60 * 60 * 1000;
	}

	@Override
	public String toString() {
		return "Duration [startTimeMillis=" + startTimeMillis
				+ ", endTimeMillis=" + endTimeMillis + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (endTimeMillis ^ (endTimeMillis >>> 32));
		result = prime * result
				+ (int) (startTimeMillis ^ (startTimeMillis >>> 32));
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
		Duration other = (Duration) obj;
		if (endTimeMillis != other.endTimeMillis)
			return false;
		if (startTimeMillis != other.startTimeMillis)
			return false;
		return true;
	}
	
}
