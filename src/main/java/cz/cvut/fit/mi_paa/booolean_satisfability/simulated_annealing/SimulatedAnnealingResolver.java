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
		result.setNumOfStates(new Long(numOfStates));
		result.setFormula(getFormula());

		setResult(result);
	}

	private double getInitialTemperature() {
		return getFormula().getWeightsSum() * getFormula().getNumOfVariables();
	}

	private void initTotalNumOfStates() {
		totalNumOfStates = (long) Math.pow(2, getFormula().getNumOfVariables());
	}

	@Override
	public void init() {
		current = new State(getFormula().getNumOfVariables());
		getFormula().setState(current);
	}

	@Override
	public double getCostForCurrentState() {
		getFormula().setState(current);
		return current.getCost(getFormula());
	}

	@Override
	public void createNextState() {
		numOfStates++;
		next = current.clone();
		swapValue();
	}

	private void swapValue() {
		int index = getRandomInt();
		next.setValue(index, new Boolean(!next.getValue(index).booleanValue()));
	}

	private int getRandomInt() {
		return random.nextInt(getFormula().getNumOfVariables().intValue());
	}

	@Override
	public double getCostForNextState() {
		getFormula().setState(next);
		return next.getCost(getFormula());
	}

	@Override
	public void goToNextState() {
		current = next;
		getFormula().setState(current);
		next = null;
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
