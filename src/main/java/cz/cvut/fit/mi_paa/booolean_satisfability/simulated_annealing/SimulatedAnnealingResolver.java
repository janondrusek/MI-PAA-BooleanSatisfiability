package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem;

public class SimulatedAnnealingResolver extends AbstractResolver implements SimulatedAnnealingProblem {

	@Override
	protected void createResult() {
		setResult(new SimulatedAnnealingResult());
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
