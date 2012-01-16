package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

public class Row {

	private String line;

	public Row(String line) {
		this.line = line;
	}

	public boolean isWeight() {
		return line.startsWith("w");
	}

	public boolean isClause() {
		return line.matches("^-?[0-9]+");
	}
}
