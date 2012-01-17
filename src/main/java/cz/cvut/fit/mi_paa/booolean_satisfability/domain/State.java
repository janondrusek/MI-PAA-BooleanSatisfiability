package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

import org.apache.commons.lang3.ArrayUtils;

public class State {

	private Boolean[] values;

	private Formula formula;

	public State(Formula formula) {
		this.formula = formula;
		init();
	}

	private State(Formula formula, Boolean[] values) {
		this(formula);
		this.values = values;
	}

	private void init() {
		values = new Boolean[getFormula().getNumOfVariables()];
		for (int i = 0; i < getFormula().getNumOfVariables(); i++) {
			values[i] = Boolean.FALSE;
		}
	}

	public Boolean[] getValues() {
		return values;
	}

	public void setValues(Boolean[] values) {
		this.values = values;
	}

	public Formula getFormula() {
		return formula;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	public Boolean getValue(Integer index) {
		return values[index];
	}

	public double getCost() {
		double cost = 0;
		for (int i = 0; i < getValues().length; i++) {
			cost += values[i].booleanValue() ? getFormula().getWeights()[i].doubleValue() : 0;
		}

		if (!getFormula().isSatisfiable()) {
			cost -= getFormula().getWeightsSum().doubleValue();
		}

		return cost;
	}

	public void setValue(int index, Boolean value) {
		values[index] = value;
	}

	@Override
	public State clone() {
		return new State(getFormula(), ArrayUtils.clone(getValues()));
	}
}
