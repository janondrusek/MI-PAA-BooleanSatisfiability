package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

import org.apache.commons.lang3.BooleanUtils;

public class Formula {

	private Clause[] clauses;

	private Integer[] weights;

	private State state;

	public Formula(Clause[] clauses, Integer[] weights) {
		this.clauses = clauses;
		this.weights = weights;
	}

	public Clause[] getClauses() {
		return clauses;
	}

	public Integer[] getWeights() {
		return weights;
	}

	public Integer getNumOfVariables() {
		return new Integer(getWeights().length);
	}

	public Boolean isSatisfiable() {
		boolean[] values = new boolean[getClauses().length];
		for (int i = 0; i < getClauses().length; i++) {
			values[i] = getClauses()[i].getValue(getState());
		}
		return new Boolean(BooleanUtils.and(values));
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Integer getWeightsSum() {
		return sum(getWeights());
	}

	private Integer sum(Integer[] values) {
		int sum = 0;
		for (Integer value : values) {
			sum += value.intValue();
		}
		return new Integer(sum);
	}

}
