package cz.cvut.fit.mi_paa.booolean_satisfability;

import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;

public interface Resolver {

	public Results getResults(Integer loopCount, Formula formula);
}
