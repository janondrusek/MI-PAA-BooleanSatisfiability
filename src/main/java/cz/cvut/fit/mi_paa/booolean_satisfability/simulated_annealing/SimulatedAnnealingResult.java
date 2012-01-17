package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import cz.cvut.fit.mi_paa.booolean_satisfability.Result;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;

public class SimulatedAnnealingResult implements Result {

	private Formula formula;

	private Long numOfStates;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("SimulatedAnnealingResult[");
		sb.append("numOfStates=");
		sb.append(getNumOfStates());
		sb.append(", satisfiable=");
		sb.append(formula.isSatisfiable());
		sb.append(", value=");
		sb.append(formula.getState().getCost(formula));
		sb.append(", ");
		sb.append(formula.getState());
		sb.append("]");

		return sb.toString();
	}

	public Long getNumOfStates() {
		return numOfStates;
	}

	public void setNumOfStates(Long numOfStates) {
		this.numOfStates = numOfStates;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

}
