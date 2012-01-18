package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import java.util.List;

import cz.cvut.fit.mi_paa.booolean_satisfability.Result;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.State;

public class SimulatedAnnealingResult implements Result {

	private Formula formula;

	private Long numOfStates;

	private List<State> satisfiable;

	@Override
	public String toString() {
		State best = getBest();

		StringBuilder sb = new StringBuilder();

		sb.append("SimulatedAnnealingResult[");
		sb.append("numOfStates=");
		sb.append(getNumOfStates());
		sb.append(", satisfiable=");
		sb.append(satisfiable.size());
		sb.append(", value=");
		sb.append(getValue(best));
		sb.append(", ");
		sb.append(best);
		sb.append("]");

		return sb.toString();
	}

	@Override
	public State getBest() {
		State best = null;
		for (State state : satisfiable) {
			if (getValue(state) > getValue(best)) {
				best = state;
			}
		}
		return best;
	}

	@Override
	public Double getValue(State state) {
		double value = 0;
		if (state != null) {
			for (int i = 0; i < formula.getNumOfVariables(); i++) {
				value += state.getValue(i).booleanValue() ? formula.getWeights()[i].doubleValue() : 0;
			}
		}
		return new Double(value);
	}

	@Override
	public Long getNumOfStates() {
		return numOfStates;
	}

	public void setNumOfStates(Long numOfStates) {
		this.numOfStates = numOfStates;
	}

	public Formula getFormula() {
		return formula;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	public void setSatisfiable(List<State> satisfiable) {
		this.satisfiable = satisfiable;
	}

	@Override
	public List<State> getSatisfiable() {
		return satisfiable;
	}

}
