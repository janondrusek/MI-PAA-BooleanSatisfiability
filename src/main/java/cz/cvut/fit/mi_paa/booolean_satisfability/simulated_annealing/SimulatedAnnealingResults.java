package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.stat.StatUtils;

import cz.cvut.fit.mi_paa.booolean_satisfability.Result;
import cz.cvut.fit.mi_paa.booolean_satisfability.Results;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.State;

public class SimulatedAnnealingResults implements Results {

	private Result[] results;

	public SimulatedAnnealingResults(Result[] results) {
		this.results = results;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Result result : results) {
			sb.append(result);
			sb.append("\n");
		}

		sb.append("avg. numOfStates: ");
		sb.append(format(getAverageNumOfStates()));
		sb.append("\n");
		sb.append("avg. satisfiable: ");
		sb.append(format(getAverageSatisfiable()));
		sb.append("\n");
		sb.append("avg. relative deviation: ");
		sb.append(format(getAverageRelativeDeviation()));

		return sb.toString();
	}

	private String format(Double number) {
		return String.format("%.4f", number);
	}

	private Double getAverageNumOfStates() {
		return getAverage(collectNumOfStates());
	}

	private Long[] collectNumOfStates() {
		Long[] states = new Long[results.length];
		for (int i = 0; i < results.length; i++) {
			states[i] = results[i].getNumOfStates();
		}
		return states;
	}

	private Double getAverageSatisfiable() {
		return getAverage(collectAverageSatisfiable());
	}

	private Integer[] collectAverageSatisfiable() {
		Integer[] states = new Integer[results.length];
		for (int i = 0; i < results.length; i++) {
			states[i] = results[i].getSatisfiable().size();
		}
		return states;
	}

	private Double getAverageRelativeDeviation() {
		return getAverage(collectRelativeDeviations());
	}

	private Double[] collectRelativeDeviations() {
		Double[] relativeDeviations = new Double[results.length];
		for (int i = 0; i < results.length; i++) {
			relativeDeviations[i] = getRelativeDeviation(results[i]);
		}
		return relativeDeviations;
	}

	private Double getRelativeDeviation(Result result) {
		if (result.getSatisfiable().size() == 0) {
			return new Double(1);
		}
		List<Double> relativeDeviations = new ArrayList<>();
		State best = result.getBest();
		Double optimal = result.getValue(best);
		for (int i = 0; i < result.getSatisfiable().size(); i++) {
			if (result.getSatisfiable().get(i) == result.getBest()) {
				continue;
			}
			relativeDeviations.add(getRelativeDeviation(optimal, result.getValue(result.getSatisfiable().get(i))));
		}
		return getAverage(relativeDeviations.toArray(new Double[relativeDeviations.size()]));
	}

	private Double getRelativeDeviation(Double optimal, Double value) {
		return new Double((optimal.doubleValue() - value.doubleValue()) / value.doubleValue());
	}

	private Double getAverage(Number[] values) {
		double[] doubles = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			doubles[i] = values[i].doubleValue();
		}
		return new Double(StatUtils.mean(doubles));
	}

}
