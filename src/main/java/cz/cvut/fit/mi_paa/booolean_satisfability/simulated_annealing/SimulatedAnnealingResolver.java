package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.State;

public class SimulatedAnnealingResolver extends AbstractResolver implements SimulatedAnnealingProblem {

	private State current;

	private State next;

	@Override
	protected void createResult() {
		setResult(new SimulatedAnnealingResult());
	}

	@Override
	public void init() {
		current = new State(getFormula());
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
		return next.getCost();
	}

	@Override
	public void goToNextState() {
		current = next;
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
