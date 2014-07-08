package fry.diabetes.tracking;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Day {
	private final Map<Duration, RateConfiguration> rates;
	private final Map<Duration, BodyGlucose> bodyGlucose;
	private final Collection<FoodEvent> foods;
	private final Collection<InsulinEvent> boluses;
	
	public Day(Map<Duration, RateConfiguration> rates,
				Map<Duration, BodyGlucose> bodyGlucose,
				Collection<FoodEvent> foods, 
				Collection<InsulinEvent> boluses) {
		this.rates = Maps.newHashMap(rates);
		this.bodyGlucose = Maps.newHashMap(bodyGlucose);
		this.foods = Lists.newArrayList(foods);
		this.boluses = Lists.newArrayList(boluses);
	}
	
	public static void main(String[] args) throws IOException {
		RateConfiguration allDay = new RateConfiguration(1.2 / 60, 30, 6);
		Duration theDay = Duration.betweenHours(0, 24);
		BodyGlucose bGluc = new BodyGlucose(0.12);
		Map<Duration, RateConfiguration> rates = ImmutableMap.<Duration, RateConfiguration>builder().put(theDay, allDay).build();
		Map<Duration, BodyGlucose> bodyGlucose = ImmutableMap.<Duration, BodyGlucose>builder().put(theDay, bGluc).build();
		Collection<FoodEvent> foods = Lists.newArrayList();
		Collection<InsulinEvent> boluses = Lists.newArrayList();
		
		// breakfast
		boluses.add(InsulinEvent.bolusOfUnits(10, 9*60));
		foods.add(FoodEvent.fromCarbs(60, 9*60 + 20));
		
		
		Day day = new Day(rates, bodyGlucose, foods, boluses);
		List<Double> gluc = day.simulateDay();
		String file = "";
		for (int i = 8*60; i < 12*60; i++) {
			file += i + " " + gluc.get(i) + "\n";
		}
		FileUtils.writeStringToFile(new File(System.getProperty("user.dir") + "/outputs"), file);
	}
	
	public List<Double> simulateDay() {
		List<ProviderFunction> insulinFunctions = Lists.newArrayList();
		List<ProviderFunction> glucoseFunctions = Lists.newArrayList();
		
		for (FoodEvent event : foods) {
			glucoseFunctions.add(getFoodProviderFunction(event.getCarbs(), event.getTime()));
		}
		for (InsulinEvent event : boluses) {
			insulinFunctions.add(getInsulinProviderFunction(event.getQuantity(), event.getTime()));
		}
		for (int time = 0; time < Duration.MINUTES_IN_DAY; time++) {
			RateConfiguration rateConfig = getConfigFromTime(time);
			BodyGlucose bodyGlucose = getGlucoseFromTime(time);
			insulinFunctions.add(getInsulinProviderFunction(rateConfig.getBasalRate(), time));
			glucoseFunctions.add(getFoodProviderFunction(bodyGlucose.getRate(), time));
		}
		
		List<Double> day = Lists.newArrayList();
		double bloodGlucose = 100;
		for (int time = 0; time < Duration.MINUTES_IN_DAY; time++) {
			RateConfiguration rateConfig = getConfigFromTime(time);
			for (ProviderFunction func : insulinFunctions) {
				bloodGlucose -= rateConfig.getPointsPerUnit() / InsulinFunction.INTEGRAL * func.getValueAtTime(time);
			}
			for (ProviderFunction func : glucoseFunctions) {
				bloodGlucose += 1.0 / rateConfig.getCarbsPerUnit() * rateConfig.getPointsPerUnit() * func.getValueAtTime(time);
			}
			day.add(bloodGlucose);
		}
		return day;
	}
	
	private ProviderFunction getInsulinProviderFunction(final double value, final int start) {
		return new ProviderFunction() {
			@Override
			public double getValueAtTime(int minutes) {
				return InsulinFunction.getConcentrationAfterStart(minutes - start) * value;
			}
		};
	}
	
	private ProviderFunction getFoodProviderFunction(final double value, final int start) {
		return new ProviderFunction() {
			@Override
			public double getValueAtTime(int minutes) {
				return CarbFunction.getAddedDuringMinute(minutes - start) * value;
			}
		};
	}

	private BodyGlucose getGlucoseFromTime(int time) {
		for (Map.Entry<Duration, BodyGlucose> entry : bodyGlucose.entrySet()) {
			if (entry.getKey().containsMinute(time)) {
				return entry.getValue();
			}
		}
		throw new IllegalArgumentException("No glucose for time " + time);
	}
	
	private RateConfiguration getConfigFromTime(int time) {
		for (Map.Entry<Duration, RateConfiguration> entry : rates.entrySet()) {
			if (entry.getKey().containsMinute(time)) {
				return entry.getValue();
			}
		}
		throw new IllegalArgumentException("No config for time " + time);
	}
}
