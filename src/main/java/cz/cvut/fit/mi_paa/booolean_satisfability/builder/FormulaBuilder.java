package cz.cvut.fit.mi_paa.booolean_satisfability.builder;

import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;

public class FormulaBuilder implements Builder<Formula> {

	private Formula formula;

	@Override
	public void build() {
		formula = new Formula();
	}

	@Override
	public Formula getObject() {
		return formula;
	}
}
