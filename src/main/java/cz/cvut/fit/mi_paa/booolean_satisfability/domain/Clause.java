package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

public class Clause {

	private Boolean[] signa;

	private Integer[] indices;

	public Clause(Integer[] variables) {
		initArrays(variables.length);
		initSigna(variables);
		initIndices(variables);
	}

	private void initArrays(int length) {
		signa = new Boolean[length];
		indices = new Integer[length];
	}

	private void initSigna(Integer[] variables) {
		for (int i = 0; i < variables.length; i++) {
			signa[i] = variables[i] > 0;
		}
	}

	private void initIndices(Integer[] variables) {
		for (int i = 0; i < variables.length; i++) {
			indices[i] = Math.abs(variables[i]) - 1;
		}

	}

	public Boolean[] getSigna() {
		return signa;
	}

	public Integer[] getIndices() {
		return indices;
	}

	public Boolean getValue(State state) {
		boolean value = false;
		for (int i = 0; i < getIndices().length; i++) {
			value = value || state.getValue(getIndices()[i]).booleanValue() ^ getSigna()[i].booleanValue();
		}
		return new Boolean(value);
	}

}
