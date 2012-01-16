package cz.cvut.fit.mi_paa.booolean_satisfability;

import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;

public interface Resolver {

	public Result getResult(Integer loopCount, Formula formula);
}
