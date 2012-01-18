package cz.cvut.fit.mi_paa.booolean_satisfability;

import java.util.List;

import cz.cvut.fit.mi_paa.booolean_satisfability.domain.State;

public interface Result {

	public Long getNumOfStates();

	public State getBest();

	public List<State> getSatisfiable();

	public Double getValue(State state);

	public String toString();
}
