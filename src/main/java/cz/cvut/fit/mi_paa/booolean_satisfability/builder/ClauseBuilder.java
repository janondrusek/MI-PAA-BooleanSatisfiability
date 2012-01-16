package cz.cvut.fit.mi_paa.booolean_satisfability.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Clause;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Row;

public class ClauseBuilder implements Builder<Clause> {

	private Row row;

	private Clause clause;

	public ClauseBuilder(Row row) {
		this.row = row;
	}

	@Override
	public void build() {
		List<Integer> variables = new ArrayList<Integer>();
		Scanner s = new Scanner(row.getLine());
		int i;
		while ((i = s.nextInt()) != 0) {
			variables.add(new Integer(i));
		}
		clause = new Clause(variables.toArray(new Integer[variables.size()]));
	}

	@Override
	public Clause getObject() {
		return clause;
	}

}
