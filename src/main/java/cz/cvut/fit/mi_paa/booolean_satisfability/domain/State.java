package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

public class State {

	private Boolean[] values;

	public State(Boolean[] values) {
		this.values = values;
	}

	public Boolean getValue(Integer index) {
		return values[index];
	}

}
