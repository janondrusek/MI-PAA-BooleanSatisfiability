package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import cz.cvut.fit.mi_paa.booolean_satisfability.Resolver;
import cz.cvut.fit.mi_paa.booolean_satisfability.Result;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;

public abstract class AbstractResolver implements Resolver {

	private Result result;

	private Formula formula;

	private Integer loopCount;

	@Override
	public Result getResult(Integer loopCount, Formula formula) {
		createResult();
		this.loopCount = loopCount;
		this.formula = formula;
		return result;
	}

	abstract protected void createResult();

	protected void setResult(Result result) {
		this.result = result;
	}

	protected Result getResult() {
		return result;
	}

	protected Formula getFormula() {
		return formula;
	}

	protected Integer getLoopCount() {
		return loopCount;
	}

}
