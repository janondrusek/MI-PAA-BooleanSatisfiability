package cz.cvut.fit.mi_paa.booolean_satisfability.builder;

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

	}

	@Override
	public Clause getObject() {
		return clause;
	}

}
