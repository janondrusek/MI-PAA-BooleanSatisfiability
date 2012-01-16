package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

public class Row {

	private String line;

	public Row(String line) {
		this.line = line;
	}

	public String getLine() {
		return line;
	}

	public boolean isMetaInfo() {
		return line.startsWith("p");
	}

	public boolean isWeight() {
		return line.startsWith("w");
	}

	public boolean isClause() {
		return line.matches("^\\s*(-?[0-9]+\\s?){2,}");
	}
}
