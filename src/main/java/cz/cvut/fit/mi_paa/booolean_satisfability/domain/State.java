package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class State {

	private Boolean[] values;

	public State(Integer numOfVariables) {
		init(numOfVariables);
	}

	private State(Boolean[] values) {
		this.values = values;
	}

	private void init(Integer numOfVariables) {
		values = new Boolean[numOfVariables];
		for (int i = 0; i < numOfVariables; i++) {
			values[i] = Boolean.FALSE;
		}
	}

	public Boolean[] getValues() {
		return values;
	}

	public void setValues(Boolean[] values) {
		this.values = values;
	}

	public Boolean getValue(Integer index) {
		return values[index];
	}

	public double getCost(Formula formula) {
		double cost = formula.getWeightsSum();
		for (int i = 0; i < getValues().length; i++) {
			cost -= values[i].booleanValue() ? formula.getWeights()[i].doubleValue() : 0;
		}
		return cost + getPenalty(formula);
	}

	private int getPenalty(Formula formula) {
		int maxWeight = max(formula.getWeights());
		return getNumOfUnsatisfiedClauses(formula) * maxWeight;
	}

	private int max(Integer[] values) {
		int max = 0;
		for (Integer value : values) {
			if (value.intValue() > max) {
				max = value.intValue();
			}
		}
		return max;
	}

	private int getNumOfUnsatisfiedClauses(Formula formula) {
		int satisfied = formula.getClauses().length;
		for (Clause clause : formula.getClauses()) {
			if (clause.getValue(this).booleanValue()) {
				satisfied--;
			}
		}
		return satisfied;
	}

	public void setValue(int index, Boolean value) {
		values[index] = value;
	}

	@Override
	public State clone() {
		return new State(ArrayUtils.clone(getValues()));
	}

	@Override
	public String toString() {
		return "State[" + Arrays.toString(values) + "]";
	}
}
