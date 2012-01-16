package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

public class State {

	private Boolean[] values;

	private Formula formula;

	public State(Formula formula) {
		this.formula = formula;
		init();
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
		
		return 0;
	}

}
