package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

import org.apache.commons.lang3.BooleanUtils;

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
			signa[i] = new Boolean(variables[i] < 0);
		}
	}

	private void initIndices(Integer[] variables) {
		for (int i = 0; i < variables.length; i++) {
			indices[i] = new Integer(Math.abs(variables[i]) - 1);
		}
	}

	public Boolean[] getSigna() {
		return signa;
	}

	public Integer[] getIndices() {
		return indices;
	}

	public Boolean getValue(State state) {
		boolean[] values = new boolean[getIndices().length];
		for (int i = 0; i < getIndices().length; i++) {
			values[i] = state.getValue(getIndices()[i]).booleanValue() ^ getSigna()[i].booleanValue();
		}
		return new Boolean(BooleanUtils.or(values));
	}

}
