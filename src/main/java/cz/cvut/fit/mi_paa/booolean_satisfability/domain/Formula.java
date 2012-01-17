package cz.cvut.fit.mi_paa.booolean_satisfability.domain;

public class Formula {

	private Clause[] clauses;

	private Integer[] weights;

	public Formula(Clause[] clauses, Integer[] weights) {
		this.clauses = clauses;
		this.weights = weights;
	}

	public Clause[] getClauses() {
		return clauses;
	}

	public Integer[] getWeights() {
		return weights;
	}

	public Integer getNumOfVariables() {
		return new Integer(getWeights().length);
	}

	public Boolean isSatisfiable() {
		Boolean satisfiable = Boolean.TRUE;
		for (Clause clause : getClauses()) {
			satisfiable &= clause.getValue();
		}
		return satisfiable;
	}

	public Integer getWeightsSum() {
		return sum(getWeights());
	}

	private Integer sum(Integer[] values) {
		int sum = 0;
		for (Integer value : values) {
			sum += value.intValue();
		}
		return new Integer(sum);
	}

}
