package cz.cvut.fit.mi_paa.booolean_satisfability.builder;

import java.util.Scanner;

import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Clause;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Row;

public class FormulaBuilder implements Builder<Formula> {

	private Formula formula;

	private Row[] rows;

	public FormulaBuilder(Row[] rows) {
		this.rows = rows;
	}

	@Override
	public void build() {
		Scanner s = new Scanner(getMetaInfo().getLine());
		s.next(); // p
		s.next(); // cnf

		int numOfVariables = s.nextInt();
		int numOfClauses = s.nextInt();

		formula = new Formula(getClauses(numOfClauses), getWeights(numOfVariables));
	}

	private Row getMetaInfo() {
		for (Row row : rows) {
			if (row.isMetaInfo()) {
				return row;
			}
		}
		return null;
	}

	private Clause[] getClauses(int numOfClauses) {
		Clause[] clauses = new Clause[numOfClauses];

		for (int i = 0; i < numOfClauses; i++) {
			Builder<Clause> builder = new ClauseBuilder(getClauseRow(i + 1));
			builder.build();
			clauses[i] = builder.getObject();
		}

		return clauses;
	}

	private Row getClauseRow(int count) {
		Row clause = null;
		for (Row row : rows) {
			if (row.isClause()) {
				clause = row;
				count--;
			}

			if (count == 0) {
				break;
			}
		}
		return clause;
	}

	private Integer[] getWeights(int numOfWeights) {
		Integer[] weights = new Integer[numOfWeights];
		Scanner s = new Scanner(getWeightsRow());
		s.next(); // w
		for (int i = 0; i < numOfWeights; i++) {
			weights[i] = s.nextInt();
		}
		return weights;
	}

	private String getWeightsRow() {
		for (Row row : rows) {
			if (row.isWeight()) {
				return row.getLine();
			}
		}
		return null;
	}

	@Override
	public Formula getObject() {
		return formula;
	}
}
