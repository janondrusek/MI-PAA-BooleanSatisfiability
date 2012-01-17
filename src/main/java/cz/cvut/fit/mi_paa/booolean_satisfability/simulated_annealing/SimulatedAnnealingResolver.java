package cz.cvut.fit.mi_paa.booolean_satisfability.simulated_annealing;

import java.util.Random;

import ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem;
import ure.phd.simulatedannealing.solver.SimulatedAnnealingProblemSolver;
import cz.cvut.fit.mi_paa.booolean_satisfability.domain.State;

public class SimulatedAnnealingResolver extends AbstractResolver implements SimulatedAnnealingProblem {

	private State current;
	private State next;

	private Random random = new Random();

	private long numOfStates;
	private long totalNumOfStates;

	@Override
	protected void createResult() {
		SimulatedAnnealingResult result = new SimulatedAnnealingResult();
		initTotalNumOfStates();

		SimulatedAnnealingScheduler scheduler = new SimulatedAnnealingScheduler(getInitialTemperature(), 0.0001, 0.995,
				3);
		scheduler.reset();

		SimulatedAnnealingProblemSolver problemSolver = new SimulatedAnnealingProblemSolver(scheduler, this);

		problemSolver.solve();
		result.setFormula(getFormula());
		result.setState(current);

		setResult(result);

	}

	private double getInitialTemperature() {
		return getFormula().getWeightsSum();
	}

	private void initTotalNumOfStates() {
		totalNumOfStates = (long) Math.pow(2D, getFormula().getNumOfVariables());
	}

	@Override
	public void init() {
		current = new State(getFormula());
	}

	@Override
	public double getCostForCurrentState() {
		return current.getCost();
	}

	@Override
	public void createNextState() {
		numOfStates++;
		next = current.clone();
		swapValue(next);
	}

	private void swapValue(State state) {
		int index = random.nextInt(getFormula().getNumOfVariables());
		state.setValue(index, new Boolean(!state.getValue(index)));
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
		return totalNumOfStates == numOfStates;
	}

	@Override
	public String getSolutionString() {
		return null;
	}

}
