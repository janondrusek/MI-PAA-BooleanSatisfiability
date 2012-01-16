package ure.phd.simulatedannealing.solver;

import ure.phd.simulatedannealing.interfaces.AnnealingScheduler;
import ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem;

public class SimulatedAnnealingProblemSolver {

	/**
	 * AnnealingScheduler is required for limiting the number of tries for
	 * global search and also for search in a temperature state
	 */
	protected AnnealingScheduler annealingSchedule;

	/**
	 * Problem is the state generator also the cost function is defined in
	 * problem
	 */
	protected SimulatedAnnealingProblem simulatedAnnealingProblem;

	public SimulatedAnnealingProblemSolver(AnnealingScheduler annealingSchedule,
			SimulatedAnnealingProblem simulatedAnnealingProblem) {
		this.annealingSchedule = annealingSchedule;
		this.simulatedAnnealingProblem = simulatedAnnealingProblem;
	}

	public SimulatedAnnealingProblemSolver() {
	}

	public AnnealingScheduler getAnnealingSchedule() {
		return annealingSchedule;
	}

	public void setAnnealingSchedule(AnnealingScheduler annealingSchedule) {
		this.annealingSchedule = annealingSchedule;
	}

	public SimulatedAnnealingProblem getSimulatedAnnealingProblem() {
		return simulatedAnnealingProblem;
	}

	public void setSimulatedAnnealingProblem(SimulatedAnnealingProblem simulatedAnnealingProblem) {
		this.simulatedAnnealingProblem = simulatedAnnealingProblem;
	}

	private strictfp double getProbability(double deltaEnergy) {
		return Math.exp(-deltaEnergy / annealingSchedule.getCurrentTemperature());
	}

	private strictfp double getRandomDouble() {
		return Math.random();
	}

	/**
	 * this function solves the problem in the following steps <br/>
	 * 
	 * @see ure.phd.simulatedannealing.interfaces.AnnealingScheduler#init() 0.
	 *      init annealingSchedular
	 * @see ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem#init() <br/>
	 *      0. init simulatedAnnealingProblem
	 * @see ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem#isTotalNumberOfStatesReached() <br/>
	 *      1. loop until reaching the freezing point
	 * @see ure.phd.simulatedannealing.interfaces.AnnealingScheduler#isFreezingPointReached() <br/>
	 *      1. OR until reaching the limit for number of tries
	 * @SimulatedAnnealingProblem.isTotalNumberOfStatesReached()
	 * @see ure.phd.simulatedannealing.interfaces.AnnealingScheduler#incrementIterationCount() <br/>
	 *      1.0 increment iteration count for schedular
	 * @see ure.phd.simulatedannealing.interfaces.AnnealingScheduler#isMarkovChainLimitReached() <br/>
	 *      1.1 loop until reaching a limit for a certain temperature
	 * @see ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem#createNextState() <br/>
	 *      1.1.1 create a next state
	 * @see ure.phd.simulatedannealing.interfaces.AnnealingScheduler#incrementSameTemperatureIterationCount() <br/>
	 *      1.1.1.0 increment schedular's iteration count for same temperature
	 * @see ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem#getCostForCurrentState()
	 * @see ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem#getCostForNextState() <br/>
	 *      1.1.2 find the difference between the next state and the current
	 *      state<br/>
	 *      1.1.3 if the delta is less then 0 then go to next state<br/>
	 *      1.1.3 OR if not use Boltzman function in order to decide main
	 *      purpose of accepting an increment state is not to get stuck in a
	 *      local minimum
	 * @see ure.phd.simulatedannealing.interfaces.SimulatedAnnealingProblem#goToNextState() <br/>
	 *      1.1.3.1 go to next state
	 * @see ure.phd.simulatedannealing.interfaces.AnnealingScheduler#incrementAcceptanceCount() <br/>
	 *      1.1.3.2 increment acceptance count for schedular
	 * @see AnnealingScheduler#coolDown(double) 1.2 cool it down<br/>
	 * 
	 */
	public strictfp void solve() {
		// init scheduler and problem
		annealingSchedule.init();
		simulatedAnnealingProblem.init();

		// loop until reaching the freezing point
		while ((!simulatedAnnealingProblem.isTotalNumberOfStatesReached())
				&& !annealingSchedule.isFreezingPointReached()) {

			double deltaEnergy = 0.0;
			annealingSchedule.incrementIterationCount();

			// loop until reaching a limit for a certain temperature
			while (!annealingSchedule.isMarkovChainLimitReached()) {

				// create a next state
				simulatedAnnealingProblem.createNextState();
				annealingSchedule.incrementSameTemperatureIterationCount();

				// find the difference between the next state and the current
				// state
				deltaEnergy = simulatedAnnealingProblem.getCostForNextState()
						- simulatedAnnealingProblem.getCostForCurrentState();

				// if the delta is less then 0 then go to next state
				// if not use Boltzman function in order to decide
				// main purpose of accepting an increment state is not to get
				// stuck on a local minimum
				if ((deltaEnergy <= 0) || (deltaEnergy > 0 && getProbability(deltaEnergy) > getRandomDouble())) {
					simulatedAnnealingProblem.goToNextState();
					annealingSchedule.incrementAcceptanceCount();
				}
			}

			// cool down
			annealingSchedule.coolDown(deltaEnergy);
		}
	}
}
