package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem;
import cz.cvut.fit.mi_paa.booolean_satisfability.Resolver;
import cz.cvut.fit.mi_paa.booolean_satisfability.Result;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Formula;

public class SimulatedAnnealingResolver implements Resolver, SimulatedAnnealingProblem {

	@Override
	public Result getResult(Integer loopCount, Formula formula) {
		return null;
	}

	@Override
	public void init() {

	}

	@Override
	public double getCostForCurrentState() {
		return 0;
	}

	@Override
	public void createNextState() {

	}

	@Override
	public double getCostForNextState() {
		return 0;
	}

	@Override
	public void goToNextState() {

	}

	@Override
	public boolean isTotalNumberOfStatesReached() {
		return false;
	}

	@Override
	public String getSolutionString() {
		return null;
	}

}
