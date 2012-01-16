package ure.phd.simulatedannealing.interfaces;

public interface SimulatedAnnealingProblem {

	/**
	 * initializes the problem
	 */
	public void init();

	/**
	 * should calculate the cost for the current state
	 * 
	 * @return the result of the current state (also called current Energy)
	 */
	public double getCostForCurrentState();

	/**
	 * creates a new state but doesn't change the current state, next state is
	 * usually created randomly in SA applications
	 */
	public void createNextState();

	/**
	 * should calculate the cost for the next state
	 * 
	 * @return the result of the next generated state which is created in
	 *         createNextState
	 */
	public double getCostForNextState();

	/**
	 * sets the current state as the next state current state <--- next state
	 */
	public void goToNextState();

	/**
	 * problem solve operation will be terminated if this function returns true
	 * in essence this function is used if one wants to put a limitation besides
	 * freezing temperature
	 * 
	 * @return true if there is a desired number of solutions to limit the
	 *         solution
	 */
	public boolean isTotalNumberOfStatesReached();

	/**
	 * should generate a result set string
	 * 
	 * @return result set as a string
	 */
	public String getSolutionString();

}
